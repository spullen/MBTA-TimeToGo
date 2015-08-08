package net.scottpullen.config

import groovy.util.logging.Slf4j
import org.springframework.boot.context.embedded.ServletContextInitializer
import org.springframework.context.annotation.Configuration

import javax.servlet.ServletContext
import javax.servlet.ServletException

@Slf4j
@Configuration
class ServiceInitialization implements ServletContextInitializer {

    @Override
    void onStartup(ServletContext servletContext) throws ServletException {
        TimeZone.default = TimeZone.getTimeZone("America/New_York")
        //TimeZone.default.useDaylightTime()
    }
}
