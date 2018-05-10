package com.workspace.server.filter

import com.workspace.server.security.token.WorkspaceToken
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter extends BasicHttpAuthenticationFilter {

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request
        String jwtToken = httpServletRequest?.getCookies()?.find { it?.name == 'token' }?.value
        WorkspaceToken token = new WorkspaceToken(token: jwtToken)
        getSubject(request, response).login(token)
        return true
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        try {
            executeLogin(request, response)
        } catch (Exception e) {
            ((HttpServletResponse) response).sendRedirect("/access/denied")
        }
        return true
    }

}