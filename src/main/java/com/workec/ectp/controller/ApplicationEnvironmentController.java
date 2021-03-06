package com.workec.ectp.controller;

import com.workec.ectp.entity.Do.ApplicationEnvironment;
import com.workec.ectp.entity.Bo.AppEnvDetailInfo;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.service.ApplicationEnvironmentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController()
public class ApplicationEnvironmentController {

    @Autowired
    ApplicationEnvironmentService applicationEnvironmentService;

    @ApiOperation(value="获取测试环境列表", notes="获取测试环境列表")
    @GetMapping(value = "/appenv/list")
    public Result<ApplicationEnvironment> list(){
        return applicationEnvironmentService.getList();
    }



    @ApiOperation(value="初始化参数配置", notes="初始化参数配置")
    @ApiImplicitParam(name = "environment", value = "应用环境和IP", required = true, dataType = "ApplicationEnvironment")
    @PostMapping(value = "/appenv/initDetail")
    public Result initDetail(@Valid @RequestBody ApplicationEnvironment environment, BindingResult bindingResult){
        return applicationEnvironmentService.initDetail(environment,bindingResult);
    }


    @ApiOperation(value="根据应用环境ID获取配置详情", notes="根据应用环境ID获取配置详情")
    @GetMapping(value = "/appenv/findByEnvId/{envId}")
    public Result<List<AppEnvDetailInfo>> findByEnvId(@PathVariable("envId") Integer envId){
        return applicationEnvironmentService.findByEnvId(envId);
    }

    @ApiOperation(value="更新参数", notes="更新参数")
    @ApiImplicitParam(name = "appEnvDetailInfo", value = "参数信息和IP", required = true, dataType = "AppEnvDetailInfo")
    @PostMapping(value = "/appenv/updateDetail")
    public Result updateDetail(@Valid @RequestBody AppEnvDetailInfo appEnvDetailInfo){
        return applicationEnvironmentService.updateDetail(appEnvDetailInfo);
    }
}
