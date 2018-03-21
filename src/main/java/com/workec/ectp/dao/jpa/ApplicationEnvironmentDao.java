package com.workec.ectp.dao.jpa;


import com.workec.ectp.entity.Do.ApplicationEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationEnvironmentDao extends JpaRepository<ApplicationEnvironment,Integer> {

}
