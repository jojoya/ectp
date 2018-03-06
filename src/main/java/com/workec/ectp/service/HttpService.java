package com.workec.ectp.service;

import com.workec.ectp.entity.Bo.HttpDebugInformation;
import com.workec.ectp.entity.Bo.HttpResult;
import com.workec.ectp.entity.Dto.Result;
import org.springframework.validation.BindingResult;

/**
 * Created by user on 2018/1/10.
 */
public interface HttpService {


    /*接口调试*/
    Result<HttpResult> doDebug(HttpDebugInformation httpDebugInformation, BindingResult bindingResult);

}
