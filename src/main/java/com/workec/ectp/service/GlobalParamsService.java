package com.workec.ectp.service;

import com.workec.ectp.entity.Do.GlobalParams;
import com.workec.ectp.entity.Dto.Result;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * Created by user on 2018/1/10.
 */
public interface GlobalParamsService {


    Result<GlobalParams> getList() throws Exception;
    /*添加*/
    Result<GlobalParams> save(@Valid GlobalParams globalParams, BindingResult bindingResult);

}
