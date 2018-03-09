package com.workec.ectp.service;

import com.workec.ectp.entity.Do.Case;
import com.workec.ectp.entity.Dto.Result;
import io.swagger.models.auth.In;


public interface CaseService {

    //获取用例列表
    Result<Case> getListByInterfaceId(Integer interfaceId);

    //保存用例信息
    Result<Case> updateCase(Case cs);

    //删除用例
    Result<Case> deleteCaseById(Integer id);

}
