package net.scottpullen.config

import com.google.maps.GeoApiContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfiguration {

    @Bean
    GeoApiContext geoApiContext() {
        new GeoApiContext().setApiKey(System.getenv('GOOGLE_GEOCODE_API_KEY'));
    }
}
