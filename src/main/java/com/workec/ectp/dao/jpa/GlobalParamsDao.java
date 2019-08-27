package com.workec.ectp.dao.jpa;


import com.workec.ectp.entity.Do.GlobalParams;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalParamsDao extends JpaRepository<GlobalParams,Integer> {

    GlobalParams findByParamName(String paramName);
}
