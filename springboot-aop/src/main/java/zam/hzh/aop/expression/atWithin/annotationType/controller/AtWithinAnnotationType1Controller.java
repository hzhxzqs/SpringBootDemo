package zam.hzh.aop.expression.atWithin.annotationType.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.aop.expression.atWithin.annotationType.annotation.AtWithinAnnotationTypeAnnotation;

@AtWithinAnnotationTypeAnnotation
@RestController
@RequestMapping("/atWithin/annotationType1")
public class AtWithinAnnotationType1Controller {

    @RequestMapping("/test")
    public void test() {
        System.out.println("正在执行test方法，类带注解");
    }
}
