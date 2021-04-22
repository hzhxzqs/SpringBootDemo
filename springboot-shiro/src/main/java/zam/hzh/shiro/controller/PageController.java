package zam.hzh.shiro.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/index.html")
    public String index() {
        return "index";
    }

    @RequestMapping("/login.html")
    public String login() {
        return "login";
    }

    @RequestMapping("/welcome.html")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/pages.html")
    public String pages() {
        return "pages";
    }

    @RequestMapping("/unauthc.html")
    public String unauthc() {
        return "/authc/unauthc";
    }

    @RequestMapping("/authc.html")
    public String authc() {
        return "/authc/authc";
    }

    @RequestMapping("/unauthorized.html")
    public String unauthorized() {
        return "/authc/unauthorized";
    }

    @RequestMapping("/admin1.html")
    public String admin1() {
        return "/role/admin1";
    }

    @RequiresRoles("admin")
    @RequestMapping("/admin2.html")
    public String admin2() {
        return "/role/admin2";
    }

    @RequestMapping("/look1.html")
    public String look1() {
        return "/permission/look1";
    }

    @RequiresPermissions("look")
    @RequestMapping("/look2.html")
    public String look2() {
        return "/permission/look2";
    }
}
