package com.example.kondus.Kondus_api.modules.auth.config

import com.example.kondus.Kondus_api.modules.auth.service.UserDetailsServiceImpl
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class Configuration {

    @Bean
    fun userDetailsService() = UserDetailsServiceImpl()

    
}