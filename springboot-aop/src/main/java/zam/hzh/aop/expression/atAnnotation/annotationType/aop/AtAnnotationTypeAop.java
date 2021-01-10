package zam.hzh.aop.expression.atAnnotation.annotationType.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - @annotation表达式：annotation-type部分
 */
@Aspect
@Component
public class AtAnnotationTypeAop {

    @After("@annotation(zam.hzh.aop.expression.atAnnotation.annotationType.annotation.AtAnnotationTypeAnnotation)")
    public void advice() {
        System.out.println("advice执行，业务代码方法带注解");
    }
}
