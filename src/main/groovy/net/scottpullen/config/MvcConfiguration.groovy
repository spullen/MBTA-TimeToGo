package net.scottpullen.config

import groovy.transform.CompileStatic
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.resource.GzipResourceResolver

@EnableWebMvc
@Configuration
@CompileStatic
class MvcConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/")
        registry.addResourceHandler("/javascripts/**").addResourceLocations("/javascripts/")
        registry.addResourceHandler("/stylesheets/**").addResourceLocations("/stylesheets/")
        registry.addResourceHandler("/images/**").addResourceLocations("/images/")
    }
}
