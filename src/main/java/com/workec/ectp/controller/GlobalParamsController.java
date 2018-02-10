package com.workec.ectp.controller;

import com.workec.ectp.entity.DO.GlobalParams;
import com.workec.ectp.entity.dto.Result;
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

@CrossOrigin
@RestController()
public class GlobalParamsController {

    @Autowired
    GlobalParamsService globalParamsService;

    @ApiOperation(value="全局参数保存", notes="全局参数保存")
    @ApiImplicitParam(name = "globalParams", value = "全局参数保存", required = true, dataType = "GlobalParams")
    @PostMapping(value = "/globalparams/save")
    public Result<GlobalParams> save(@Valid @RequestBody GlobalParams globalParams, BindingResult bindingResult){
        return globalParamsService.save(globalParams,bindingResult);
    }

    @ApiOperation(value="全局参数查询", notes="全局参数查询")
    @ApiImplicitParam(name = "globalParams", value = "全局参数查询", required = true, dataType = "GlobalParams")
    @PostMapping(value = "/globalparams/list")
    public Result<GlobalParams> getlist() throws Exception{
        return globalParamsService.getList();
    }

}
