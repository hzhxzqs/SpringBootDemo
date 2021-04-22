package zam.hzh.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.model.SysRole;
import zam.hzh.shiro.model.SysUser;
import zam.hzh.shiro.service.SysRoleService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/add")
    public Msg addRole(SysRole sysRole) {
        return sysRoleService.addRole(sysRole);
    }

    @RequestMapping("/findAll")
    public Msg findAllRoles() {
        return sysRoleService.findAllRoles();
    }

    @RequestMapping("/give")
    public Msg giveUserRole(@RequestParam(value = "roleIds[]", required = false) List<String> roleIds) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return Msg.error();
        }
        if (roleIds == null) {
            roleIds = new ArrayList<>();
        }
        return sysRoleService.giveUserRole(user.getId(), roleIds);
    }
}
