package zam.hzh.aop.expression.atAnnotation.annotationType.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.aop.expression.atAnnotation.annotationType.annotation.AtAnnotationTypeAnnotation;

@RestController
@RequestMapping("/atAnnotation/annotationType")
public class AtAnnotationTypeController {

    @AtAnnotationTypeAnnotation
    @RequestMapping("/test1")
    public void test1() {
        System.out.println("正在执行test1方法，方法带注解");
    }

    @RequestMapping("/test2")
    public void test2() {
        System.out.println("正在执行test2方法，方法不带注解");
    }
}
