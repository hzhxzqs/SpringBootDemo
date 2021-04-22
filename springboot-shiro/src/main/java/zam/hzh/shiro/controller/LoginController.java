package zam.hzh.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.common.result.MsgEnum;
import zam.hzh.shiro.common.shiro.util.PasswordRetryUtil;

@RestController
@RequestMapping("/shiro")
public class LoginController {

    @Autowired
    private EhCacheManager ehCacheManager;

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    @RequestMapping("/login")
    public Msg login(String username, String password) {
        // 检查账号锁定
        if (PasswordRetryUtil.checkPasswordRetry(username)) {
            return Msg.error(MsgEnum.USER_LOCK);
        }
        // 封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 获取Subject
        Subject subject = SecurityUtils.getSubject();
        try {
            // 通过Subject执行登录，此处将会调用MyShiroRealm的doGetAuthenticationInfo()方法进行登录认证
            subject.login(token);
            // 登录成功后清除锁定记录
            PasswordRetryUtil.clearPasswordRetry(username);
        } catch (UnknownAccountException e) {
            return Msg.error(MsgEnum.USER_NOT_EXIST);
        } catch (IncorrectCredentialsException e) {
            // 密码错误增加错误次数
            if (PasswordRetryUtil.addPasswordRetry(username)) {
                return Msg.error(MsgEnum.USER_LOCK);
            }
            return Msg.error(MsgEnum.PASSWORD_ERROR);
        } catch (DisabledAccountException e) {
            return Msg.error(MsgEnum.USER_DISABLED);
        } catch (Exception e) {
            e.printStackTrace();
            return Msg.error();
        }
        return Msg.success();
    }

    /**
     * 用户退出
     *
     * @return Msg
     */
    @RequestMapping("/loginOut")
    public Msg loginOut() {
        // 执行退出
        SecurityUtils.getSubject().logout();
        return Msg.success();
    }

    /**
     * 修改用户角色或权限时清空缓存，否则授权无法即时生效
     *
     * @return Msg
     */
    @RequestMapping("/clearCache")
    public Msg clearCache() {
        DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
        // Shiro自动为Realm创建权限缓存，缓存名为“Realm的全限定类名.authorizationCache”
        String cacheName = "zam.hzh.shiro.common.shiro.config.MyShiroRealm.authorizationCache";
        // 获取权限缓存
        Cache<Object, Object> cache = securityManager.getCacheManager().getCache(cacheName);
        // 清空缓存
        cache.clear();
        return Msg.success();
    }
}
