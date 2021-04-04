package zam.hzh.quartz.common.quartz.job;

import org.quartz.*;

/**
 * 自定义作业4
 *
 * <br>@PersistJobDataAfterExecution注解表示作业在执行过程中可以更新JobDataMap，并且在执行完成后重新保存JobDataMap
 * <br>该注解可以将本次执行过程的数据或执行结果传入下一次作业中，可以用于作业间的数据传递
 * <br>通常与@DisallowConcurrentExecution注解一起使用，可以避免并发性问题
 */
@PersistJobDataAfterExecution
public class Demo4Job implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job4正在执行");
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        int count = (int) jobDataMap.get("count");
        System.out.println("第" + count + "次执行");
        count++;
        jobDataMap.put("count", count);
        System.out.println("Job4执行完成");
    }
}
