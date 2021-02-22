package zam.hzh.mybatis.service;

import zam.hzh.mybatis.common.result.Msg;
import zam.hzh.mybatis.model.Demo;

import java.util.List;

public interface DemoService {

    Msg addDemo(Demo demo);

    Msg addDemoList(List<Demo> demos);

    Msg delDemo(Long id);

    Demo findDemoById(Long id);

    List<Demo> findAllDemo();

    Msg updateDemo(Demo demo);
}
