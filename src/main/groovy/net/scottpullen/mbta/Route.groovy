package net.scottpullen.mbta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class Route {

    @JsonProperty('route_id')
    String routeId

    @JsonProperty('route_name')
    String routeName

    @JsonProperty('direction')
    List<Direction> directions
}
