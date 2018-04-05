package com.workspace.server.configuration

import com.workspace.server.interceptor.RequestContentInterceptor
import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

/**
 * Created by 51998 on 2018/2/28.
 */
@Configuration
@Order(4)
@Slf4j
class RequestContentConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    RequestContentInterceptor requestContentInterceptor () {
        return new RequestContentInterceptor()
    }

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        log.info('[workspace-server] Initialize Request Content Interceptor')
        registry.addInterceptor(requestContentInterceptor())
    }

}
