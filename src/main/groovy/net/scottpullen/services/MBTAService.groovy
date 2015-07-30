package net.scottpullen.services

import groovy.util.logging.Slf4j
import net.scottpullen.mbta.Route
import net.scottpullen.mbta.RoutesResponse
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

    @Cacheable(value="routes")
    List<Route> getRoutes() {
        RoutesResponse routesResponse = this.restTemplate.getForObject("$baseUrl/routes?api_key=$mbtaApiKey&format=json", RoutesResponse.class)

        routesResponse.modes.parallelStream().filter({
            it.modeName == 'Subway'
        }).flatMap({
            it.routes.stream()
        }).collect(Collectors.toList())
    }

    def getStopsByRoute(String routeId) {

    }
}
