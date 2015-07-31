package net.scottpullen.mbta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class Direction {

    @JsonProperty('direction_id')
    String directionId

    @JsonProperty('direction_name')
    String directionName

    @JsonProperty('stop')
    List<Stop> stops

    @JsonProperty('trip')
    List<Trip> trips
}
