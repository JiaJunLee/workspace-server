package com.workspace.server.handler

import com.workspace.server.exception.ServerException
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

@ControllerAdvice
class ServerExceptionHandler {

    @Autowired MessageSource messageSource

    @ExceptionHandler(ServerException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    String handler(@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, ServerException serverException){
        contentFormatter.content().'workspace_content' {
            'result_code' serverException.exceptionCode
            'exception_information' messageSource.getMessage(serverException.toString(), null, LocaleContextHolder.getLocale())
            'status_code' WorkspaceStatus.FAIL
        }
        return contentFormatter.toString()
    }

}
