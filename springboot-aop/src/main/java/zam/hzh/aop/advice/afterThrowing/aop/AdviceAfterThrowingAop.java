package zam.hzh.aop.advice.afterThrowing.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * - @AfterThrowing使用
 */
@Aspect
@Component
public class AdviceAfterThrowingAop {

    @AfterThrowing("execution(* zam.hzh.aop.advice.afterThrowing.controller.AdviceAfterThrowingController.*())")
    public void advice1() {
        System.out.println("advice1执行，业务代码抛出任意异常");
    }

    @AfterThrowing(value = "execution(* zam.hzh.aop.advice.afterThrowing.controller.AdviceAfterThrowingController.*())", throwing = "e")
    public void advice2(IOException e) {
        System.out.println("advice2执行，业务代码抛出IOException类型异常，异常：" + e);
    }

    @AfterThrowing(value = "execution(* zam.hzh.aop.advice.afterThrowing.controller.AdviceAfterThrowingController.*())", throwing = "e")
    public void advice3(RuntimeException e) {
        System.out.println("advice3执行，业务代码抛出RuntimeException类型异常，异常：" + e);
    }

    @AfterThrowing(value = "execution(* zam.hzh.aop.advice.afterThrowing.controller.AdviceAfterThrowingController.*())", throwing = "e")
    public void advice4(Exception e) {
        System.out.println("advice4执行，业务代码抛出Exception类型异常，异常：" + e);
    }

    @AfterThrowing(value = "execution(* zam.hzh.aop.advice.afterThrowing.controller.AdviceAfterThrowingController.*())", throwing = "e")
    public void advice5(Throwable e) {
        System.out.println("advice5执行，业务代码抛出Throwable类型异常，异常：" + e);
    }

    /**
     * 业务代码正常执行，此处不执行
     */
    @AfterThrowing("execution(* zam.hzh.aop.advice.afterThrowing.controller.AdviceAfterThrowingController.test5())")
    public void advice6() {
        System.out.println("advice6执行");
    }
}
