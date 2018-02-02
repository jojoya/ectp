package com.workec.ectp.controller;

import com.workec.ectp.entity.DO.DataEnvironment;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.service.DataEnvironmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController()
public class DataEnvironmentController {

    @Autowired
    DataEnvironmentService dataEnvironmentService;


    @ApiOperation(value="获取数据库环境列表", notes="获取数据库环境列表")
    @GetMapping(value = "/database/list")
    public Result<DataEnvironment> list(){
        return dataEnvironmentService.getList();
    }
}
