package com.workec.ectp.service;

import com.workec.ectp.entity.Do.MiddleParam;
import com.workec.ectp.entity.Dto.Result;

import java.util.List;


public interface MiddleParamService {


    //修改中间变量信息
    Result<MiddleParam> updateMiddleParam(MiddleParam mp);

    //删除中间变量信息

    Result<MiddleParam> deleteMiddleParam(Integer id);

    }
