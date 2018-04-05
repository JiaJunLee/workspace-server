package com.workspace.server.configuration

import com.workspace.server.filter.StatelessFilter
import com.workspace.server.security.factory.StatelessSubjectFactory
import com.workspace.server.security.realm.WorkspaceUserRealm
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator
import org.apache.shiro.mgt.DefaultSubjectDAO
import org.apache.shiro.session.mgt.DefaultSessionManager
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by 51998 on 2018/2/20.
 */
@Configuration
class ShiroConfiguration {

    @Bean
    public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean()
        shiroFilterFactoryBean.setSecurityManager(securityManager)
//        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
//        // 配置不会被拦截的链接 顺序判断
//        filterChainDefinitionMap.put("/static/**", "anon");
//        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
//        filterChainDefinitionMap.put("/logout", "logout");
//        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
//        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/**", "authc");
//        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
//
//        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //Add.4.2.start
        shiroFilterFactoryBean.getFilters().put("statelessAuthc", statelessAuthcFilter());
//        //拦截器.
//        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
        filterChainDefinitionMap.put("/**", "statelessAuthc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        //Add.4.2.end

        ((DefaultSessionStorageEvaluator)((DefaultSubjectDAO)securityManager.getSubjectDAO()).getSessionStorageEvaluator()).setSessionStorageEnabled(false)


        return shiroFilterFactoryBean;
    }

    @Bean
    public StatelessFilter statelessAuthcFilter(){
        return new StatelessFilter()
    }

    @Bean
    public WorkspaceUserRealm workspaceUserRealm(){
        return new WorkspaceUserRealm()
    }

    @Bean
    public DefaultWebSubjectFactory subjectFactory(){
        return new StatelessSubjectFactory()
    }

    @Bean
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager()
        securityManager.setSubjectFactory(subjectFactory())
        securityManager.setRealm(workspaceUserRealm())
        securityManager.setSessionManager(sessionManager())
        return securityManager
    }

    @Bean
    public DefaultSessionManager sessionManager(){
        DefaultSessionManager sessionManager = new DefaultSessionManager()
        sessionManager.setSessionValidationSchedulerEnabled(false)
        return sessionManager
    }

}
