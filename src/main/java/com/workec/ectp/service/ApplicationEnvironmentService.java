package com.workec.ectp.service;

import com.workec.ectp.entity.DO.ApplicationEnvironment;
import com.workec.ectp.entity.dto.Result;

/**
 * Created by user on 2018/1/10.
 */
public interface ApplicationEnvironmentService {

    Result<ApplicationEnvironment> getList();

}
