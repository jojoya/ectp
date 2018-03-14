package com.workec.ectp.service;

import com.workec.ectp.entity.Bo.CaseExecuteResult;
import com.workec.ectp.entity.DoBak.Case;
import com.workec.ectp.entity.Dto.Result;

import java.util.List;


public interface CaseService {

    //获取用例列表
    Result<List<Case>> getListByInterfaceId(Integer interfaceId);

    //保存用例信息
    Result<Case> updateCase(Case cs);

    //删除用例
    Result<Case> deleteCaseById(Integer id);

    //根据用例id执行用例
    Result<CaseExecuteResult> executeById(Integer caseId);

    //根调用id获取调用数据详情
    Result getCallInterfaceInfo(Integer caseId);

    }
