package zam.hzh.quartz.common.quartz.configuration;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 继承SpringBeanJobFactory创建Job工厂类
 */
@Configuration
public class QuartzJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        beanFactory = applicationContext.getAutowireCapableBeanFactory();
    }

    /**
     * 创建job实例
     *
     * @param bundle TriggerFiredBundle
     * @return Object
     * @throws Exception Exception
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 调用父类方法创建job实例
        Object jobInstance = super.createJobInstance(bundle);
        // 注入到spring容器中
        beanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
