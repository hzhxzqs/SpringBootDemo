package zam.hzh.aop.expression.atWithin.annotationType.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - @within表达式：annotation-type部分
 */
@Aspect
@Component
public class AtWithinAnnotationTypeAop {

    @After("@within(zam.hzh.aop.expression.atWithin.annotationType.annotation.AtWithinAnnotationTypeAnnotation)")
    public void advice() {
        System.out.println("advice执行，业务代码类带注解");
    }
}
