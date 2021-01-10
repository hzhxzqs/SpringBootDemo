package zam.hzh.aop.expression.ethis.declaringType.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.aop.expression.ethis.declaringType.service.ThisDeclaringTypeInterface;
import zam.hzh.aop.expression.ethis.declaringType.service.ThisDeclaringTypeService;
import zam.hzh.aop.expression.ethis.declaringType.service.ThisDeclaringTypeServiceImpl;

@RestController
@RequestMapping("/this/declaringType")
public class ThisDeclaringTypeController {

    @Autowired
    private ThisDeclaringTypeService thisDeclaringTypeService;

    @Autowired
    private ThisDeclaringTypeServiceImpl thisDeclaringTypeServiceImpl;

    @Autowired
    private ThisDeclaringTypeInterface thisDeclaringTypeInterface;

    @RequestMapping("/test1")
    public void test1() {
        thisDeclaringTypeService.test();
    }

    @RequestMapping("/test2")
    public void test2() {
        thisDeclaringTypeServiceImpl.test();
    }

    @RequestMapping("/test3")
    public void test3() {
        thisDeclaringTypeServiceImpl.iTest();
    }

    @RequestMapping("/test4")
    public void test4() {
        thisDeclaringTypeInterface.iTest();
    }
}
