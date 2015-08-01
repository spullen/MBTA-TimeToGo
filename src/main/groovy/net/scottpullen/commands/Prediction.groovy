package net.scottpullen.commands

import com.google.maps.model.TravelMode

class Prediction {
    BigDecimal latitude
    BigDecimal longitude
    TravelMode travelMode

    void setTravelMode(String travelMode) {
        switch(travelMode) {
            case "driving":
                this.travelMode = TravelMode.DRIVING
                break;
            case "walking":
            default:
                this.travelMode = TravelMode.WALKING
                break
        }
    }
}
