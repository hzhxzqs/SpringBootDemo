package zam.hzh.aop.expression.execution.classType.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execution/classType2")
public class ExecutionClassType2Controller {

    @RequestMapping("/test")
    public void test() {
        System.out.println("正在执行test方法，指定包下的类");
    }
}
