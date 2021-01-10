package zam.hzh.aop.expression.ethis.declaringType.service;

import org.springframework.stereotype.Service;

@Service
public class ThisDeclaringTypeServiceImpl implements ThisDeclaringTypeInterface {

    public void test() {
        System.out.println("正在执行test方法，实现接口的类的方法");
    }

    @Override
    public void iTest() {
        System.out.println("正在执行iTest方法，接口方法");
    }
}
