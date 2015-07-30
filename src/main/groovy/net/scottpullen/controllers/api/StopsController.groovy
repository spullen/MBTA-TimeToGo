package net.scottpullen.controllers.api

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping(value='/routes/{routeId}')
class StopsController extends ApiBaseController {


    @RequestMapping(value='/routes/{routeId}/stops')
    @ResponseBody def index() {

    }
}
