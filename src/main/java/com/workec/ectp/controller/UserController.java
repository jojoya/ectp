package com.workec.ectp.controller;

import com.workec.ectp.entity.DoBak.User;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.entity.Bo.UserLoginInfo;
import com.workec.ectp.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController()
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value="用户注册", notes="注册用户")
    @ApiImplicitParam(name = "user", value = "用户实体详情信息", required = true, dataType = "User")
    @PostMapping(value = "/user/add")
    public Result<User> addUser(@Valid @RequestBody User user, BindingResult bindingResult){
        return userService.addUser(user,bindingResult);
    }

    @ApiOperation(value="用户登录", notes="用户登录")
    @ApiImplicitParam(name = "userLoginInfo", value = "用户实体详情信息", required = true, dataType = "UserLoginInfo")
    @PostMapping(value = "/user/login")
    public Result<User> login(@Valid @RequestBody UserLoginInfo userLoginInfo, BindingResult bindingResult){
        return userService.login(userLoginInfo,bindingResult);
    }
}
