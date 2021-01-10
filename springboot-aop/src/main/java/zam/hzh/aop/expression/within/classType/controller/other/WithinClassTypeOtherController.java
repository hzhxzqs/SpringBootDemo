package zam.hzh.aop.expression.within.classType.controller.other;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/within/classType/other")
public class WithinClassTypeOtherController {

    @RequestMapping("/test")
    public void test() {
        System.out.println("正在执行test方法，子包下的类");
    }
}
