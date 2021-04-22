package zam.hzh.shiro.service;

import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.model.SysRole;

import java.util.List;

public interface SysRoleService {

    Msg addRole(SysRole addRole);

    Msg findAllRoles();

    Msg giveUserRole(String userId, List<String> roleIds);
}
