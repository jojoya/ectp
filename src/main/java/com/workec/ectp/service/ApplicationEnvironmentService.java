package com.workec.ectp.service;

import com.workec.ectp.entity.Do.ApplicationEnvironment;
import com.workec.ectp.entity.Do.ApplicationEnvironmentDetail;
import com.workec.ectp.entity.Bo.AppEnvDetailInfo;
import com.workec.ectp.entity.Dto.Result;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Created by user on 2018/1/10.
 */
public interface ApplicationEnvironmentService {

    /*获取应用环境列表*/
    Result<ApplicationEnvironment> getList();

    /*初始化应用环境参数*/
    Result<ApplicationEnvironmentDetail> initDetail(ApplicationEnvironment environment,BindingResult bindingResult);


    /*获取应用环境配置表*/
    Result<List<AppEnvDetailInfo>> findByEnvId(Integer envId);

    /*更新明细*/
    Result<ApplicationEnvironmentDetail> updateDetail(AppEnvDetailInfo appEnvDetailInfo);
}
