package zam.hzh.shiro.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zam.hzh.shiro.model.SysRole;

import java.util.Collection;
import java.util.List;

public interface SysRoleDao extends JpaRepository<SysRole, String> {

    List<SysRole> findByIdIn(Collection<String> id);

    SysRole findByName(String name);
}
