package com.workec.ectp.dao.jdbc.Impl;

import com.workec.ectp.dao.jdbc.IInterfaceParamDao;
import com.workec.ectp.entity.Bo.InterfaceParamForCallInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 2018/3/5.
 */
@Repository
public class InterfaceParamDaoImpl implements IInterfaceParamDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<InterfaceParamForCallInfo> findByCallInterfaceIdAndInterfaceIdAndLocation(Integer callInterfaceId, Integer interfaceId, Integer location) {
        List<InterfaceParamForCallInfo> list = jdbcTemplate.query
                ("SELECT t1.id as paramId,t1.param_name as paramName,t2.id as valueId,t2.params_value as value,t1.format,t1.remark \n" +
                                "FROM \n" +
                                "(SELECT * FROM interface_param a WHERE a.interface_def_id = ? and a.location = ?)t1 \n" +
                                "LEFT JOIN \n" +
                                "(SELECT * FROM call_interface_data b WHERE b.call_interface_id = ? )t2 \n" +
                                "ON t1.id=t2.param_key_id",
                new Object[]{interfaceId,location,callInterfaceId},
                new BeanPropertyRowMapper(InterfaceParamForCallInfo.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }


}
