package com.workspace.server.security.realm

import com.workspace.server.security.token.WorkspaceUserToken
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection

/**
 * Created by 51998 on 2018/2/20.
 */
class WorkspaceUserRealm extends AuthorizingRealm {

    @Override
    String getName() {
        return 'WORKSPACE-USER-REALM'
    }

    @Override
    boolean supports(AuthenticationToken token) {
        return token instanceof WorkspaceUserToken
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.err.println("LOGIN")
        return new SimpleAuthenticationInfo('test', 'test123', getName())
    }

}
