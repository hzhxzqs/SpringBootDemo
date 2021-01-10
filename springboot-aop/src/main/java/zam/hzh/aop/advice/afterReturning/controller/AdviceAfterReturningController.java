package zam.hzh.aop.advice.afterReturning.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/advice/afterReturning")
public class AdviceAfterReturningController {

    @RequestMapping("/test1")
    public void test1() {
        System.out.println("正在执行test1方法，无返回值");
    }

    @RequestMapping("/test2")
    public int test2() {
        System.out.println("正在执行test2方法，int返回值");
        return 0;
    }

    @RequestMapping("/test3")
    public Integer test3() {
        System.out.println("正在执行test3方法，Integer返回值");
        return 0;
    }

    @RequestMapping("/test4")
    public String test4() {
        System.out.println("正在执行test4方法，String返回值");
        return "执行完成";
    }

    @RequestMapping("/test5")
    public Object test5() {
        System.out.println("正在执行test5方法，Object返回值，可转String类型");
        return "执行完成";
    }

    @RequestMapping("/test6")
    public Object test6() {
        System.out.println("正在执行test6方法，Object返回值，不可转String类型");
        return new Date();
    }

    @RequestMapping("/test7")
    public Object test7() throws Exception {
        System.out.println("正在执行test7方法，抛出异常");
        throw new Exception();
    }
}
