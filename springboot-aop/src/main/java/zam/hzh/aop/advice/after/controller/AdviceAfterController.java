package zam.hzh.aop.advice.after.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advice/after")
public class AdviceAfterController {

    @RequestMapping("/test1")
    public void test1() {
        System.out.println("正在执行test1方法");
    }

    @RequestMapping("/test2")
    public void test2() throws Exception {
        System.out.println("正在执行test2方法，抛出异常");
        throw new Exception();
    }
}
