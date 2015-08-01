package net.scottpullen.controllers.api

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import net.scottpullen.mbta.Direction
import net.scottpullen.mbta.Stop
import net.scottpullen.services.MBTAService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Slf4j
@Controller
@CompileStatic
class StopsController extends ApiBaseController {

    private MBTAService mbtaService

    @Autowired
    StopsController(MBTAService mbtaService) {
        this.mbtaService = mbtaService
    }

    @RequestMapping(value='/stops', method=RequestMethod.GET, produces='application/json')
    @ResponseBody List<Stop> stops() {
        mbtaService.stops
    }

    @RequestMapping(value='/stops/{stopId}', method=RequestMethod.GET, produces='application/json')
    @ResponseBody Stop stop(@PathVariable String stopId) {
        mbtaService.getStop(stopId)
    }

    @RequestMapping(value='/routes/{routeId}/stops', method=RequestMethod.GET, produces='application/json')
    @ResponseBody List<Direction> routesByStop(@PathVariable String routeId) {
        mbtaService.getStopsByRoute(routeId)
    }
}
