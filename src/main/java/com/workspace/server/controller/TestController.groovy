package com.workspace.server.controller

import com.workspace.server.filter.RequestFilter
import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.interceptor.RequestContentInterceptor
import com.workspace.server.model.UserEntity
import com.workspace.server.security.token.WorkspaceUserToken
import com.workspace.server.security.utils.HMAC
import com.workspace.server.security.utils.rsa.RSA
import com.workspace.server.service.UserEntityService
import com.workspace.server.service.UserService
import com.workspace.server.util.ContentFormatter
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.DefaultClaims
import io.jsonwebtoken.impl.crypto.MacProvider
import org.apache.shiro.SecurityUtils
import org.apache.shiro.subject.Subject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import java.security.Key
import java.util.concurrent.TimeUnit

@RestController
class TestController {

    @Autowired MessageSource messageSource
    @Autowired UserService userService
    @Autowired StringRedisTemplate redisTemplate
    @Autowired UserEntityService userEntityService
    @Autowired HMAC hmac

    @RequestMapping('/')
    String hello(@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter, @RequestAttribute(value = RequestContentInterceptor.JSON_CONTENT) def json){
        contentFormatter.content().'workspace_content'{
            'result_code' '200'
            'state' 'Server is running'
        }
        println json
        return contentFormatter.toString()
    }

//    @RequestMapping('/{username}')
//    String user(@PathVariable String username) throws Exception{
//        return userService.getUser(username)?.password
//    }

    @GetMapping('/user/{email}/{password}')
    String user(@PathVariable String email, @PathVariable String password) throws Exception {
        System.err.println(email)
        System.err.println(password)
        UserEntity userEntity = userEntityService.findUserEntityByEmail(email)
        return hmac.validate(password, userEntity.hsKey, HMAC.HMAC_SHA512, userEntity.hsPassword)
    }

    @GetMapping('/update')
    String update() throws Exception {
        return userService.updateUsers()
    }

    static Key key = MacProvider.generateKey()
    @GetMapping('/test')
    String redisTest() throws Exception {
        def map = ['first_name': 'JiaJun', 'last_name': 'Li', 'time': System.currentTimeMillis()]
        String token = Jwts.builder()
                        .setSubject('admin')
                        .setClaims(map)
                        .signWith(SignatureAlgorithm.HS512, key)
                        .compact()
        return "TOKEN: ${token}"
    }

}
