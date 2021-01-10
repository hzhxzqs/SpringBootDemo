package zam.hzh.aop.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 定义切面类
 */
@Aspect
@Component
public class DemoAop {

    /**
     * 定义切入点
     */
    @Pointcut("execution(* zam.hzh.aop.demo.controller..*(..))")
    public void pointcut() {
    }

    /**
     * 定义环绕通知
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) {
        System.out.println("业务代码执行之前");
        Object result;
        try {
            result = pjp.proceed();
            System.out.println("业务代码执行完成，结果：" + result);
        } catch (Throwable throwable) {
            result = "执行异常";
            System.out.println("业务代码执行异常，异常：" + throwable);
        }
        return result;
    }
}
