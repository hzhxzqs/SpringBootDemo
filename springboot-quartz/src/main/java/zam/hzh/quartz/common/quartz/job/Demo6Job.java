package zam.hzh.quartz.common.quartz.job;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import zam.hzh.quartz.dao.DemoDAO;
import zam.hzh.quartz.model.Demo;

/**
 * 自定义作业6
 *
 * <br>结合作业中断和手动事务，实现事务管理
 */
@Component
public class Demo6Job implements InterruptableJob {

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private TransactionDefinition transactionDefinition;

    @Autowired
    private DemoDAO demoDAO;

    private boolean isInterrupted = false;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        isInterrupted = true;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Job6正在执行");
        // 开启事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        demoDAO.save(new Demo());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!isInterrupted) {
            // 提交事务
            platformTransactionManager.commit(transaction);
            System.out.println("Job6正常完成");
        } else {
            // 回滚事务
            platformTransactionManager.rollback(transaction);
            System.out.println("Job6异常回滚");
        }
    }
}
