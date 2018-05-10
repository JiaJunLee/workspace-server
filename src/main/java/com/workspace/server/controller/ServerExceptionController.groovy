package com.workspace.server.controller

import com.workspace.server.exception.ServerException
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by LIBU on 5/3/2018.
 */
@RestController
@RequestMapping('/access')
class ServerExceptionController {

    @RequestMapping('/denied')
    String requestAccessDenied () {
        throw new ServerException(ServerException.ServerExceptionCode.ACCESS_DENIED)
    }

    @RequestMapping('/restricted')
    String requestAccessRestricted () {
        throw new ServerException(ServerException.ServerExceptionCode.ACCESS_RESTRICTED)
    }

}
