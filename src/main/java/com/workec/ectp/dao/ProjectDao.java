package com.workec.ectp.dao;


import com.workec.ectp.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectDao extends JpaRepository<Project,Integer> {

}
