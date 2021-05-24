package zam.hzh.ehcache.controller;

import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.ehcache.util.EhcacheConst;

/**
 * 注解@CachePut示例
 */
@RestController
@RequestMapping("/cachePut")
public class CachePutController {

    /**
     * 基本使用，再次使用相同的参数值请求时仍会执行方法
     */
    @RequestMapping("/put")
    @CachePut(EhcacheConst.DEMO_CACHE)
    public String cachePut(String a) {
        System.out.println("执行cachePut方法");
        return a;
    }

    /**
     * 使用cacheNames属性指定缓存
     */
    @RequestMapping("/put1")
    @CachePut(cacheNames = EhcacheConst.DEMO1_CACHE)
    public String cachePut1(String a) {
        System.out.println("执行cachePut1方法");
        return a;
    }

    /**
     * 使用key属性指定key值生成规则，这里使用方法名与参数值拼接作为key值
     */
    @RequestMapping("/put2")
    @CachePut(value = EhcacheConst.DEMO_CACHE, key = "#root.methodName + '-' + #a")
    public String cachePut2(String a) {
        System.out.println("执行cachePut2方法");
        return a;
    }

    /**
     * 使用condition属性指定进行put操作的条件，这里当方法返回结果是"a"时，才进行put操作
     */
    @RequestMapping("/put3")
    @CachePut(value = EhcacheConst.DEMO_CACHE, key = "#root.methodName + '-' + #a", condition = "'a'.equals(#result)")
    public String cachePut3(String a) {
        System.out.println("执行cachePut3方法");
        return a;
    }

    /**
     * 使用unless属性指定阻止put操作的条件，这里当方法返回结果是"a"时，就不进行put操作
     */
    @RequestMapping("/put4")
    @CachePut(value = EhcacheConst.DEMO_CACHE, key = "#root.methodName + '-' + #a", unless = "'a'.equals(#result)")
    public String cachePut4(String a) {
        System.out.println("执行cachePut4方法");
        return a;
    }
}
