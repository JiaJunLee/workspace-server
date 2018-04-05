package com.workspace.server.controller

import com.workspace.server.interceptor.ContentFormatInterceptor
import com.workspace.server.security.utils.WorkspaceStatus
import com.workspace.server.security.utils.rsa.RSA
import com.workspace.server.util.ContentFormatter
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RestController

/**
 * Created by 51998 on 2018/2/28.
 */
@RestController
class RSAController {

    @PostMapping('/public_key')
    String getPublicKey (@RequestAttribute(value = ContentFormatInterceptor.CONTENT_FORMATTER) ContentFormatter contentFormatter) {
        contentFormatter.content().'workspace_content' {
            'result_code' WorkspaceStatus.SUCCESS
            'public_key' RSA.getPublicKey()
        }
        return contentFormatter.toString()
    }

}
