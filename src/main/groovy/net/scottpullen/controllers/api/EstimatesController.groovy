package net.scottpullen.controllers.api

import com.google.maps.model.LatLng
import groovy.util.logging.Slf4j
import net.scottpullen.commands.Estimate
import net.scottpullen.services.EstimationService
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
class EstimatesController extends ApiBaseController {

    private EstimationService estimationService

    @Autowired
    StopsController(EstimationService predictionService) {
        this.estimationService = predictionService
    }

    @RequestMapping(value='/estimations/{stopId}', method=RequestMethod.GET, produces='application/json')
    @ResponseBody List index(@PathVariable String stopId, @ModelAttribute("estimate") Estimate estimate, BindingResult bindingResult) {
        estimationService.determineTimesToGo(stopId, new LatLng(estimate.latitude, estimate.longitude), estimate.travelMode)
    }
}
