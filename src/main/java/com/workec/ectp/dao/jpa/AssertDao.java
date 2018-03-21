package com.workec.ectp.dao.jpa;


import com.workec.ectp.entity.Do.CaseAssert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssertDao extends JpaRepository<CaseAssert, Integer> {

    @Query(value = "SELECT * FROM case_assert WHERE call_interface_id=?1", nativeQuery = true)
    List<CaseAssert> findAll(Integer callInterfaceId);
}
