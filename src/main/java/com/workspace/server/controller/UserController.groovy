package com.workspace.server.controller

import com.workspace.server.exception.AuthenticationException
import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.interceptor.RequestContentInterceptor
import com.workspace.server.model.TeamEntity
import com.workspace.server.model.UserEntity
import com.workspace.server.security.utils.HMAC
import com.workspace.server.security.utils.JWT
import com.workspace.server.model.WorkspaceStatus
import com.workspace.server.service.TeamEntityService
import com.workspace.server.service.UserEntityService
import com.workspace.server.util.ContentFormatter
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

/**
 * Created by 51998 on 2018/2/28.
 */
@RestController
@RequestMapping('/user')
class UserController {

    @Autowired UserEntityService userEntityService
    @Autowired TeamEntityService teamEntityService
    @Autowired HMAC hmac
    @Autowired JWT jwt

    @RequestMapping('/find')
    String findUser (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def jsonRequestContent) {
        int resultCode = userEntityService.exists(jsonRequestContent.'username') ? AuthenticationException.AuthenticationExceptionCode.USER_EXISTS : AuthenticationException.AuthenticationExceptionCode.USER_NOT_FOUND
        contentFormatter.content().'workspace_content' {
            'result_code' resultCode
            'status_code' WorkspaceStatus.SUCCESS
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

            // generate jwt token
            String jwtToken = jwt?.sign(['id': userEntity?.id, 'username': userEntity?.email, 'hsKey': userEntity?.hsKey, 'hsPassword': userEntity?.hsPassword])
            Cookie cookie = new Cookie('token', jwtToken)
            cookie.setSecure(true)
            cookie.setHttpOnly(true)
            cookie.setDomain('localhost')
            cookie.setPath('/')
            cookie.setMaxAge(60 * 5)
            httpServletResponse.addCookie(cookie)

            // find all teams
            def List<TeamEntity> teamEntityList = teamEntityService?.findAllByAdministratorUserId(userEntity?.id)

            contentFormatter.content().'workspace_content' {
                'user_information' {
                    'email' userEntity.email
                    'name' userEntity.name
                    'phone' userEntity.phone
                    'sex' userEntity.sex
                    'birth' userEntity.birth
                    'address' userEntity.address
                    'create_date_time' userEntity.createDatetime
                    'icon_file_id' userEntity.iconFileId ?: ''
                }
                'teams' teamEntityList, { TeamEntity teamEntity ->
                    'id' teamEntity?.id
                    'name' teamEntity?.name
                    'introduce' teamEntity?.introduce
                    'icon_file_id' teamEntity?.iconFileId
                }
                'status_code' WorkspaceStatus.SUCCESS
            }

        }

        return contentFormatter.toString()
    }

    @PostMapping('/register')
    String register (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def jsonRequestContent) {
        String name = jsonRequestContent.'name'
        String username = jsonRequestContent.'username'
        String password = jsonRequestContent.'password'
        long iconFileId = Long.valueOf(jsonRequestContent.'icon_file_id'.toString())

        UserEntity userEntity = userEntityService.findUserEntityByEmail(username)

        if (userEntity) {
            throw new AuthenticationException(AuthenticationException.AuthenticationExceptionCode.USER_EXISTS, new UserEntity(email: username))
        }

        String hsKey = hmac?.generateKey(HMAC.HMAC_SHA512)

        userEntity = new UserEntity(
                name: name,
                email: username,
                hsKey: hsKey,
                hsPassword: hmac?.digest(password, hsKey, HMAC.HMAC_SHA512),
                iconFileId: iconFileId
        )

        userEntityService?.save(userEntity)

        contentFormatter.content().'workspace_content' {
            'status_code' WorkspaceStatus.SUCCESS
        }

        return contentFormatter.toString()
    }

    @RequestMapping('/test')
    String test (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def jsonRequestContent) {
        Subject subject = SecurityUtils.getSubject()
        println subject.getPrincipal()
        println subject.isAuthenticated()
        println jwt?.getId(subject.getPrincipal()?.toString())
        contentFormatter.content().'workspace_content' {
            'status_code' WorkspaceStatus.SUCCESS
        }
        return contentFormatter.toString()
    }

}
