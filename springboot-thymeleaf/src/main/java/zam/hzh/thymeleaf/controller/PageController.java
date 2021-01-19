package zam.hzh.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import zam.hzh.thymeleaf.bean.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/demo")
    public String demo(Model model) {
        // 设置普通对象
        model.addAttribute("msg", "哈哈哈");
        // 设置自定义对象
        model.addAttribute("user", new User("Li Liu", 20, 1));

        // 日期对象
        model.addAttribute("date", new Date());

        // List对象
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(new User("user" + i, 20 + i));
        }
        model.addAttribute("users", users);
        return "demo";
    }
}
