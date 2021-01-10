package zam.hzh.aop.expression.execution.methodName.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - execution表达式：method-name部分
 */
@Aspect
@Component
public class ExecutionMethodNameAop {

    @After("execution(* zam.hzh.aop.expression.execution.methodName.controller.ExecutionMethodNameController.test1())")
    public void advice1() {
        System.out.println("advice1执行，业务代码指定方法名");
    }

    @After("execution(* zam.hzh.aop.expression.execution.methodName.controller.ExecutionMethodNameController.*())")
    public void advice2() {
        System.out.println("advice2执行，业务代码任意方法名");
    }
}
