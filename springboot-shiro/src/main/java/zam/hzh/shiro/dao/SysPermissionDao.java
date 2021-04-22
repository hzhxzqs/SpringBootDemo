package zam.hzh.shiro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zam.hzh.shiro.model.SysPermission;

import java.util.Collection;
import java.util.List;

public interface SysPermissionDao extends JpaRepository<SysPermission, String> {

    List<SysPermission> findByIdIn(Collection<String> id);

    SysPermission findByName(String name);
}
