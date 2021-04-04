package zam.hzh.quartz.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import zam.hzh.quartz.model.Demo;

public interface DemoDAO extends JpaRepository<Demo, String> {
}
