package zam.hzh.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.model.SysPermission;
import zam.hzh.shiro.model.SysUser;
import zam.hzh.shiro.service.SysPermissionService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @RequestMapping("/add")
    public Msg addPermission(SysPermission sysPermission) {
        return sysPermissionService.addPermission(sysPermission);
    }

    @RequestMapping("/findAll")
    public Msg findAllPermissions() {
        return sysPermissionService.findAllPermissions();
    }

    @RequestMapping("/give")
    public Msg giveRolePermission(@RequestParam(value = "permissionIds[]", required = false) List<String> permissionIds) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return Msg.error();
        }
        if (permissionIds == null) {
            permissionIds = new ArrayList<>();
        }
        return sysPermissionService.giveRolePermission(user.getId(), permissionIds);
    }
}
