package com.workec.ectp.service;

import com.workec.ectp.entity.DO.ApplicationEnvironment;
import com.workec.ectp.entity.DO.ApplicationEnvironmentDetail;
import com.workec.ectp.entity.dto.Result;

/**
 * Created by user on 2018/1/10.
 */
public interface ApplicationEnvironmentService {

    /*获取应用环境列表*/
    Result<ApplicationEnvironment> getList();

    /*初始化应用环境参数*/
    Result<ApplicationEnvironmentDetail> initDetail(ApplicationEnvironment environment);
}
