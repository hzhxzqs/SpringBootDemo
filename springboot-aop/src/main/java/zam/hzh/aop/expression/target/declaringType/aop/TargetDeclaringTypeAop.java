package zam.hzh.aop.expression.target.declaringType.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - target表达式：declaring-type部分
 */
@Aspect
@Component
public class TargetDeclaringTypeAop {

    @After("target(zam.hzh.aop.expression.target.declaringType.service.TargetDeclaringTypeService)")
    public void advice1() {
        System.out.println("advice1执行，业务代码来自未实现接口的类的方法");
    }

    @After("target(zam.hzh.aop.expression.target.declaringType.service.TargetDeclaringTypeServiceImpl)")
    public void advice2() {
        System.out.println("advice2执行，业务代码来自实现接口的类的方法");
    }

    @After("target(zam.hzh.aop.expression.target.declaringType.service.TargetDeclaringTypeInterface)")
    public void advice3() {
        System.out.println("advice3执行，业务代码来自接口方法");
    }
}
