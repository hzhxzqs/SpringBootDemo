package zam.hzh.aop.expression.execution.throwsException.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * execution表达式：throws-exception部分
 */
@Aspect
@Component
public class ExecutionThrowsExceptionAop {

    @After("execution(* zam.hzh.aop.expression.execution.throwsException.controller.ExecutionThrowsExceptionController.*() throws java.io.IOException)")
    public void advice1() {
        System.out.println("advice1执行，业务代码声明抛出IOException异常");
    }

    @After("execution(* zam.hzh.aop.expression.execution.throwsException.controller.ExecutionThrowsExceptionController.*() throws Exception)")
    public void advice2() {
        System.out.println("advice2执行，业务代码声明抛出Exception异常");
    }

    @After("execution(* zam.hzh.aop.expression.execution.throwsException.controller.ExecutionThrowsExceptionController.*() throws *)")
    public void advice3() {
        System.out.println("advice3执行，业务代码声明抛出任意异常");
    }

    /**
     * 不填写throws-exception，可匹配任意方法
     */
    @After("execution(* zam.hzh.aop.expression.execution.throwsException.controller.ExecutionThrowsExceptionController.*())")
    public void advice4() {
        System.out.println("advice4执行，业务代码任意方法");
    }
}
