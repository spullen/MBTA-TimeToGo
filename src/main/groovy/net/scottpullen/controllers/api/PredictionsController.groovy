package net.scottpullen.controllers.api

import com.google.maps.model.LatLng
import com.google.maps.model.TravelMode
import groovy.util.logging.Slf4j
import net.scottpullen.services.PredictionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Slf4j
@Controller
class PredictionsController extends ApiBaseController {

    private PredictionService predictionService

    @Autowired
    StopsController(PredictionService predictionService) {
        this.predictionService = predictionService
    }

    // 1.5s
    @RequestMapping(value='/predictions/{stopId}', method=RequestMethod.GET, produces='application/json')
    @ResponseBody List index(@PathVariable String stopId) {
        predictionService.determineTimesToGo(stopId, new LatLng(42.3678149, -71.0900598), TravelMode.WALKING)
    }
}
