package zam.hzh.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 使用@EnableCaching注解开启缓存支持
 */
@EnableCaching
@SpringBootApplication
public class SpringbootEhcacheApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootEhcacheApplication.class, args);
    }
}
