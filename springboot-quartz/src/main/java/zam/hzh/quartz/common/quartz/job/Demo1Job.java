package zam.hzh.quartz.common.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 自定义作业1
 *
 * <br>实现Job接口并重写execute()方法
 */
public class Demo1Job implements Job {

    /**
     * 作业执行体
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job1作业执行");
    }
}
