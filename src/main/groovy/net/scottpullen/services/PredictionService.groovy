package net.scottpullen.services

import com.google.maps.DirectionsApi
import com.google.maps.DirectionsApiRequest
import com.google.maps.GeoApiContext
import com.google.maps.model.DirectionsRoute
import com.google.maps.model.LatLng
import com.google.maps.model.TravelMode
import groovy.time.TimeCategory
import groovy.util.logging.Slf4j
import net.scottpullen.mbta.Stop
import net.scottpullen.mbta.Trip
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.text.SimpleDateFormat
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

@Slf4j
@Service
class PredictionService {

    private GeoApiContext geoApiContext
    private MBTAService mbtaService

    @Autowired
    PredictionService(GeoApiContext geoApiContext, MBTAService mbtaService) {
        this.geoApiContext = geoApiContext
        this.mbtaService = mbtaService
    }

    // Non-completable future: 500ms, Completeable future: 200ms - 400ms
    List<Map> determineTimesToGo(String stopId, LatLng originLatLng, TravelMode mode) {
        Stop stop = mbtaService.getStop(stopId)

        CompletableFuture<List<Trip>> tripsFuture = CompletableFuture.supplyAsync({
            mbtaService.predictionsByStop(stopId)
        })

        CompletableFuture<Integer> etaToStopFuture = CompletableFuture.supplyAsync({
            Integer etaToStop = null
            DirectionsApiRequest directionsRequest = DirectionsApi.newRequest(geoApiContext)
            directionsRequest.origin(originLatLng)
            directionsRequest.destination(new LatLng(stop.stopLat, stop.stopLon))
            directionsRequest.mode(mode)

            List<DirectionsRoute> directionsRoutes = directionsRequest.await() as List
            DirectionsRoute directionsRoute = directionsRoutes[0]
            if(directionsRoute) {
                etaToStop = directionsRoute.legs[0].duration.inSeconds
            }

            etaToStop
        })

        List<Map> results

        CompletableFuture<Void> resultsFuture = etaToStopFuture.thenAcceptBoth(tripsFuture, { Integer etaToStop, List<Trip> trips ->
            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM d yyyy @ h:mm a")

            use(TimeCategory) {
                results = trips.stream().filter({ it.preAway >= etaToStop }).map({
                    Integer preAway = it.preAway
                    Integer leaveIn = preAway - etaToStop

                    Map m = [:]

                    m.headsign = it.tripHeadsign
                    m.schArrDt = dateFormatter.format(new Date(it.scheduledArrivalDt * 1000))
                    m.preAway = preAway
                    m.etaToStation = etaToStop
                    m.leaveIn = (int) (leaveIn / 60)
                    m.leaveAt = dateFormatter.format(new Date() + leaveIn.seconds)

                    m
                }).collect(Collectors.toList())
            }
        })

        // wait for both to complete and get results
        CompletableFuture.allOf(resultsFuture).join()

        results
    }
}
