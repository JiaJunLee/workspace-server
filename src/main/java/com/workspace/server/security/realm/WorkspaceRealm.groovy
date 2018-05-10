package com.workspace.server.security.realm

import com.workspace.server.exception.ServerException
import com.workspace.server.model.UserEntity
import com.workspace.server.security.token.WorkspaceToken
import com.workspace.server.security.utils.JWT
import com.workspace.server.service.UserEntityServiceForRealm
import groovy.util.logging.Slf4j
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by LIBU on 5/2/2018.
 */
@Slf4j
class WorkspaceRealm extends AuthorizingRealm {

    @Autowired JWT jwt
    @Autowired UserEntityServiceForRealm userEntityServiceForRealm

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws Exception {

        log.info('[workspace-server] User Login')
        String jwtToken = (String) authenticationToken?.getCredentials()
        String username = jwt?.getUsername(jwtToken)

        if (username == null) {
            throw new ServerException(ServerException.ServerExceptionCode.ACCESS_RESTRICTED)
        }

        UserEntity userEntity = userEntityServiceForRealm.findUserEntityByEmail(username)

        if (userEntity == null) {
            throw new com.workspace.server.exception.AuthenticationException(com.workspace.server.exception.AuthenticationException.AuthenticationExceptionCode.USER_NOT_FOUND, new UserEntity(email: username))
        }

        if (! jwt.verify(jwtToken, ['id': userEntity?.id, 'username': userEntity?.email, 'hsKey': userEntity?.hsKey, 'hsPassword': userEntity?.hsPassword])) {
            throw new com.workspace.server.exception.AuthenticationException(com.workspace.server.exception.AuthenticationException.AuthenticationExceptionCode.USER_VALIDATE_FAIL, new UserEntity(email: username))
        }

        return new SimpleAuthenticationInfo(jwtToken, jwtToken, this.getName())
    }

    @Override
    boolean supports(AuthenticationToken token) {
        return token instanceof WorkspaceToken
    }

    @Override
    String getName() {
        return 'WORKSPACE-REALM'
    }

}
