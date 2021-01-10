package zam.hzh.aop.advice.around.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * - @Around使用
 */
@Aspect
@Component
public class AdviceAroundAop {

    @Around("execution(* zam.hzh.aop.advice.around.controller.AdviceAroundController.*())")
    public Object advice(ProceedingJoinPoint pjp) {
        System.out.println("advice执行，业务代码执行之前");
        Object result;
        try {
            // 执行业务代码
            result = pjp.proceed();
            // 取消以下注释可修改正常执行时返回结果
            // result = "纳尼，被改了。。。";
            System.out.println("advice执行，业务代码执行完成，结果：" + result);
        } catch (Throwable throwable) {
            // 抛出异常，修改业务代码返回结果
            result = "执行异常";
            System.out.println("advice执行，业务代码执行异常，异常：" + throwable);
        }
        return result;
    }
}
