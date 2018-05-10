package com.workspace.server.security.token

import org.apache.shiro.authc.AuthenticationToken

/**
 * Created by LIBU on 5/2/2018.
 */
class WorkspaceToken implements AuthenticationToken {

    String token

    @Override
    Object getPrincipal() {
        return this.token
    }

    @Override
    Object getCredentials() {
        return this.token
    }

}
