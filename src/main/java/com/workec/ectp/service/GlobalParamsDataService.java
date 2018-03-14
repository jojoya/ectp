package com.workec.ectp.service;

import com.workec.ectp.entity.Bo.GlobalParamsDataInfo;
import com.workec.ectp.entity.DoBak.GlobalParamsData;
import com.workec.ectp.entity.Dto.Result;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/1/10.
 */
public interface GlobalParamsDataService {

    /*添加*/
    Result<GlobalParamsData> save(@Valid GlobalParamsData globalParamsData, BindingResult bindingResult);

    Result<List<GlobalParamsDataInfo>> getListByUserAndEnv(Map<String,Object> reqMap);
}
