package zam.hzh.aop.advice.before.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advice/before")
public class AdviceBeforeController {

    @RequestMapping("/test1")
    public void test1() {
        System.out.println("正在执行test1方法");
    }

    @RequestMapping("/test2")
    public void test2() {
        System.out.println("正在执行test2方法");
    }
}
