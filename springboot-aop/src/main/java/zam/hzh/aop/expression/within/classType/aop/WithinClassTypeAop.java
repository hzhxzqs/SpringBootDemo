package zam.hzh.aop.expression.within.classType.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - within表达式：class-type部分
 */
@Aspect
@Component
public class WithinClassTypeAop {

    @After("within(zam.hzh.aop.expression.within.classType.controller.WithinClassType1Controller)")
    public void advice1() {
        System.out.println("advice1执行，业务代码来自指定类");
    }

    @After("within(zam.hzh.aop.expression.within.classType.controller.*)")
    public void advice2() {
        System.out.println("advice2执行，业务代码来自指定包下的类");
    }

    @After("within(zam.hzh.aop.expression.within.classType.controller..*)")
    public void advice3() {
        System.out.println("advice3执行，业务代码来自指定包及其子包下的类");
    }
}
