package com.workec.ectp.service;

import com.workec.ectp.entity.Bo.InterfaceDebugData;
import com.workec.ectp.entity.Bo.ParamIdList;
import com.workec.ectp.entity.Dto.Result;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * Created by jojoya on 2018/1/10.
 */
public interface InterfaceService {

    /*修改*/
    Result<InterfaceDebugData> updateInterface(@Valid InterfaceDebugData itf, BindingResult bindingResult);

    Result<InterfaceDebugData> getInterface(Integer id);

    Result deleteParams(ParamIdList paramIdList);

}
