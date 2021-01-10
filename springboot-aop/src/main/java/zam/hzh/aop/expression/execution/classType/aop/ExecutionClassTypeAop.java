package zam.hzh.aop.expression.execution.classType.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - execution表达式：class-type部分
 */
@Aspect
@Component
public class ExecutionClassTypeAop {

    @After("execution(* zam.hzh.aop.expression.execution.classType.controller.ExecutionClassType1Controller.*(..))")
    public void advice1() {
        System.out.println("advice1执行，业务代码来自指定类");
    }

    @After("execution(* zam.hzh.aop.expression.execution.classType.controller.*.*(..))")
    public void advice2() {
        System.out.println("advice2执行，业务代码来自指定包下的类");
    }

    @After("execution(* zam.hzh.aop.expression.execution.classType.controller..*(..))")
    public void advice3() {
        System.out.println("advice3执行，业务代码来自指定包及其子包下的类");
    }
}
