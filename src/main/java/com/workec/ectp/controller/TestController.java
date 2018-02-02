package com.workec.ectp.controller;

import com.workec.ectp.entity.DO.User;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.http.HttpAPIService;
import com.workec.ectp.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController()
public class TestController {

    @Autowired
    HttpAPIService httpAPIService;

    @GetMapping(value = "/test/get")
    public String addser() throws Exception{
        return httpAPIService.doGet("http://www.baidu.com");
    }
}
