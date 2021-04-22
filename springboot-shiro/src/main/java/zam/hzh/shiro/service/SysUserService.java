package zam.hzh.shiro.service;

import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.model.SysUser;

public interface SysUserService {

    Msg addUser(SysUser addUser);

    Msg findByUsername(String username);

    Msg getUserRolesPerms(String username);
}
