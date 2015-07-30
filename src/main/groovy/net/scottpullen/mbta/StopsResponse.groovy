package net.scottpullen.mbta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class StopsResponse {

    @JsonProperty('direction')
    List<Direction> directions
}

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class Direction {

    @JsonProperty('direction_id')
    String directionId

    @JsonProperty('direction_name')
    String directionName

    @JsonProperty('stop')
    List<Stop> stops
}

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class Stop {

    @JsonProperty('stop_order')
    String stopOrder

    @JsonProperty('stop_id')
    String stopId

    @JsonProperty('stop_name')
    String stopName

    @JsonProperty('parent_station')
    String parentStation

    @JsonProperty('parent_station_name')
    String parentStationName

    @JsonProperty('stop_lat')
    BigDecimal stopLat

    @JsonProperty('stop_lon')
    BigDecimal stopLon
}
