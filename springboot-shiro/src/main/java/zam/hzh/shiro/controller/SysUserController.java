package zam.hzh.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.common.result.MsgEnum;
import zam.hzh.shiro.model.SysUser;
import zam.hzh.shiro.service.SysUserService;

@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/add")
    public Msg addUser(SysUser sysUser) {
        return sysUserService.addUser(sysUser);
    }

    @RequestMapping("/getRolesPerms")
    public Msg getUserRolesPerms() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if (user == null) {
            return Msg.error(MsgEnum.USER_NOT_EXIST);
        }
        String username = user.getUsername();
        return sysUserService.getUserRolesPerms(username);
    }
}
