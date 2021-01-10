package zam.hzh.aop.expression.ethis.declaringType.service;

import org.springframework.stereotype.Service;

@Service
public class ThisDeclaringTypeService {

    public void test() {
        System.out.println("正在执行test方法，未实现接口的类的方法");
    }
}
