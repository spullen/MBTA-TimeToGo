package net.scottpullen.controllers.api

import com.google.maps.model.LatLng
import com.google.maps.model.TravelMode
import groovy.util.logging.Slf4j
import net.scottpullen.commands.Prediction
import net.scottpullen.services.PredictionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
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

    @RequestMapping(value='/predictions/{stopId}', method=RequestMethod.GET, produces='application/json')
    @ResponseBody List index(@PathVariable String stopId, @ModelAttribute("prediction") Prediction prediction, BindingResult bindingResult) {
        predictionService.determineTimesToGo(stopId, new LatLng(prediction.latitude, prediction.longitude), prediction.travelMode)
    }
}
