package com.workspace.server.controller

import com.workspace.server.exception.AuthenticationException
import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.interceptor.RequestContentInterceptor
import com.workspace.server.model.UserEntity
import com.workspace.server.security.utils.HMAC
import com.workspace.server.security.utils.WorkspaceStatus
import com.workspace.server.service.UserEntityService
import com.workspace.server.util.ContentFormatter
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.crypto.MacProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse
import java.security.Key

/**
 * Created by 51998 on 2018/2/28.
 */
@RestController
class UserController {

    @Autowired UserEntityService userEntityService
    @Autowired HMAC hmac
    private static Key key = MacProvider.generateKey()

    @RequestMapping('/user/find')
    String findUser (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def jsonRequestContent) {
        int resultCode = userEntityService.exists(jsonRequestContent.'username') ? WorkspaceStatus.SUCCESS : AuthenticationException.AuthenticationExceptionCode.USER_NOT_FOUND
        contentFormatter.content().'workspace_content' {
            'result_code' resultCode
        }
        return contentFormatter.toString()
    }

    @PostMapping('/authentication')
    String authentication (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def jsonRequestContent, HttpServletResponse httpServletResponse) {
        String username = jsonRequestContent.'username'
        String password = jsonRequestContent.'password'

        UserEntity userEntity = userEntityService.findUserEntityByEmail(username)

        if (userEntity == null) {
            throw new AuthenticationException(AuthenticationException.AuthenticationExceptionCode.USER_NOT_FOUND, new UserEntity(email: username))
        }

        if (!hmac.validate(password, userEntity?.hsKey, HMAC.HMAC_SHA512, userEntity?.hsPassword)) {
            throw new AuthenticationException(AuthenticationException.AuthenticationExceptionCode.USER_VALIDATE_FAIL, new UserEntity(email: username))
        } else {

            def map = ['id': userEntity.id, 'timestamp': System.currentTimeMillis()]
            String jwtToken = Jwts.builder()
                    .setSubject(userEntity.email)
                    .setClaims(map)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact()
            Cookie cookie = new Cookie('token', jwtToken)
            cookie.setSecure(true)
            cookie.setHttpOnly(true)
            cookie.setDomain('localhost')
            cookie.setMaxAge(60 * 60 * 2)
            httpServletResponse.addCookie(cookie)

            contentFormatter.content().'workspace_content' {
                'user_information' {
                    'email' userEntity.email
                    'name' userEntity.name
                    'phone' userEntity.phone
                    'sex' userEntity.sex
                    'birth' userEntity.birth
                    'address' userEntity.address
                    'create_date_time' userEntity.createDatetime
                }
                'result_code' WorkspaceStatus.SUCCESS
            }

        }

        return contentFormatter.toString()
    }

}
