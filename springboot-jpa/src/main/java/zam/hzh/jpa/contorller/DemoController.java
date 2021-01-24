package zam.hzh.jpa.contorller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.jpa.common.result.Msg;
import zam.hzh.jpa.model.Demo;
import zam.hzh.jpa.service.DemoService;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @RequestMapping("/add")
    public Msg addDemo(Demo demo) {
        return demoService.addDemo(demo);
    }

    @RequestMapping("/delete")
    public Msg deleteDemo(String id) {
        return demoService.deleteDemo(id);
    }

    @RequestMapping("/findAll")
    public Msg findAllDemo() {
        return demoService.findAllDemo();
    }

    @RequestMapping("/update")
    public Msg updateDemo(Demo demo) {
        return demoService.updateDemo(demo);
    }
}
