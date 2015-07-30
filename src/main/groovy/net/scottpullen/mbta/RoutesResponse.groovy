package net.scottpullen.mbta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class RoutesResponse {

    @JsonProperty('mode')
    List<Mode> modes
}

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class Mode {

    @JsonProperty('route_type')
    String routeType

    @JsonProperty('mode_name')
    String modeName

    @JsonProperty('route')
    List<Route> routes
}

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class Route {

    @JsonProperty('route_id')
    String routeId

    @JsonProperty('route_name')
    String routeName
}
