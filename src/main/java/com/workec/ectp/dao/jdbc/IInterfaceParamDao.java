package com.workec.ectp.dao.jdbc;


import com.workec.ectp.entity.Bo.InterfaceParamForCallInfo;

import java.util.List;

public interface IInterfaceParamDao {

    List<InterfaceParamForCallInfo> findByCallInterfaceIdAndInterfaceIdAndLocation(Integer callInterfaceId, Integer interfaceId, Integer location);

    List<InterfaceParamForCallInfo> findByInterfaceIdAndLocation(Integer interfaceId, Integer location);

}
