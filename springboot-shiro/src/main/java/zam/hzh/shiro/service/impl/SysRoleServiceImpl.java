package zam.hzh.shiro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zam.hzh.shiro.common.result.Msg;
import zam.hzh.shiro.common.result.MsgEnum;
import zam.hzh.shiro.dao.SysRoleDao;
import zam.hzh.shiro.dao.SysUserDao;
import zam.hzh.shiro.dao.SysUserRoleDao;
import zam.hzh.shiro.model.SysRole;
import zam.hzh.shiro.model.SysUser;
import zam.hzh.shiro.model.SysUserRole;
import zam.hzh.shiro.service.SysRoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Override
    public Msg addRole(SysRole addRole) {
        SysRole sysRole = sysRoleDao.findByName(addRole.getName());
        if (sysRole != null) {
            return Msg.error(MsgEnum.ROLE_EXIST);
        }
        sysRoleDao.save(addRole);
        return Msg.success();
    }

    @Override
    public Msg findAllRoles() {
        return Msg.success(sysRoleDao.findAll());
    }

    @Override
    public Msg giveUserRole(String userId, List<String> roleIds) {
        Optional<SysUser> optional = sysUserDao.findById(userId);
        if (!optional.isPresent()) {
            return Msg.error(MsgEnum.USER_NOT_EXIST);
        }
        List<SysUserRole> sysUserRoles = sysUserRoleDao.findByUserId(userId);
        sysUserRoleDao.deleteAll(sysUserRoles);
        List<SysRole> sysRoles = sysRoleDao.findByIdIn(roleIds);
        List<SysUserRole> sysUserRoleList = new ArrayList<>();
        for (SysRole sysRole : sysRoles) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(sysRole.getId());
            sysUserRoleList.add(sysUserRole);
        }
        sysUserRoleDao.saveAll(sysUserRoleList);
        return Msg.success();
    }
}
