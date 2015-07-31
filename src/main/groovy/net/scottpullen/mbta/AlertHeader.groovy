package net.scottpullen.mbta

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.CompileStatic

@CompileStatic
@JsonIgnoreProperties(ignoreUnknown = true)
class AlertHeader {

    @JsonProperty('alert_id')
    String alertId

    @JsonProperty('header_text')
    String headerText

    @JsonProperty('effect_name')
    String effectName
}
