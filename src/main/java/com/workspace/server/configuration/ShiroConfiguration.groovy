package com.workspace.server.configuration

import com.workspace.server.filter.AuthenticationFilter
import com.workspace.server.security.realm.WorkspaceRealm
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator
import org.apache.shiro.mgt.DefaultSubjectDAO
import org.apache.shiro.spring.LifecycleBeanPostProcessor
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import javax.servlet.Filter

@Configuration
class ShiroConfiguration {

    @Bean("securityManager")
    DefaultWebSecurityManager getManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager()
        manager.setRealm(workspaceRealm())
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO()
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator()
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false)
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator)
        manager.setSubjectDAO(subjectDAO)
        return manager
    }

    @Bean
    WorkspaceRealm workspaceRealm () {
        return new WorkspaceRealm()
    }

    @Bean("shiroFilter")
    ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean()
        Map<String, Filter> filterMap = new HashMap<>()
        filterMap.put("AuthenticationFilter", new AuthenticationFilter())
        factoryBean.setFilters(filterMap)
        factoryBean.setSecurityManager(securityManager)

        Map<String, String> filterRuleMap = new HashMap<>()
        filterRuleMap.put("/*/get", "AuthenticationFilter")
        filterRuleMap.put("/*/find-all", "AuthenticationFilter")
        filterRuleMap.put("/*/create", "AuthenticationFilter")
        filterRuleMap.put("/*/test", "AuthenticationFilter")
        filterRuleMap.put("/search/*", "AuthenticationFilter")
//        filterRuleMap.put('/public_key', 'anon')
//        filterRuleMap.put('/access_denied', 'anon')
//        filterRuleMap.put("/access_restricted", 'anon')
        factoryBean.setFilterChainDefinitionMap(filterRuleMap)
        return factoryBean
    }


    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator()
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true)
        return defaultAdvisorAutoProxyCreator
    }

    @Bean
    LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor()
    }

    @Bean
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor()
        advisor.setSecurityManager(securityManager)
        return advisor
    }

}