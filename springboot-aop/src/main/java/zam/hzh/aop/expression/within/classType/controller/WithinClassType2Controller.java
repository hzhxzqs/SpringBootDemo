package zam.hzh.aop.expression.within.classType.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/within/classType2")
public class WithinClassType2Controller {

    @RequestMapping("/test")
    public void test() {
        System.out.println("正在执行test方法，指定包下的类");
    }
}
