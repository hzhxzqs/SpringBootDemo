package zam.hzh.aop.expression.execution.modifier.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - execution表达式：modifier部分
 */
@Aspect
@Component
public class ExecutionModifierAop {

    @After("execution(public * zam.hzh.aop.expression.execution.modifier.controller.ExecutionModifierController.*())")
    public void advice1() {
        System.out.println("advice1执行，业务代码public方法");
    }

    @After("execution(protected * zam.hzh.aop.expression.execution.modifier.controller.ExecutionModifierController.*())")
    public void advice2() {
        System.out.println("advice2执行，业务代码protected方法");
    }

    /**
     * 对private方法无效，此处不执行
     */
    @After("execution(private * zam.hzh.aop.expression.execution.modifier.controller.ExecutionModifierController.*())")
    public void advice3() {
        System.out.println("advice3执行，业务代码private方法");
    }

    /**
     * 不填写modifier，可匹配public、protected、package包可见方法，对private方法无效
     */
    @After("execution(* zam.hzh.aop.expression.execution.modifier.controller.ExecutionModifierController.*())")
    public void advice4() {
        System.out.println("advice4执行，业务代码除private之外的方法");
    }
}
