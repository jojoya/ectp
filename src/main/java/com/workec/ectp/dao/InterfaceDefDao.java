package com.workec.ectp.dao;


import com.workec.ectp.entity.InterfaceDef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterfaceDefDao extends JpaRepository<InterfaceDef,Integer> {

      @Query(value = "SELECT * FROM interface_def WHERE MODULE_ID=?1 AND VALUE =?2", nativeQuery = true)
      List<InterfaceDef> findByModuleIdAndValue(Integer moduleId, String value);

      @Query(value = "SELECT * FROM interface_def WHERE MODULE_ID=?1", nativeQuery = true)
      List<InterfaceDef> findByModuleId(Integer moduleId);
}
