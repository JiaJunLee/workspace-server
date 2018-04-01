package com.workspace.server.configuration

import groovy.util.logging.Slf4j
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.i18n.CookieLocaleResolver
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor

@Configuration
@Order(2)
@Slf4j
class I18nConfiguration extends WebMvcConfigurerAdapter {

    public static final String I18N_PARAM_NAME = 'lang'

    @Bean
    LocaleResolver localeResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver()
        cookieLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE)
        return cookieLocaleResolver
    }

    @Bean
    LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor()
        lci.setParamName(I18N_PARAM_NAME)
        return lci
    }

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        log.info('[workspace-server] Registration I18N Interceptor')
        registry.addInterceptor(localeChangeInterceptor())
    }

}
