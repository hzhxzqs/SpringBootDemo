package zam.hzh.shiro.common.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 密码重试次数检查
 */
public class PasswordRetryUtil {

    private final static String PASSWORD_RETRY_CACHE = "passwordRetryCache";

    /**
     * 增加密码错误次数，当次数达到5次时返回true
     *
     * @param username 用户名
     * @return 是否出错达到5次
     */
    public static boolean addPasswordRetry(String username) {
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Cache<Object, AtomicInteger> cache = securityManager.getCacheManager().getCache(PASSWORD_RETRY_CACHE);
        AtomicInteger atomicInteger = cache.get(username);
        if (atomicInteger == null) {
            atomicInteger = new AtomicInteger(1);
            cache.put(username, atomicInteger);
        } else {
            atomicInteger.incrementAndGet();
        }
        return atomicInteger.get() >= 5;
    }

    /**
     * 检查密码错误次数，当次数达到5次时返回true
     *
     * @param username 用户名
     * @return 是否出错达到5次
     */
    public static boolean checkPasswordRetry(String username) {
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Cache<Object, AtomicInteger> cache = securityManager.getCacheManager().getCache(PASSWORD_RETRY_CACHE);
        AtomicInteger atomicInteger = cache.get(username);
        if (atomicInteger == null) {
            return false;
        }
        return atomicInteger.get() >= 5;
    }

    /**
     * 清除密码错误次数
     *
     * @param username 用户名
     */
    public static void clearPasswordRetry(String username) {
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Cache<Object, AtomicInteger> cache = securityManager.getCacheManager().getCache(PASSWORD_RETRY_CACHE);
        AtomicInteger atomicInteger = cache.get(username);
        if (atomicInteger != null) {
            cache.remove(username);
        }
    }
}
