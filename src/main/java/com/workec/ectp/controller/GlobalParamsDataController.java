package com.workec.ectp.controller;

import com.workec.ectp.entity.DO.GlobalParamsData;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.service.GlobalParamsDataService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
@RestController()
public class GlobalParamsDataController {

    @Autowired
    GlobalParamsDataService globalParamsDataService;

    @ApiOperation(value="全局参数值保存", notes="全局参数值保存")
    @ApiImplicitParam(name = "globalParamsData", value = "全局参数值保存", required = true, dataType = "GlobalParamsData")
    @PostMapping(value = "globalParamsData/save")
    public Result<GlobalParamsData> save(@Valid @RequestBody  GlobalParamsData globalParamsData, BindingResult bindingResult){
        return globalParamsDataService.save(globalParamsData,bindingResult);
    }


}
