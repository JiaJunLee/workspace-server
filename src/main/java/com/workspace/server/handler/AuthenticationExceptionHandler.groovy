package com.workspace.server.handler

import com.workspace.server.exception.AuthenticationException
import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.model.WorkspaceStatus
import com.workspace.server.util.ContentFormatter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Created by 51998 on 2018/3/6.
 */
@ControllerAdvice
class AuthenticationExceptionHandler {

    @Autowired MessageSource messageSource

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    String handler(@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, AuthenticationException authenticationException){
        contentFormatter.content().'workspace_content' {
            'result_code' authenticationException.exceptionCode
            'exception_information' messageSource.getMessage(authenticationException.toString(), null, LocaleContextHolder.getLocale())
            'user_information' {
                'email' authenticationException?.userEntity?.email
            }
            'status_code' WorkspaceStatus.FAIL
        }
        return contentFormatter.toString()
    }

}
