package net.scottpullen.mbta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class Vehicle {
    @JsonProperty('vehicle_id')
    String vehicleId

    @JsonProperty('vehicle_lat')
    BigDecimal vehicleLat

    @JsonProperty('vehicle_lon')
    BigDecimal vehicleLon

    @JsonProperty('vehicle_bearing')
    Integer vehicleBearing

    @JsonProperty('vehicle_timestamp')
    Long vehicleTimestamp
}
