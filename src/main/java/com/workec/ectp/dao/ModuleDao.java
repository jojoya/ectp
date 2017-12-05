package com.workec.ectp.dao;


import com.workec.ectp.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModuleDao extends JpaRepository<Module,Integer> {

    @Query(value = "SELECT count(1) FROM MODULE WHERE parent_id = ?1", nativeQuery = true)
    Integer findChildrenCountByParentId(Integer id);

    @Query(value = "SELECT * FROM MODULE WHERE parent_id = ?1", nativeQuery = true)
    List<Module> findChildrenByParentId(Integer id);

    @Query(value = "SELECT * FROM MODULE WHERE label LIKE %?1%", nativeQuery = true)
    List<Module> findByName(String name);

    @Query(value = "select b.id,b.label from project_module_relation a,module b where a.module_id = b.id AND a.project_id=?1", nativeQuery = true)
    List findByProjectId(Integer projectId);
}
