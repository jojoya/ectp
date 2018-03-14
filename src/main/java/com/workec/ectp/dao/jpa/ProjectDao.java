package com.workec.ectp.dao.jpa;


import com.workec.ectp.entity.Do.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDao extends JpaRepository<Project,Integer> {

}
