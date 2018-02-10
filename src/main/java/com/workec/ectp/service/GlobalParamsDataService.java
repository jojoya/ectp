package com.workec.ectp.service;

import com.workec.ectp.entity.DO.GlobalParams;
import com.workec.ectp.entity.DO.GlobalParamsData;
import com.workec.ectp.entity.dto.Result;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * Created by user on 2018/1/10.
 */
public interface GlobalParamsDataService {

    /*添加*/
    Result<GlobalParamsData> save(@Valid GlobalParamsData globalParamsData, BindingResult bindingResult);

}
