package zam.hzh.aop.other.proceedingJoinPoint.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/other/proceedingJoinPoint")
public class ProceedingJoinPointController {

    @RequestMapping("/test1")
    public String test1() {
        System.out.println("正在执行test1方法");
        return "执行完成";
    }

    @RequestMapping("/test2")
    public String test2(String a) {
        System.out.println("正在执行test2方法，a：" + a);
        return "执行完成";
    }

    @RequestMapping("/test3")
    public String test3(String a, String b) {
        System.out.println("正在执行test3方法，a：" + a + " b：" + b);
        return "执行完成";
    }

    @RequestMapping("/test4")
    public String test4(String a, int b) {
        System.out.println("正在执行test4方法，a：" + a + " b：" + b);
        return "执行完成";
    }
}
