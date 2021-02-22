package zam.hzh.mybatis.dao;

import org.apache.ibatis.annotations.Mapper;
import zam.hzh.mybatis.model.Demo;

import java.util.List;

/**
 * 添加@Mapper注解标识MyBatis映射
 */
@Mapper
public interface DemoDao {

    int addDemo(Demo demo);

    int addDemoList(List<Demo> demos);

    int delDemo(Long id);

    Demo findDemoById(Long id);

    List<Demo> findAllDemo();

    int updateDemo(Demo demo);
}
