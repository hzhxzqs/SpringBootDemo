package zam.hzh.quartz.common.quartz.job;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

/**
 * 自定义作业5
 *
 * <br>实现InterruptableJob接口及其方法，进行作业中断
 */
public class Demo5Job implements InterruptableJob {

    // 中断标记
    private boolean isInterrupted = false;

    /**
     * 当调用Scheduler.interrupt(JobKey)方法时，执行该方法
     *
     * @throws UnableToInterruptJobException 中断异常
     */
    @Override
    public void interrupt() throws UnableToInterruptJobException {
        isInterrupted = true;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job5正在执行");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 根据中断标记判断是否中断
        if (isInterrupted) {
            System.out.println("Job5作业中断");
        } else {
            System.out.println("Job5执行完成");
        }
    }
}
