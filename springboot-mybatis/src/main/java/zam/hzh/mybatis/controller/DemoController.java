package zam.hzh.mybatis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.mybatis.common.result.Msg;
import zam.hzh.mybatis.common.result.MsgEnum;
import zam.hzh.mybatis.model.Demo;
import zam.hzh.mybatis.service.DemoService;

import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/add")
    public Msg addDemo(Demo demo) {
        return demoService.addDemo(demo);
    }

    @RequestMapping("/addList")
    public Msg addDemoList(@RequestBody List<Demo> demos) {
        if (demos == null || demos.size() == 0) return Msg.error();
        return demoService.addDemoList(demos);
    }

    @RequestMapping("/delete")
    public Msg delDemo(Long id) {
        return demoService.delDemo(id);
    }

    @RequestMapping("/findById")
    public Msg findDemoById(Long id) {
        Demo demo = demoService.findDemoById(id);
        if (null == demo) {
            return Msg.error(MsgEnum.DATA_NOT_EXIST);
        }
        return Msg.success(demo);
    }

    @RequestMapping("/findAll")
    public Msg findAllDemo() {
        return Msg.success(demoService.findAllDemo());
    }

    @RequestMapping("/update")
    public Msg updateDemo(Demo demo) {
        return demoService.updateDemo(demo);
    }
}
