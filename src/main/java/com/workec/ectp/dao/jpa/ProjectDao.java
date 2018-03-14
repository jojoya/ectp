package com.workec.ectp.dao.jpa;


import com.workec.ectp.entity.DoBak.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDao extends JpaRepository<Project,Integer> {

}
