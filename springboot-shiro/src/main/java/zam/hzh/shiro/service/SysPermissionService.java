package zam.hzh.shiro.service;

import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.model.SysPermission;

import java.util.List;

public interface SysPermissionService {

    Msg addPermission(SysPermission addPermission);

    Msg findAllPermissions();

    Msg giveRolePermission(String userId, List<String> permissionIds);
}
