package zam.hzh.quartz.service.Impl;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zam.hzh.quartz.common.quartz.job.*;
import zam.hzh.quartz.common.quartz.util.QuartzUtil;
import zam.hzh.quartz.common.result.Msg;
import zam.hzh.quartz.service.DemoService;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public Msg startDemo1() {
        QuartzUtil.startScheduler(scheduler, Demo1Job.class, "demo1", "demo1", null, getTime());
        return Msg.success();
    }

    @Override
    public Msg startDemo2() {
        Map<String, Object> map = new HashMap<>();
        map.put("demo2", "demo2");
        map.put("test", "test");
        QuartzUtil.startScheduler(scheduler, Demo2Job.class, "demo2", "demo2", map, getTime());
        return Msg.success();
    }

    @Override
    public Msg startDemo3() {
        QuartzUtil.startScheduler(scheduler, Demo3Job.class, "demo3", "demo3", null, "0/10 * * * * ?");
        return Msg.success();
    }

    @Override
    public Msg startDemo4() {
        Map<String, Object> map = new HashMap<>();
        map.put("count", 1);
        QuartzUtil.startScheduler(scheduler, Demo4Job.class, "demo4", "demo4", map, "0/10 * * * * ?");
        return Msg.success();
    }

    @Override
    public Msg startDemo5() {
        QuartzUtil.startScheduler(scheduler, Demo5Job.class, "demo5", "demo5", null, getTime());
        return Msg.success();
    }

    @Override
    public Msg startDemo6() {
        QuartzUtil.startScheduler(scheduler, Demo6Job.class, "demo6", "demo6", null, getTime());
        return Msg.success();
    }

    @Override
    public Msg stopAll() {
        QuartzUtil.stopSchedulerAll(scheduler);
        return Msg.success();
    }

    private Date getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);
        return calendar.getTime();
    }
}
