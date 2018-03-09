package com.workec.ectp.dao.jpa;

import com.workec.ectp.entity.Do.InterfaceParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by jojoya on 2018/1/17.
 */
public interface InterfaceParamDao extends JpaRepository<InterfaceParam,Integer> {

    @Query(value = "SELECT * FROM interface_param WHERE interface_def_id=?1 AND location=?2", nativeQuery = true)
    List<InterfaceParam> findByInterfaceDefIdAndLocation(Integer interfaceDefId,Integer location);

    List<InterfaceParam> findByInterfaceIdAndLocation(Integer interfaceId,Integer location);
}
