package com.workspace.server.filter

import com.workspace.server.security.token.WorkspaceUserToken
import groovy.util.logging.Slf4j
import org.apache.shiro.web.filter.AccessControlFilter
import org.springframework.stereotype.Component

import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.annotation.WebFilter
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by 51998 on 2018/2/21.
 */
@Slf4j
class StatelessFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse
        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest
        httpServletRequest.getHeaders('Cookie').each { System.err.println(it) }
        Cookie cookie = new Cookie('tok3', 'tokkken')
        cookie.setHttpOnly(true)
        cookie.setSecure(true)
        cookie.setMaxAge(1000 * 60 * 5)
        httpServletResponse.addCookie(cookie)
        httpServletRequest.getCookies()
        log.info('[workspace-server] on access denied')

        WorkspaceUserToken workspaceUserToken = new WorkspaceUserToken()
        workspaceUserToken.setUsername('test')
        workspaceUserToken.setPassword('test123'.toCharArray())
        try {
            getSubject(servletRequest, servletResponse).login(workspaceUserToken)
        } catch (Exception e) {
            e.printStackTrace()
            return false
        }
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse
//        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
//        httpResponse.getWriter().write("login error")
        return true
    }

}
