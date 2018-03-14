package com.workec.ectp.dao.jdbc;


import com.workec.ectp.entity.Bo.AppEnvDetailInfo;
import com.workec.ectp.entity.Do.InterfaceParam;

import java.util.List;

public interface IInterfaceParamDao {

    List<InterfaceParam> findByCallInterfaceIdAndInterfaceIdAndLocation(Integer callInterfaceId,Integer interfaceId, Integer location);
}
