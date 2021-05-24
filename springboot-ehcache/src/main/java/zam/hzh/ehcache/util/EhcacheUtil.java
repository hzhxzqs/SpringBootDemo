package zam.hzh.ehcache.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * ehcache工具类
 */
@Component
public class EhcacheUtil {

    private final CacheManager ehcacheManager;

    public EhcacheUtil(org.springframework.cache.CacheManager cacheManager) {
        this.ehcacheManager = ((EhCacheCacheManager) cacheManager).getCacheManager();
    }

    /**
     * 获取指定缓存
     *
     * @param cacheName 缓存名
     */
    private Cache getCertainCache(String cacheName) {
        return ehcacheManager.getCache(cacheName);
    }

    /**
     * 获取所有缓存
     *
     * @return 所有缓存
     */
    public Map<String, Cache> getAllCaches() {
        Map<String, Cache> cacheMap = new HashMap<>();
        for (String cacheName : ehcacheManager.getCacheNames()) {
            cacheMap.put(cacheName, getCertainCache(cacheName));
        }
        return cacheMap;
    }

    /**
     * 获取指定缓存中指定key值的缓存内容
     *
     * @param cacheName 缓存名
     * @param key       key值
     * @return 缓存内容
     */
    public Element getCacheValue(String cacheName, Object key) {
        Cache cache = getCertainCache(cacheName);
        if (cache == null) return null;
        return cache.get(key);
    }

    /**
     * 获取指定缓存中所有key值的缓存内容
     *
     * @param cacheName 缓存名
     * @return 缓存内容
     */
    public Map<Object, Element> getCacheAllValues(String cacheName) {
        Cache cache = getCertainCache(cacheName);
        if (cache == null) return new HashMap<>();
        return cache.getAll(cache.getKeys());
    }

    /**
     * 向指定缓存添加内容
     *
     * @param cacheName 缓存名
     * @param key       key值
     * @param value     缓存内容
     */
    public void putCacheValue(String cacheName, Object key, Object value) {
        Cache cache = getCertainCache(cacheName);
        if (cache == null) return;
        cache.put(new Element(key, value));
    }

    /**
     * 移除指定缓存中指定key值的缓存内容
     *
     * @param cacheName 缓存名
     * @param key       key值
     */
    public void removeCacheValue(String cacheName, Object key) {
        Cache cache = getCertainCache(cacheName);
        if (cache == null) return;
        cache.remove(key);
    }
}
