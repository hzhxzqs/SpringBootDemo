package zam.hzh.aop.expression.atArgs.annotationType.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - @args表达式：annotation-type部分
 */
@Aspect
@Component
public class AtArgsAnnotationTypeAop {

    @After("@args(zam.hzh.aop.expression.atArgs.annotationType.annotation.AtArgsAnnotationTypeAnnotation) && within(zam.hzh.aop.expression.atArgs.annotationType.controller.AtArgsAnnotationTypeController)")
    public void advice1() {
        System.out.println("advice1执行，业务代码参数带注解");
    }
}
