package net.scottpullen.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.ehcache.EhCacheCacheManager
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource

@EnableCaching
@Configuration
@Profile("production")
class CacheConfiguration {

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
