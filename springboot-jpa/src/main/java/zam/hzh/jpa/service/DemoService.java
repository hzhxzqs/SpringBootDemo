package zam.hzh.jpa.service;

import zam.hzh.jpa.common.result.Msg;
import zam.hzh.jpa.model.Demo;

public interface DemoService {

    Msg addDemo(Demo addDemo);

    Msg deleteDemo(String deleteId);

    Msg findAllDemo();

    Msg updateDemo(Demo updateDemo);
}
