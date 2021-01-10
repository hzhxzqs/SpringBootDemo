package zam.hzh.aop.expression.args.methodParam.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/args/methodParam")
public class ArgsMethodParamController {

    @RequestMapping("/test1")
    public void test1() {
        System.out.println("正在执行test1方法，无参数");
    }

    @RequestMapping("/test2")
    public void test2(int a) {
        System.out.println("正在执行test2方法，int参数");
    }

    @RequestMapping("/test3")
    public void test3(String a) {
        System.out.println("正在执行test3方法，String参数");
    }

    @RequestMapping("/test4")
    public void test4(int a, String b) {
        System.out.println("正在执行test4方法，int参数、String参数");
    }

    @RequestMapping("/test5")
    public void test5(String a, int b) {
        System.out.println("正在执行test5方法，String参数、int参数");
    }
}
