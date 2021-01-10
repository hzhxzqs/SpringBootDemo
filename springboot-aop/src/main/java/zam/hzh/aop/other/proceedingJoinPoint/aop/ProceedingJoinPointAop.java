package zam.hzh.aop.other.proceedingJoinPoint.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * ProceedingJoinPoint参数用法
 */
@Aspect
@Component
public class ProceedingJoinPointAop {

    @Around("execution(* zam.hzh.aop.other.proceedingJoinPoint.controller.ProceedingJoinPointController.*())")
    public Object advice1(ProceedingJoinPoint pjp) {
        System.out.println("advice1执行，业务代码执行之前");
        Object result;
        try {
            result = pjp.proceed();
            System.out.println("advice1执行，业务代码执行完成，结果：" + result);
        } catch (Throwable throwable) {
            result = "执行异常";
            System.out.println("advice1执行，业务代码执行异常，异常：" + throwable);
        }
        return result;
    }

    @Around("execution(* zam.hzh.aop.other.proceedingJoinPoint.controller.ProceedingJoinPointController.*(String, ..))")
    public Object advice2(ProceedingJoinPoint pjp) {
        System.out.println("advice2执行，业务代码执行之前");
        Object result;
        try {
            result = pjp.proceed(new Object[]{"a", "b"});
            System.out.println("advice2执行，业务代码执行完成，结果：" + result);
        } catch (Throwable throwable) {
            result = "执行异常";
            System.out.println("advice2执行，业务代码执行异常，异常：" + throwable);
        }
        return result;
    }

}
