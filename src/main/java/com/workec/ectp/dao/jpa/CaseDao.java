package com.workec.ectp.dao.jpa;

import com.workec.ectp.entity.Do.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaseDao extends JpaRepository<Case,Integer> {

    @Query(value = "SELECT * FROM t_case WHERE interface_id=?1", nativeQuery = true)
    List<Case> getListByInterfaceId(int interfaceId);


}
