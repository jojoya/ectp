package com.workec.ectp.dao.jpa;

import com.workec.ectp.entity.Do.MiddleParam;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.PostUpdate;
import java.util.List;


public interface MiddleParamDao extends JpaRepository<MiddleParam, Integer> {

    List<MiddleParam> findByCallInterfaceId(Integer callInterfaceId);
    List<MiddleParam> findByCaseId(Integer callInterfaceId);
}
