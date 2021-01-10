package zam.hzh.aop.expression.execution.returnType.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - execution表达式：return-type部分
 */
@Aspect
@Component
public class ExecutionReturnTypeAop {

    @After("execution(int zam.hzh.aop.expression.execution.returnType.controller.ExecutionReturnTypeController.*())")
    public void advice1() {
        System.out.println("advice1执行，业务代码返回int返回值");
    }

    @After("execution(Integer zam.hzh.aop.expression.execution.returnType.controller.ExecutionReturnTypeController.*())")
    public void advice2() {
        System.out.println("advice2执行，业务代码返回Integer返回值");
    }

    @After("execution(String zam.hzh.aop.expression.execution.returnType.controller.ExecutionReturnTypeController.*())")
    public void advice3() {
        System.out.println("advice3执行，业务代码返回String返回值");
    }

    @After("execution(Object zam.hzh.aop.expression.execution.returnType.controller.ExecutionReturnTypeController.*())")
    public void advice4() {
        System.out.println("advice4执行，业务代码返回Object返回值");
    }

    @After("execution(* zam.hzh.aop.expression.execution.returnType.controller.ExecutionReturnTypeController.*())")
    public void advice5() {
        System.out.println("advice5执行，业务代码返回任意返回值");
    }
}
