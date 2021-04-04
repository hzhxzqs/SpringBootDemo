package zam.hzh.quartz.common.quartz.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 自定义作业2
 *
 * <br>使用JobExecutionContext获取JobDataMap来获取传入的数据
 */
public class Demo2Job implements Job {

    /**
     * 作业执行体
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job2正在执行");
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        for (String key : jobDataMap.getKeys()) {
            System.out.print(key + " -> ");
            System.out.println(jobDataMap.get(key));
        }
        System.out.println("Job2执行完成");
    }
}
