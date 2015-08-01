package net.scottpullen.controllers

import groovy.transform.CompileStatic
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@CompileStatic
class HomeController {

    @RequestMapping("/")
    ModelAndView index() {
        new ModelAndView('home')
    }
}
