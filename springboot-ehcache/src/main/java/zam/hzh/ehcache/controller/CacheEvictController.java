package zam.hzh.ehcache.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.ehcache.util.EhcacheConst;

/**
 * 注解@CacheEvict示例
 */
@RestController
@RequestMapping("/cacheEvict")
public class CacheEvictController {

    /**
     * 基本使用，执行方法将会移除以参数值作为key值的缓存内容（如果key值存在的话）
     */
    @RequestMapping("/evict")
    @CacheEvict(EhcacheConst.DEMO_CACHE)
    public String cacheEvict(String a) {
        System.out.println("执行cacheEvict方法");
        return a;
    }

    /**
     * 使用allEntries属性并指定为true，执行方法时就会将缓存中的所有内容都移除
     */
    @RequestMapping("/evict1")
    @CacheEvict(value = EhcacheConst.DEMO_CACHE, allEntries = true)
    public String cacheEvict1(String a) {
        System.out.println("执行cacheEvict1方法");
        return a;
    }

    /**
     * 使用beforeInvocation属性默认值，方法执行之后才移除以参数值作为key值的缓存内容
     */
    @RequestMapping("/evict2")
    @CacheEvict(EhcacheConst.DEMO_CACHE)
    public String cacheEvict2(String a) {
        System.out.println("开始执行cacheEvict2方法");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束执行cacheEvict2方法");
        return a;
    }

    /**
     * 使用beforeInvocation属性并指定为true，方法执行前就会移除以参数值作为key值的缓存内容
     */
    @RequestMapping("/evict3")
    @CacheEvict(value = EhcacheConst.DEMO_CACHE, beforeInvocation = true)
    public String cacheEvict3(String a) {
        System.out.println("开始执行cacheEvict3方法");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束执行cacheEvict3方法");
        return a;
    }
}
