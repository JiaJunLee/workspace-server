package com.workspace.server.security.token

import org.apache.shiro.authc.AuthenticationToken

/**
 * Created by 51998 on 2018/2/22.
 */
class WorkspaceHMACToken implements AuthenticationToken {

    private String clientUUID
    private Map<String, String[]> requestParameters
    private String digest
    private String timestamp
    private String host
    private String hmacKey

    @Override
    Object getPrincipal() {
        return null
    }

    @Override
    Object getCredentials() {
        return null
    }
}
