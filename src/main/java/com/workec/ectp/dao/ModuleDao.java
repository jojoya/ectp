package com.workec.ectp.dao;


import com.workec.ectp.entity.DO.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModuleDao extends JpaRepository<Module,Integer> {

    @Modifying
    @Query(value = "UPDATE MODULE SET label = ?2 WHERE id = ?1", nativeQuery = true)
    Integer updateName(Integer id,String name);

    List<Module> findByParentId(Integer id);

    List<Module> findByLabelLike(String name);

    @Query(value = "select b.* from project_module_relation a,module b where a.module_id = b.id AND a.project_id=?1", nativeQuery = true)
    List<Module> findByProjectId(Integer projectId);

}
