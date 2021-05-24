package zam.hzh.ehcache.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.ehcache.util.EhcacheConst;

/**
 * 注解@Cacheable示例
 */
@RestController
@RequestMapping("/cacheable")
public class CacheableController {

    /**
     * 基本使用，方法执行后，再次使用相同的参数值请求时方法不执行
     */
    @RequestMapping("/able")
    @Cacheable(EhcacheConst.DEMO_CACHE)
    public String cacheable(String a) {
        System.out.println("执行cacheable方法");
        return a;
    }

    /**
     * 使用sync属性默认值，表示不同步执行该方法，使用相同的参数值连续请求，方法会连续执行
     */
    @RequestMapping("/able1")
    @Cacheable(EhcacheConst.DEMO_CACHE)
    public String cacheable1(String a) {
        System.out.println("开始执行cacheable1方法");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束执行cacheable1方法");
        return a;
    }

    /**
     * 使用sync属性并指定为true，表示同步执行该方法，使用相同的参数值连续请求，之后的方法需要等前一个方法执行完成后才返回
     */
    @RequestMapping("/able2")
    @Cacheable(value = EhcacheConst.DEMO_CACHE, sync = true)
    public String cacheable2(String a) {
        System.out.println("开始执行cacheable2方法");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束执行cacheable2方法");
        return a;
    }
}
