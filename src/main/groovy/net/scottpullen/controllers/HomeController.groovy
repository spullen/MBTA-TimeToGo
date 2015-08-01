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
}
