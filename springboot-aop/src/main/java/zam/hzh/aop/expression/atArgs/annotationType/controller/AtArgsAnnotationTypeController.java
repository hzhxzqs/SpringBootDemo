package zam.hzh.aop.expression.atArgs.annotationType.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.aop.expression.atArgs.annotationType.bean.AtArgsAnnotationTypeBean;

@RestController
@RequestMapping("/atArgs/annotationType")
public class AtArgsAnnotationTypeController {

    @RequestMapping("/test1")
    public void test1(AtArgsAnnotationTypeBean a) {
        System.out.println("正在执行test1方法，带注解参数");
    }

    @RequestMapping("/test2")
    public void test2(String a) {
        System.out.println("正在执行test2方法，未带注解参数");
    }
}
