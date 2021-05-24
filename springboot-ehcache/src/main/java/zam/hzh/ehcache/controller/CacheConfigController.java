package zam.hzh.ehcache.controller;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.ehcache.util.EhcacheConst;

/**
 * 注解@CacheConfig示例
 * <p>
 * 这里指定默认使用的缓存是demoCache，当缓存注解没有指定操作的缓存时，默认使用该缓存
 */
@RestController
@RequestMapping("/cacheConfig")
@CacheConfig(cacheNames = EhcacheConst.DEMO_CACHE)
public class CacheConfigController {

    /**
     * 未指定操作的缓存，使用默认缓存
     */
    @RequestMapping("/config")
    @CachePut
    public String cacheConfig(String a) {
        System.out.println("执行cacheConfig方法");
        return a;
    }

    /**
     * 指定了操作的缓存，使用指定的缓存而不是默认缓存
     */
    @RequestMapping("/config1")
    @CachePut(EhcacheConst.DEMO1_CACHE)
    public String cacheConfig1(String a) {
        System.out.println("执行cacheConfig1方法");
        return a;
    }
}
