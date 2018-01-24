package com.workec.ectp.dao;


import com.workec.ectp.entity.DO.InterfaceDef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterfaceDefDao extends JpaRepository<InterfaceDef,Integer> {

      @Query(value = "SELECT * FROM interface_def WHERE MODULE_ID=?1", nativeQuery = true)
      List<InterfaceDef> findByModuleId(Integer moduleId);

      @Modifying
      @Query(value = "UPDATE interface_def SET label = ?2 WHERE ID = ?1", nativeQuery = true)
      Integer updateName(Integer id,String name);
}
