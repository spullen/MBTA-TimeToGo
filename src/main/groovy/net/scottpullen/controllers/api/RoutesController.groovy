package net.scottpullen.controllers.api

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import net.scottpullen.mbta.Route
import net.scottpullen.services.MBTAService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Slf4j
@Controller
@CompileStatic
class RoutesController extends ApiBaseController {

    private MBTAService mbtaService

    @Autowired
    RoutesController(MBTAService mbtaService) {
        this.mbtaService = mbtaService
    }

    @RequestMapping(value='/routes', method=RequestMethod.GET, produces = 'application/json')
    @ResponseBody List<Route> index() {
        mbtaService.routes
    }
}
