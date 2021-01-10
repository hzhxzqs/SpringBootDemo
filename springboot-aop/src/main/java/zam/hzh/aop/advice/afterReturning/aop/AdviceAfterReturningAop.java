package zam.hzh.aop.advice.afterReturning.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - @AfterReturning使用
 */
@Aspect
@Component
public class AdviceAfterReturningAop {

    @AfterReturning("execution(* zam.hzh.aop.advice.afterReturning.controller.AdviceAfterReturningController.*())")
    public void advice1() {
        System.out.println("advice1执行，业务代码返回任意返回值");
    }

    @AfterReturning(value = "execution(* zam.hzh.aop.advice.afterReturning.controller.AdviceAfterReturningController.*())", returning = "result")
    public void advice2(int result) {
        System.out.println("advice2执行，业务代码返回int类型返回值，结果：" + result);
    }

    @AfterReturning(value = "execution(* zam.hzh.aop.advice.afterReturning.controller.AdviceAfterReturningController.*())", returning = "result")
    public void advice3(Integer result) {
        System.out.println("advice3执行，业务代码返回Integer类型返回值，结果：" + result);
    }

    @AfterReturning(value = "execution(* zam.hzh.aop.advice.afterReturning.controller.AdviceAfterReturningController.*())", returning = "result")
    public void advice4(String result) {
        System.out.println("advice4执行，业务代码返回String类型返回值，结果：" + result);
    }

    @AfterReturning(value = "execution(* zam.hzh.aop.advice.afterReturning.controller.AdviceAfterReturningController.*())", returning = "result")
    public void advice5(Object result) {
        System.out.println("advice5执行，业务代码返回Object类型返回值，结果：" + result);
    }

    /**
     * 业务代码抛出异常，此处不执行
     */
    @AfterReturning("execution(* zam.hzh.aop.advice.afterReturning.controller.AdviceAfterReturningController.test7())")
    public void advice6() {
        System.out.println("advice6执行");
    }
}
