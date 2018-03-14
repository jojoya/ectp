package com.workec.ectp.controller;

import com.workec.ectp.entity.DoBak.GlobalParams;
import com.workec.ectp.entity.DoBak.GlobalParamsData;
import com.workec.ectp.entity.Bo.GlobalParamsDataInfo;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.service.GlobalParamsDataService;
import com.workec.ectp.service.GlobalParamsService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController()
public class GlobalParamsController {

    @Autowired
    GlobalParamsService globalParamsService;

    @Autowired
    GlobalParamsDataService globalParamsDataService;


    @ApiOperation(value="保存全局参数", notes="保存全局参数")
    @ApiImplicitParam(name = "globalParams", value = "保存全局参数", required = true, dataType = "GlobalParams")
    @PostMapping(value = "/globalParams/save")
    public Result<GlobalParams> save(@Valid @RequestBody GlobalParams globalParams, BindingResult bindingResult){
        return globalParamsService.save(globalParams,bindingResult);
    }

    @ApiOperation(value="查询全局参数", notes="查询全局参数")
    @PostMapping(value = "/globalParams/list")
    public Result<GlobalParams> getList() throws Exception{
        return globalParamsService.getList();
    }

    @ApiOperation(value="用户根据数据环境获取全局参数配置表", notes="用户根据数据环境获取全局参数配置表")
    @PostMapping(value = "globalParamsData/listByUserAndEnv")
    public Result<List<GlobalParamsDataInfo>> getListByUserAndEnv(@RequestBody Map<String,Object> reqMap){
        return globalParamsDataService.getListByUserAndEnv(reqMap);
    }

    @ApiOperation(value="保存全局参数值", notes="保存全局参数值")
    @ApiImplicitParam(name = "globalParamsData", value = "保存全局参数值", required = true, dataType = "GlobalParamsData")
    @PostMapping(value = "globalParamsData/save")
    public Result<GlobalParamsData> save(@Valid @RequestBody  GlobalParamsData globalParamsData, BindingResult bindingResult){
        return globalParamsDataService.save(globalParamsData,bindingResult);
    }

}
