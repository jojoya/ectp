package com.workec.ectp.dao.JdbcTemplates;

import com.workec.ectp.entity.Bo.GlobalParamsDataInfo;

import java.util.List;

/**
 * Created by user on 2018/3/5.
 */
public interface IGlobalParamsDataDao {

    List<GlobalParamsDataInfo> findByUserAndEnv(Integer userId,Integer EnvId);
}
