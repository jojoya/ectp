package com.workec.ectp.dao.jpa;


import com.workec.ectp.entity.Do.CaseAssert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaseAssertDao extends JpaRepository<CaseAssert, Integer> {

    List<CaseAssert> findByCallInterfaceId(Integer callInterfaceId);
}
