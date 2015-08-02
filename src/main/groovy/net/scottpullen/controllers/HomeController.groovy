package net.scottpullen.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import net.scottpullen.mbta.Route
import net.scottpullen.services.MBTAService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.ModelAndView

@Slf4j
@Controller
@CompileStatic
class HomeController {

    private MBTAService mbtaService

    @Autowired
    HomeController(MBTAService mbtaService) {
        this.mbtaService = mbtaService
    }

    @RequestMapping("/")
    ModelAndView index() {
        List<Route> routes = mbtaService.getRoutes()
        ObjectWriter objectWriter = new ObjectMapper().writer()
        String routesJSON = objectWriter.writeValueAsString(routes)
        new ModelAndView('home', [
                routesJSON: routesJSON
        ])
    }

    // Workaround: wanted a catch all for home (/**) because the client side app routing should take over,
    //             but the assets don't play nice with it (they would always return the home page instead of the requested asset)
    // So, instead anything that should go to home on the client side app should be explicitly declared and just forwarded
    @RequestMapping("/routes/{routeId}")
    String routes(final WebRequest request) {
        return "forward:/"
    }
}
