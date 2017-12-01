package com.workec.ectp.dao;


import com.workec.ectp.entity.InterfaceMain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterfaceMainDao extends JpaRepository<InterfaceMain,Integer> {

      @Query(value = "SELECT * FROM interface_main WHERE MODULE_ID=?1 AND VALUE =?2", nativeQuery = true)
      List<InterfaceMain> findByModuleIdAndValue(Integer moduleId,String value);

}
