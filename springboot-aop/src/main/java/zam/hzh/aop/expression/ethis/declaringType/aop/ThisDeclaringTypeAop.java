package zam.hzh.aop.expression.ethis.declaringType.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - this表达式：declaring-type部分
 */
@Aspect
@Component
public class ThisDeclaringTypeAop {

    @After("this(zam.hzh.aop.expression.ethis.declaringType.service.ThisDeclaringTypeService)")
    public void advice1() {
        System.out.println("advice1执行，业务代码来自未实现接口的类的方法");
    }

    @After("this(zam.hzh.aop.expression.ethis.declaringType.service.ThisDeclaringTypeServiceImpl)")
    public void advice2() {
        System.out.println("advice2执行，业务代码来自实现接口的类的方法");
    }

    @After("this(zam.hzh.aop.expression.ethis.declaringType.service.ThisDeclaringTypeInterface)")
    public void advice3() {
        System.out.println("advice3执行，业务代码来自接口方法");
    }
}
