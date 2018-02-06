package com.workec.ectp.dao;


import com.workec.ectp.entity.DO.ApplicationEnvironment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ApplicationEnvironmentDao extends JpaRepository<ApplicationEnvironment,Integer> {

    @Modifying
    @Query(value = "UPDATE application_environment SET ip=?2 WHERE id=?1", nativeQuery = true)
    Integer updateIp(int id, String ip);
}
