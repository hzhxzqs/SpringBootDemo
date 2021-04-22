package zam.hzh.shiro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zam.hzh.shiro.model.SysRolePermission;

import java.util.Collection;
import java.util.List;

public interface SysRolePermissionDao extends JpaRepository<SysRolePermission, String> {

    List<SysRolePermission> findByRoleId(String roleId);

    List<SysRolePermission> findByRoleIdIn(Collection<String> roleId);
}
