package zam.hzh.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import zam.hzh.jpa.model.Demo;

import java.util.List;

/**
 * DAO接口
 * <p>
 * 继承JpaRepository<实体类，实体类id类型>接口，进行数据库表操作
 */
public interface DemoDAO extends JpaRepository<Demo, String> {

    List<Demo> findByDemoDesc(String demoDesc);

    @Query("select d from Demo d where d.demoDesc = ?1")
    List<Demo> findByDemoDescQuery(String demoDesc);

    @Query("select d from Demo d where d.demoDesc = :demoDesc")
    List<Demo> findByDemoDescNamed(@Param("demoDesc") String demoDesc);

    @Query(value = "select * from demo where demo_desc = ?1", nativeQuery = true)
    List<Demo> findByDemoDescNative(String demoDesc);

    @Modifying
    @Query("update Demo d set d.demoDesc = ?1 where d.id = ?2")
    int updateDemoDescById(String demoDesc, String id);

    @Modifying
    @Query("delete from Demo d where d.id = ?1")
    void deleteDemoById(String id);
}
