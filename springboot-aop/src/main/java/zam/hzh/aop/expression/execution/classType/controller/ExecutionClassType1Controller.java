package zam.hzh.aop.expression.execution.classType.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execution/classType1")
public class ExecutionClassType1Controller {

    @RequestMapping("/test")
    public void test() {
        System.out.println("正在执行test方法，指定类");
    }
}
