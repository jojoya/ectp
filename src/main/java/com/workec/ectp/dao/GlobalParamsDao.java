package com.workec.ectp.dao;


import com.workec.ectp.entity.DO.GlobalParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface GlobalParamsDao extends JpaRepository<GlobalParams,Integer> {

}
