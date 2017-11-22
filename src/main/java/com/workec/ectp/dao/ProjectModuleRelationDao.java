package com.workec.ectp.dao;


import com.workec.ectp.entity.ProjectModuleRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectModuleRelationDao extends JpaRepository<ProjectModuleRelation,Integer> {

    @Query(value = "SELECT * FROM ProjectModuleRelation WHERE project_id=?1", nativeQuery = true)
    List<ProjectModuleRelation> findModuleIdByProjectId(Integer project_id);
}
