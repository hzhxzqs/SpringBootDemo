package zam.hzh.ehcache.controller;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.ehcache.util.EhcacheUtil;

import java.util.Map;

/**
 * ehcache基本使用
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private EhcacheUtil ehcacheUtil;

    /**
     * 打印指定缓存内容
     *
     * @param cacheName 缓存名
     */
    @RequestMapping("/showAllValues")
    public void showCacheAllValues(String cacheName) {
        Map<Object, Element> allValues = ehcacheUtil.getCacheAllValues(cacheName);
        System.out.println("===============================================");
        allValues.forEach((k, v) -> {
            System.out.print(k + " -> ");
            System.out.println(v);
        });
        System.out.println("===============================================");
    }

    /**
     * 打印指定缓存指定key值内容
     *
     * @param cacheName 缓存名
     * @param key       key值
     */
    @RequestMapping("/showValue")
    public void showCacheValue(String cacheName, String key) {
        System.out.println(key + " -> " + ehcacheUtil.getCacheValue(cacheName, key));
    }

    /**
     * 打印所有缓存内容
     */
    @RequestMapping("/showAll")
    public void showAllCaches() {
        Map<String, Cache> allCaches = ehcacheUtil.getAllCaches();
        allCaches.forEach((k, v) -> {
            System.out.println("缓存名：" + k);
            showCacheAllValues(k);
            System.out.println();
        });
    }

    /**
     * 向指定缓存添加内容
     *
     * @param cacheName 缓存名
     * @param key       key值
     * @param value     缓存内容
     */
    @RequestMapping("/putValue")
    public void putCacheValue(String cacheName, String key, String value) {
        ehcacheUtil.putCacheValue(cacheName, key, value);
        showCacheAllValues(cacheName);
    }

    /**
     * 移除指定缓存中指定key值的缓存内容
     *
     * @param cacheName 缓存名
     * @param key       key值
     */
    @RequestMapping("/removeValue")
    public void removeCacheValue(String cacheName, String key) {
        ehcacheUtil.removeCacheValue(cacheName, key);
        showCacheAllValues(cacheName);
    }
}
