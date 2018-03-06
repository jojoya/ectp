package com.workec.ectp.dao.jdbc;


import com.workec.ectp.entity.Bo.AppEnvDetailInfo;

import java.util.List;

public interface IAppEnvDataDao {

    List<AppEnvDetailInfo> findByEnvId(Integer envId);
}
