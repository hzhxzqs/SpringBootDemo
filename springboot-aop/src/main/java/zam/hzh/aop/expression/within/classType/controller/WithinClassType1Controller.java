package zam.hzh.aop.expression.within.classType.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/within/classType1")
public class WithinClassType1Controller {

    @RequestMapping("/test1")
    public void test1() {
        System.out.println("正在执行test1方法，指定类，public方法");
    }

    @RequestMapping("/test2")
    protected void test2() {
        System.out.println("正在执行test2方法，protected方法");
    }

    @RequestMapping("/test3")
    private void test3() {
        System.out.println("正在执行test3方法，private方法");
    }

    @RequestMapping("/test4")
    void test4() {
        System.out.println("正在执行test4方法，package包可见方法");
    }
}
