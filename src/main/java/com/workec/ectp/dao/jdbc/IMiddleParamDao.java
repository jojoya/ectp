package com.workec.ectp.dao.jdbc;

import com.workec.ectp.entity.Bo.CallInterfaceAndMiddleValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by user on 2018/3/9.
 */
public interface IMiddleParamDao {

    List<CallInterfaceAndMiddleValues> getCallInterfaceAndMiddleValuesByCaseId(Integer caseId);

}
