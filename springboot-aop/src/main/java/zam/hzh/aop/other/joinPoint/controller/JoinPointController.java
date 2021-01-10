package zam.hzh.aop.other.joinPoint.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/other/joinPoint")
public class JoinPointController {

    @RequestMapping("/test1")
    public void test1() {
        System.out.println("正在执行test1方法");
    }

    @RequestMapping("/test2")
    public void test2(String a, String b) {
        System.out.println("正在执行test2方法");
    }
}
