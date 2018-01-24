package com.workec.ectp.service;

import com.workec.ectp.entity.dto.Interface;
import com.workec.ectp.entity.dto.Result;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * Created by jojoya on 2018/1/10.
 */
public interface InterfaceService {

    /*修改*/
    Result<Interface> updateInterface(@Valid Interface itf, BindingResult bindingResult);

    Result<Interface> getInterface(Integer id);

    Result deleteParams(String ids);

//    Result<InterfaceParam> updateInterfaceParam(@Valid InterfaceParam params, BindingResult bindingResult);

}
