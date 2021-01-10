package zam.hzh.aop.advice.afterThrowing.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/advice/afterThrowing")
public class AdviceAfterThrowingController {

    @RequestMapping("/test1")
    public void test1() throws IOException {
        System.out.println("正在执行test1方法，IOException异常");
        throw new IOException();
    }

    @RequestMapping("/test2")
    public void test2() {
        System.out.println("正在执行test2方法，RuntimeException异常");
        throw new RuntimeException();
    }

    @RequestMapping("/test3")
    public void test3() throws Exception {
        System.out.println("正在执行test3方法，Exception异常");
        throw new Exception();
    }

    @RequestMapping("/test4")
    public void test4() throws Throwable {
        System.out.println("正在执行test4方法，Throwable异常");
        throw new Throwable();
    }

    @RequestMapping("/test5")
    public void test5() {
        System.out.println("正在执行test5方法，未抛出异常");
    }
}
