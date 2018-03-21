package com.workec.ectp.dao.jdbc.Impl;

import com.workec.ectp.dao.jdbc.IMiddleParamDao;
import com.workec.ectp.entity.Bo.CallInterfaceAndMiddleValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 2018/3/5.
 */
@Repository
public class MiddleParamDaoImpl implements IMiddleParamDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CallInterfaceAndMiddleValues> getCallInterfaceAndMiddleValuesByCaseId(Integer caseId) {
        List<CallInterfaceAndMiddleValues> list = jdbcTemplate.query
                ("SELECT \n" +
                                "a.case_id AS caseId,\n" +
                                "a.id AS callInterfaceId,\n" +
                                "a.label,\n" +
                                "a.location,\n" +
                                "a.step,\n" +
                                "b.id AS middleValueId,\n" +
                                "b.name,\n" +
                                "b.expression\n" +
                                "FROM call_interface a,middle_param b\n" +
                                "WHERE a.case_id = ? and a.id = b.call_interface_id",
                new Object[]{caseId},
                new BeanPropertyRowMapper(CallInterfaceAndMiddleValues.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

}
