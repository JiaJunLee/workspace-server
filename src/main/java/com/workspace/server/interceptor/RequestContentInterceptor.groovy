package com.workspace.server.interceptor

import com.workspace.server.exception.ContentFormatException
import com.workspace.server.exception.ServerException
import com.workspace.server.security.utils.rsa.RSA
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by 51998 on 2018/2/28.
 */
@Component
@Slf4j
class RequestContentInterceptor implements HandlerInterceptor {

    private static JsonSlurper jsonSlurper = new JsonSlurper()
    public static final String JSON_CONTENT = 'JSON_CONTENT'

    @Override
    boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        log.info('[workspace-server] Decode request content')
        Map parameterMap = httpServletRequest?.getParameterMap()
        if (parameterMap[JSON_CONTENT]) {
            try {
                httpServletRequest.setAttribute(JSON_CONTENT, jsonSlurper.parseText(RSA.getRsaDecoder().decode(parameterMap[JSON_CONTENT])))
            } catch (Exception e) {
                log.error("[workspace-server] Decode Error, Details: ${e.toString()}")
                throw new ContentFormatException(ContentFormatException.ContentFormatExceptionCode.CONTENT_DECODE_EXCEPTION)
            }
        } else {
            if (httpServletRequest.getRequestURI() != '/public_key') {
                throw new ServerException(ServerException.ServerExceptionCode.UNKNOWN_REQUEST)
            }
        }
        return true
    }

    @Override
    void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

}
