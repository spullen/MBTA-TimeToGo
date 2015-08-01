package net.scottpullen.services

import groovy.util.logging.Slf4j
import net.scottpullen.mbta.Direction
import net.scottpullen.mbta.PredictionsByStopResponse
import net.scottpullen.mbta.Route
import net.scottpullen.mbta.RoutesResponse
import net.scottpullen.mbta.Stop
import net.scottpullen.mbta.StopsByRouteResponse
import net.scottpullen.mbta.Trip
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

import java.util.stream.Collectors

@Slf4j
@Service
class MBTAService {

    @Value('${MBTA_API_KEY}') private String mbtaApiKey

    private String baseUrl = "http://realtime.mbta.com/developer/api/v2"

    private RestTemplate restTemplate

    @Autowired
    MBTAService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    @Cacheable(value='routes')
    List<Route> getRoutes() {
        RoutesResponse routesResponse = restTemplate.getForObject("$baseUrl/routes?api_key=$mbtaApiKey&format=json", RoutesResponse.class)

        routesResponse.modes.parallelStream().filter({
            it.modeName == 'Subway'
        }).flatMap({
            it.routes.stream()
        }).collect(Collectors.toList())
    }

    @Cacheable(value='stops')
    List<Stop> getStops() {
        routes.parallelStream().flatMap({
            getStopsByRoute(it.routeId).stream()
        }).flatMap({
            it.stops.stream()
        }).distinct().collect(Collectors.toList())
    }

    @Cacheable(value='stop', key="#stopId")
    Stop getStop(String stopId) {
        stops.parallelStream().filter({ it.stopId == stopId }).findFirst().get()
    }

    @Cacheable(value='stopsByRoute', key="#routeId")
    List<Direction> getStopsByRoute(String routeId) {
        StopsByRouteResponse stopsByRouteResponse = restTemplate.getForObject("$baseUrl/stopsByRoute?api_key=$mbtaApiKey&route={routeId}&format=json", StopsByRouteResponse.class, routeId)
        stopsByRouteResponse.directions
    }

    List<Trip> predictionsByStop(String stopId) {
        PredictionsByStopResponse predictionsByStopResponse = restTemplate.getForObject("$baseUrl/predictionsbystop?api_key=$mbtaApiKey&stop={stopId}&format=json", PredictionsByStopResponse.class, stopId)
        
        predictionsByStopResponse.modes.stream().flatMap({
            it.routes.stream()
        }).flatMap({
            it.directions.stream()
        }).flatMap({
            it.trips.stream()
        }).collect(Collectors.toList())
    }
}
