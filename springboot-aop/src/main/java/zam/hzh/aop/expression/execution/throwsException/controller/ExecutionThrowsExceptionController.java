package zam.hzh.aop.expression.execution.throwsException.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/execution/throwsException")
public class ExecutionThrowsExceptionController {

    @RequestMapping("/test1")
    public void test1() throws IOException {
        System.out.println("正在执行test1方法，声明抛出IOException异常");
    }

    @RequestMapping("/test2")
    public void test2() throws Exception {
        System.out.println("正在执行test2方法，声明抛出Exception异常");
    }

    @RequestMapping("/test3")
    public void test3() throws RuntimeException {
        System.out.println("正在执行test3方法，声明抛出RuntimeException异常");
    }

    @RequestMapping("/test4")
    public void test4() {
        System.out.println("正在执行test4方法，未声明抛出异常");
    }
}
