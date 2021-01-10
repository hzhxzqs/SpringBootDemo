package zam.hzh.aop.advice.after.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - @After使用
 */
@Aspect
@Component
public class AdviceAfterAop {

    @After("execution(* zam.hzh.aop.advice.after.controller.AdviceAfterController.test1())")
    public void advice1() {
        System.out.println("advice1执行");
    }

    /**
     * 业务代码抛出异常，此处仍执行
     */
    @After("execution(* zam.hzh.aop.advice.after.controller.AdviceAfterController.test2())")
    public void advice2() {
        System.out.println("advice2执行，业务代码抛出异常");
    }
}
