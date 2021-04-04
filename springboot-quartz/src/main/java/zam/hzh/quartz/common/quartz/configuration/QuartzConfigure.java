package zam.hzh.quartz.common.quartz.configuration;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * quartz配置类
 */
@Configuration
public class QuartzConfigure {

    @Autowired
    private QuartzJobFactory quartzJobFactory;

    /**
     * SchedulerFactoryBean提供对org.quartz.Scheduler的创建与配置，并且会管理它的生命周期与Spring同步。
     *
     * @return SchedulerFactoryBean
     * @throws IOException Exception
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // 设置自动启动
        factory.setAutoStartup(true);
        // 设置Job工厂类
        factory.setJobFactory(quartzJobFactory);
        // 设置属性
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    /**
     * 从quartz.properties读取属性配置
     *
     * @return Properties
     * @throws IOException Exception
     */
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        // 设置配置文件位置
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * 调度器，所有的调度都是由它控制
     *
     * @return Scheduler
     * @throws IOException Exception
     */
    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }
}
