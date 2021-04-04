package zam.hzh.quartz.common.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * 自定义作业3
 *
 * <br>@DisallowConcurrentExecution注解表示对于同一个`JobKey`，该Job作业不能并行执行
 * <br>例如：Job每10秒执行新作业，但作业执行需要11秒，该Job的其它作业需要等待当前作业执行完才能执行，否则其它作业直接执行
 * <br>注意：org.quartz.threadPool.threadCount线程池中线程的数量至少要多个，如果只设置一个那么该注解不能发挥作用，因为本身就只有一个线程
 */
@DisallowConcurrentExecution
public class Demo3Job implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job3正在执行， " + new Date());
        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Job3执行完成， " + new Date());
    }
}
