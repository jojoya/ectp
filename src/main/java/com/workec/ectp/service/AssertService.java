package com.workec.ectp.service;

import com.workec.ectp.entity.Do.CaseAssert;
import com.workec.ectp.entity.Dto.Result;


public interface AssertService {

    //保存或者修改检查点
    Result<CaseAssert> updateAssert(CaseAssert ast);

    //删除检查点
    Result deleteAssert(Integer id);

    }
