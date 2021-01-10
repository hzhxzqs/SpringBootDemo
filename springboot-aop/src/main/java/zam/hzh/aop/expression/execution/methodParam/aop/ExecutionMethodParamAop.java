package zam.hzh.aop.expression.execution.methodParam.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - execution表达式：method-param部分
 */
@Aspect
@Component
public class ExecutionMethodParamAop {

    @After("execution(* zam.hzh.aop.expression.execution.methodParam.controller.ExecutionMethodParamController.*())")
    public void advice1() {
        System.out.println("advice1执行，业务代码无参数的方法");
    }

    @After("execution(* zam.hzh.aop.expression.execution.methodParam.controller.ExecutionMethodParamController.*(int))")
    public void advice2() {
        System.out.println("advice2执行，业务代码只有一个int参数的方法");
    }

    @After("execution(* zam.hzh.aop.expression.execution.methodParam.controller.ExecutionMethodParamController.*(Integer))")
    public void advice3() {
        System.out.println("advice3执行，业务代码只有一个Integer参数的方法");
    }

    @After("execution(* zam.hzh.aop.expression.execution.methodParam.controller.ExecutionMethodParamController.*(String))")
    public void advice4() {
        System.out.println("advice4执行，业务代码只有一个String参数的方法");
    }

    @After("execution(* zam.hzh.aop.expression.execution.methodParam.controller.ExecutionMethodParamController.*(Object))")
    public void advice5() {
        System.out.println("advice5执行，业务代码只有一个Object参数的方法");
    }

    @After("execution(* zam.hzh.aop.expression.execution.methodParam.controller.ExecutionMethodParamController.*(int, String))")
    public void advice6() {
        System.out.println("advice6执行，业务代码有int参数、String参数的方法");
    }

    @After("execution(* zam.hzh.aop.expression.execution.methodParam.controller.ExecutionMethodParamController.*(..))")
    public void advice7() {
        System.out.println("advice7执行，业务代码不定参数的方法");
    }

    @After("execution(* zam.hzh.aop.expression.execution.methodParam.controller.ExecutionMethodParamController.*(.., String, ..))")
    public void advice8() {
        System.out.println("advice8执行，业务代码包含String参数的方法");
    }
}
