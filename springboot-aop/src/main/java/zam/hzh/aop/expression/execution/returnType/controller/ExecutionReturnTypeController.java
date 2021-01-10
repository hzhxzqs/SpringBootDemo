package zam.hzh.aop.expression.execution.returnType.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execution/returnType")
public class ExecutionReturnTypeController {

    @RequestMapping("/test1")
    public int test1() {
        System.out.println("正在执行test1方法，int返回值");
        return 0;
    }

    @RequestMapping("/test2")
    public Integer test2() {
        System.out.println("正在执行test2方法，Integer返回值");
        return 0;
    }

    @RequestMapping("/test3")
    public String test3() {
        System.out.println("正在执行test3方法，String返回值");
        return "0";
    }

    @RequestMapping("/test4")
    public Object test4() {
        System.out.println("正在执行test4方法，Object返回值");
        return "0";
    }
}
