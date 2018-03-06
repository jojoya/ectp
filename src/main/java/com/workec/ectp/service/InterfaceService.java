package com.workec.ectp.service;

import com.workec.ectp.entity.Bo.Interface;
import com.workec.ectp.entity.Bo.ParamIdList;
import com.workec.ectp.entity.Dto.Result;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * Created by jojoya on 2018/1/10.
 */
public interface InterfaceService {

    /*修改*/
    Result<Interface> updateInterface(@Valid Interface itf, BindingResult bindingResult);

    Result<Interface> getInterface(Integer id);

    Result deleteParams(ParamIdList paramIdList);

//    Result<InterfaceParam> updateInterfaceParam(@Valid InterfaceParam params, BindingResult bindingResult);

}
