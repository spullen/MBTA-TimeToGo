package net.scottpullen.config

import com.google.maps.GeoApiContext
import org.springframework.cache.CacheManager
import org.springframework.cache.ehcache.EhCacheCacheManager
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.web.client.RestTemplate

@Configuration
class BeanConfiguration {

    @Bean
    GeoApiContext geoApiContext() {
        new GeoApiContext().setApiKey(System.getenv('GOOGLE_GEOCODE_API_KEY'))
    }

    @Bean
    RestTemplate restTemplate() {
        new RestTemplate()
    }

    @Bean
    CacheManager getEhCacheManager(){
        new EhCacheCacheManager(getEhCacheFactory().object)
    }
    @Bean
    EhCacheManagerFactoryBean getEhCacheFactory(){
        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean()
        factoryBean.configLocation = new ClassPathResource("ehcache.xml")
        factoryBean.shared = true
        factoryBean
    }
}
