package net.scottpullen.mbta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class Trip {
    @JsonProperty('trip_id')
    String tripId

    @JsonProperty('trip_name')
    String tripName

    @JsonProperty('trip_headsign')
    String tripHeadsign

    @JsonProperty('sch_arr_dt')
    Long scheduledArrivalDt

    @JsonProperty('sch_dep_dt')
    Long scheduledDepartureDt

    @JsonProperty('pre_dt')
    Long preDt

    @JsonProperty('pre_away')
    Integer preAway

    /*@JsonProperty('vehicle')
    Vehicle vehicle*/
}