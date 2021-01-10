package zam.hzh.aop.expression.atWithin.annotationType.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atWithin/annotationType2")
public class AtWithinAnnotationType2Controller {

    @RequestMapping("/test")
    public void test() {
        System.out.println("正在执行test方法，类不带注解");
    }
}
