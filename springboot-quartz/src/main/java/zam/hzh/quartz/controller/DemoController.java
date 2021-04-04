package zam.hzh.quartz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.quartz.common.result.Msg;
import zam.hzh.quartz.service.DemoService;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/start1")
    public Msg startDemo1() {
        return demoService.startDemo1();
    }

    @RequestMapping("/start2")
    public Msg startDemo2() {
        return demoService.startDemo2();
    }

    @RequestMapping("/start3")
    public Msg startDemo3() {
        return demoService.startDemo3();
    }

    @RequestMapping("/start4")
    public Msg startDemo4() {
        return demoService.startDemo4();
    }

    @RequestMapping("/start5")
    public Msg startDemo5() {
        return demoService.startDemo5();
    }

    @RequestMapping("/start6")
    public Msg startDemo6() {
        return demoService.startDemo6();
    }

    @RequestMapping("/stopAll")
    public Msg stopAll() {
        return demoService.stopAll();
    }
}
