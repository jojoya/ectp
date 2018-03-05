package com.workec.ectp.dao.JdbcTemplates.Impl;

import com.workec.ectp.dao.JdbcTemplates.IGlobalParamsDataDao;
import com.workec.ectp.entity.Bo.GlobalParamsDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by user on 2018/3/5.
 */
@Repository
public class IGlobalParamsDataDaoImpl implements IGlobalParamsDataDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GlobalParamsDataInfo> findByUserAndEnv(Integer userId,Integer EnvId) {
        List<GlobalParamsDataInfo> list = jdbcTemplate.query
                ("SELECT " +
                                "g.id as paramId, " +
                                "g.param_name as paramName, " +
                                "g.remark as remark, " +
                                "t.id as paramDataId, " +
                                "t.db_env_id as dbEnvId, " +
                                "t.name as dbEnvName, " +
                                "t.param_value as paramValue, " +
                                "t.user_id as userId  " +
                                "FROM " +
                                "global_params g  LEFT JOIN (select p.id, p.db_env_id, p.global_param_id,p.param_value,p.user_id,d.name" +
                                "       from global_params_data p, data_environment d  " +
                                "       WHERE " +
                                "           d.id=p.db_env_id  and p.user_id = ?  and p.db_env_id = ? )t  " +
                                "ON g.id = t.global_param_id ;",
                new Object[]{userId,EnvId},
                new BeanPropertyRowMapper(GlobalParamsDataInfo.class));
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

}
