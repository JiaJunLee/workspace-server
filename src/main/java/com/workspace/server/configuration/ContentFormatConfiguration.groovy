package com.workspace.server.configuration

import com.workspace.server.exception.ContentFormatException
import com.workspace.server.interceptor.ContentFormatInterceptor
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@Order(3)
@Slf4j
class ContentFormatConfiguration extends WebMvcConfigurerAdapter {

    /**
     * support content format
     */
    @Value('${workspace.server.output-format-types}')
    private String outputFormatTypes

    @Value('${workspace.server.default-output-format-type}')
    private String defaultOutputFormatType

    @Bean
    ContentFormatInterceptor contentFormatInterceptor() {
        ContentFormatInterceptor contentFormatInterceptor = new ContentFormatInterceptor()
        contentFormatInterceptor.setOutputFormatTypes(outputFormatTypes)
        contentFormatInterceptor.setDefaultOutputFormatType(defaultOutputFormatType)
        return contentFormatInterceptor
    }

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        if(defaultOutputFormatType in outputFormatTypes.split(',')){
            log.info("[workspace-server] Support Content Format Types: ${outputFormatTypes.split(',')}")
            log.info("[workspace-server] Default Content Format Type: ${defaultOutputFormatType}")
        } else{
            log.error("[workspace-server] Default Format Type: ${defaultOutputFormatType} Is Not In Support Content Format Types, Please Check Configuration!")
            throw new ContentFormatException()
        }

        log.info('[workspace-server] Registration Content Format Interceptor')
        registry.addInterceptor(contentFormatInterceptor())
    }

}
