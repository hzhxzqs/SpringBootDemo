package zam.hzh.aop.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/test")
    public String test() {
        System.out.println("正在执行业务代码");
        return "执行成功";
    }
}
