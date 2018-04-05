package com.workspace.server.security.token

import org.apache.shiro.authc.UsernamePasswordToken

/**
 * Created by 51998 on 2018/2/20.
 */
class WorkspaceUserToken extends UsernamePasswordToken {

    String clientKey
    String digest
}
