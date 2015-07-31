package net.scottpullen.mbta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class PredictionsByStopResponse {

    @JsonProperty('stop_id')
    String stopId

    @JsonProperty('stop_name')
    String stopName

    //@JsonProperty('mode')
    //List<Mode> modes

    //@JsonProperty('alert_headers')
    //List<AlertHeader> alertHeaders
}
