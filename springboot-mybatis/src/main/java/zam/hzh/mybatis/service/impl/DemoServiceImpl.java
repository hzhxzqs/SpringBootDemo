package zam.hzh.mybatis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zam.hzh.mybatis.common.result.Msg;
import zam.hzh.mybatis.dao.DemoDao;
import zam.hzh.mybatis.model.Demo;
import zam.hzh.mybatis.service.DemoService;

import java.util.Date;
import java.util.List;

@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoDao demoDao;

    @Override
    public Msg addDemo(Demo demo) {
        demo.setCreateDate(new Date());
        System.out.println(demo);
        int result = demoDao.addDemo(demo);
        System.out.println(result);
        System.out.println(demo);
        return Msg.success();
    }

    @Override
    public Msg addDemoList(List<Demo> demos) {
        for (Demo demo : demos) {
            demo.setCreateDate(new Date());
        }
        System.out.println(demos);
        int result = demoDao.addDemoList(demos);
        System.out.println(result);
        System.out.println(demos);
        return Msg.success();
    }

    @Override
    public Msg delDemo(Long id) {
        int result = demoDao.delDemo(id);
        System.out.println(result);
        return Msg.success();
    }

    @Override
    public Demo findDemoById(Long id) {
        return demoDao.findDemoById(id);
    }

    @Override
    public List<Demo> findAllDemo() {
        return demoDao.findAllDemo();
    }

    @Override
    public Msg updateDemo(Demo demo) {
        int result = demoDao.updateDemo(demo);
        System.out.println(result);
        return Msg.success();
    }
}
