package com.workec.ectp.controller;

import com.workec.ectp.entity.DO.ApplicationEnvironment;
import com.workec.ectp.entity.DO.DataEnvironment;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.service.ApplicationEnvironmentService;
import com.workec.ectp.service.DataEnvironmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}