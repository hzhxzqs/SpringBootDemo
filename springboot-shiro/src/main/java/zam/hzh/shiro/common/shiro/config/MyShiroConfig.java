package zam.hzh.shiro.common.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zam.hzh.shiro.common.shiro.util.PasswordHelper;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro核心配置类
 */
@Configuration
public class MyShiroConfig {

    /**
     * 配置shiro过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager());

        // 设置用户登录后，未授权时跳转界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/page/unauthorized.html");
        // 设置登录界面，当用户未登录且访问资源不符合过滤链定义时跳转到该界面
        shiroFilterFactoryBean.setLoginUrl("/page/login.html");

        // 添加过滤链定义，从上到下匹配
        // key值表示访问的资源，value表示过滤器（多个过滤器用','隔开）
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 需要认证
        filterChainDefinitionMap.put("/page/authc.html", "authc");
        // 需要admin角色
        filterChainDefinitionMap.put("/page/admin1.html", "roles[admin]");
        // 需要look权限
        filterChainDefinitionMap.put("/page/look1.html", "perms[look]");
        // 可匿名访问
        filterChainDefinitionMap.put("/page/**", "anon");

        // 配置静态资源
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/lib/**", "anon");
        // 其它资源
        filterChainDefinitionMap.put("/**", "anon");

        // 设置过滤链
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置SecurityManager
     */
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置Realm
        securityManager.setRealm(myShiroRealm());
        // 设置缓存管理器，启用缓存
        securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }

    /**
     * 配置Realm
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        // 设置认证匹配器，用户登录时使用
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * 配置认证匹配器
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 设置加密算法
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordHelper.ALGORITHM_NAME);
        // 设置加密次数
        hashedCredentialsMatcher.setHashIterations(PasswordHelper.HASH_ITERATIONS);
        return hashedCredentialsMatcher;
    }

    /**
     * 配置缓存管理器
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        // 设置ehcache配置文件
        ehCacheManager.setCacheManagerConfigFile("classpath:config/shiro-ehcache.xml");
        return ehCacheManager;
    }

    /**
     * 开启自动代理
     * <p>
     * <br>当控制类中使用@RequiresRoles、@RequiresPermissions等注解时需要设置，
     * <br>否则将导致控制器请求url出现404 Not Found异常
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 开启@RequiresRoles、@RequiresPermissions等权限注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }
}
