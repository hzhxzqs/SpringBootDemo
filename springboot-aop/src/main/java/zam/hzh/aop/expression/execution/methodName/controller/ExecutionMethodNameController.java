package zam.hzh.aop.expression.execution.methodName.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execution/methodName")
public class ExecutionMethodNameController {

    @RequestMapping("/test1")
    public void test1() {
        System.out.println("正在执行test1方法，指定方法名");
    }

    @RequestMapping("/test2")
    public void test2() {
        System.out.println("正在执行test2方法，其它方法名");
    }
}
