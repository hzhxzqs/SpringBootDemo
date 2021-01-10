package zam.hzh.aop.expression.execution.classType.controller.other;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/execution/classType/other")
public class ExecutionClassTypeOtherController {

    @RequestMapping("/test")
    public void test() {
        System.out.println("正在执行test方法，子包下的类");
    }
}
