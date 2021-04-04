package zam.hzh.quartz.common.quartz.util;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class QuartzUtil {

    private static final Logger logger = LoggerFactory.getLogger(QuartzUtil.class);

    /**
     * 获取JobKey
     *
     * @param name  job名称
     * @param group job组名
     * @return JobKey
     */
    public static JobKey getJobKey(String name, String group) {
        return new JobKey(name, group);
    }

    /**
     * 获取JobDataMap
     *
     * @param dataMap 附加数据
     * @return JobDataMap
     */
    public static JobDataMap getJobDataMap(Map<String, Object> dataMap) {
        JobDataMap jobDataMap = new JobDataMap();
        if (dataMap != null) {
            dataMap.forEach(jobDataMap::put);
        }
        return jobDataMap;
    }

    /**
     * 获取JobDetail
     *
     * @param jobClass   job执行类
     * @param jobKey     JobKey
     * @param jobDataMap JobDataMap
     * @return JobDetail
     */
    public static JobDetail getJobDetail(Class<? extends Job> jobClass, JobKey jobKey, JobDataMap jobDataMap) {
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobKey)
                .setJobData(jobDataMap)
                .build();
    }

    /**
     * 获取Trigger（使用cron表达式可多次执行作业）
     *
     * @param jobKey JobKey
     * @param cron   cron表达式
     * @return Trigger
     */
    public static Trigger getTrigger(JobKey jobKey, String cron) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobKey.getName(), jobKey.getGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .build();
    }

    /**
     * 获取Trigger（使用时间只能在该时间执行一次）
     *
     * @param jobKey JobKey
     * @param date   执行时间
     * @return Trigger
     */
    public static Trigger getTrigger(JobKey jobKey, Date date) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobKey.getName(), jobKey.getGroup())
                .startAt(date)
                .build();
    }

    /**
     * 启动定时作业
     *
     * @param scheduler Scheduler
     * @param jobDetail JobDetail
     * @param trigger   Trigger
     */
    public static void startScheduler(Scheduler scheduler, JobDetail jobDetail, Trigger trigger) {
        JobKey jobKey = jobDetail.getKey();
        try {
            if (scheduler.checkExists(jobKey)) {
                logger.warn("定时作业：{name:{}, group:{}}已存在，取消执行", jobKey.getName(), jobKey.getGroup());
                return;
            }
            scheduler.scheduleJob(jobDetail, trigger);
            logger.info("定时作业：{name:{}, group:{}}启动成功", jobKey.getName(), jobKey.getGroup());
        } catch (SchedulerException e) {
            logger.error("定时作业：name:{name:{}, group:{}}启动失败，原因：{}", jobKey.getName(), jobKey.getGroup(), e.toString());
        }
    }

    /**
     * 启动定时作业（可多次执行作业）
     *
     * @param scheduler Scheduler
     * @param jobClass  job执行类
     * @param jobName   job名称
     * @param jobGroup  job组名
     * @param dataMap   附加数据
     * @param jobCron   cron表达式
     */
    public static void startScheduler(Scheduler scheduler, Class<? extends Job> jobClass, String jobName,
                                      String jobGroup, Map<String, Object> dataMap, String jobCron) {
        JobKey jobKey = getJobKey(jobName, jobGroup);
        JobDataMap jobDataMap = getJobDataMap(dataMap);
        JobDetail jobDetail = getJobDetail(jobClass, jobKey, jobDataMap);
        Trigger trigger = getTrigger(jobKey, jobCron);
        startScheduler(scheduler, jobDetail, trigger);
    }

    /**
     * 启动定时作业（只执行一次）
     *
     * @param scheduler Scheduler
     * @param jobClass  job执行类
     * @param jobName   job名称
     * @param jobGroup  job组名
     * @param dataMap   附加数据
     * @param date      执行时间
     */
    public static void startScheduler(Scheduler scheduler, Class<? extends Job> jobClass, String jobName,
                                      String jobGroup, Map<String, Object> dataMap, Date date) {
        JobKey jobKey = getJobKey(jobName, jobGroup);
        JobDataMap jobDataMap = getJobDataMap(dataMap);
        JobDetail jobDetail = getJobDetail(jobClass, jobKey, jobDataMap);
        Trigger trigger = getTrigger(jobKey, date);
        startScheduler(scheduler, jobDetail, trigger);
    }

    /**
     * 停止定时作业
     *
     * @param scheduler Scheduler
     * @param jobKey    JobKey
     */
    public static void stopScheduler(Scheduler scheduler, JobKey jobKey) {
        try {
            if (!scheduler.checkExists(jobKey)) {
                logger.warn("定时作业：{name:{}, group:{}}不存在，取消执行", jobKey.getName(), jobKey.getGroup());
                return;
            }
            // 检查job执行类是否实现InterruptableJob接口，是则调用中断
            if (InterruptableJob.class.isAssignableFrom(scheduler.getJobDetail(jobKey).getJobClass())) {
                scheduler.interrupt(jobKey);
            }
            scheduler.unscheduleJob(TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup()));
            scheduler.deleteJob(jobKey);
            logger.info("定时作业：{name:{}, group:{}}停止成功", jobKey.getName(), jobKey.getGroup());
        } catch (SchedulerException e) {
            logger.error("定时作业：name:{name:{}, group:{}}停止失败，原因：{}", jobKey.getName(), jobKey.getGroup(), e.toString());
        }
    }

    /**
     * 停止定时作业
     *
     * @param scheduler Scheduler
     * @param jobName   job名称
     * @param jobGroup  job组名
     */
    public static void stopScheduler(Scheduler scheduler, String jobName, String jobGroup) {
        JobKey jobKey = getJobKey(jobName, jobGroup);
        stopScheduler(scheduler, jobKey);
    }

    /**
     * 停止所有定时作业
     *
     * @param scheduler Scheduler
     */
    public static void stopSchedulerAll(Scheduler scheduler) {
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyGroup());
            for (JobKey jobKey : jobKeys) {
                stopScheduler(scheduler, jobKey);
            }
            logger.info("定时作业停止完成");
        } catch (SchedulerException e) {
            logger.info("定时作业停止出错，原因：{}", e.toString());
        }
    }

    /**
     * 停止指定job组名的定时作业
     *
     * @param scheduler Scheduler
     * @param jobGroup  job组名
     */
    public static void stopSchedulerGroup(Scheduler scheduler, String jobGroup) {
        try {
            Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(jobGroup));
            for (JobKey jobKey : jobKeys) {
                stopScheduler(scheduler, jobKey);
            }
            logger.info("定时作业停止完成");
        } catch (SchedulerException e) {
            logger.info("定时作业停止出错，原因：{}", e.toString());
        }
    }
}
