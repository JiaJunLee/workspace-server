package com.workspace.server.security.factory

import groovy.util.logging.Slf4j
import org.apache.shiro.subject.Subject
import org.apache.shiro.subject.SubjectContext
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory

/**
 * Created by 51998 on 2018/2/21.
 */
@Slf4j
class StatelessSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context) {
        log.info('[workspace-server] stateless enable, session creation disable')
        context.setSessionCreationEnabled(false)
        return super.createSubject(context)
    }

}
