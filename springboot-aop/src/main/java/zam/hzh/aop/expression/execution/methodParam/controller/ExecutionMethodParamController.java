package zam.hzh.aop.expression.execution.methodParam.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execution/methodParam")
public class ExecutionMethodParamController {

    @RequestMapping("/test1")
    public void test1() {
        System.out.println("正在执行test1方法，无参数");
    }

    @RequestMapping("/test2")
    public void test2(int a) {
        System.out.println("正在执行test2方法，int参数");
    }

    @RequestMapping("/test3")
    public void test3(Integer a) {
        System.out.println("正在执行test3方法，Integer参数");
    }

    @RequestMapping("/test4")
    public void test4(String a) {
        System.out.println("正在执行test4方法，String参数");
    }

    @RequestMapping("/test5")
    public void test5(Object a) {
        System.out.println("正在执行test5方法，Object参数");
    }

    @RequestMapping("/test6")
    public void test6(int a, String b) {
        System.out.println("正在执行test6方法，int参数、String参数");
    }

    @RequestMapping("/test7")
    public void test7(String a, int b) {
        System.out.println("正在执行test7方法，String参数、int参数");
    }

    @RequestMapping("/test8")
    public void test8(int a, int b, int c) {
        System.out.println("正在执行test8方法，int参数、int参数、int参数");
    }

    @RequestMapping("/test9")
    public void test9(int a, String b, int c) {
        System.out.println("正在执行test9方法，int参数、String参数、int参数");
    }
}
