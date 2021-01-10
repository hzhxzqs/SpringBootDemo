package zam.hzh.aop.advice.before.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * - @Before使用
 */
@Aspect
@Component
public class AdviceBeforeAop {

    @Before("execution(* zam.hzh.aop.advice.before.controller.AdviceBeforeController.test1())")
    public void advice1() {
        System.out.println("advice1执行");
    }

    /**
     * aop抛出异常时，业务代码不执行
     */
    @Before("execution(* zam.hzh.aop.advice.before.controller.AdviceBeforeController.test2())")
    public void advice2() throws Exception {
        System.out.println("advice2执行，aop抛出异常");
        throw new Exception();
    }
}
