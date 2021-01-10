package zam.hzh.aop.advice.around.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advice/around")
public class AdviceAroundController {

    @RequestMapping("/test1")
    public String test1() {
        System.out.println("正在执行test1方法");
        return "执行完成";
    }

    @RequestMapping("/test2")
    public String test2() throws Exception {
        System.out.println("正在执行test2方法，抛出异常");
        throw new Exception();
    }
}
