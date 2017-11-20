package com.workec.ectp.dao;


import com.workec.ectp.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ModuleDao extends JpaRepository<Module,Integer> {

    @Query(value = "SELECT count(1) FROM MODULE WHERE parent_id = ?1", nativeQuery = true)
    Integer findChildsCountByParentId(Integer id);

    @Query(value = "SELECT * FROM MODULE WHERE parent_id = ?1", nativeQuery = true)
    List<Module> findChildsByParentId(Integer id);

    @Query(value = "SELECT * FROM MODULE WHERE name LIKE %?1%", nativeQuery = true)
    List<Module> findByName(String name);
}
