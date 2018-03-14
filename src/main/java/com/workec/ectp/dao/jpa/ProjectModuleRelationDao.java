package com.workec.ectp.dao.jpa;


import com.workec.ectp.entity.DoBak.ProjectModuleRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectModuleRelationDao extends JpaRepository<ProjectModuleRelation,Integer> {

    List<ProjectModuleRelation> findByProjectId(Integer project_id);
}
