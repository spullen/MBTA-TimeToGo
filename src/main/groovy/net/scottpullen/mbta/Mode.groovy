package net.scottpullen.mbta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

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
