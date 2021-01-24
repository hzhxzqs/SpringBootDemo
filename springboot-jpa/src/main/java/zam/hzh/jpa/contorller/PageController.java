package zam.hzh.jpa.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/demo")
    public String demo() {
        return "demo";
    }
}
