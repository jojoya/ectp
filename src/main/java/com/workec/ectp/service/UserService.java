package com.workec.ectp.service;

import com.workec.ectp.entity.Do.User;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.entity.Bo.UserLoginInfo;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * Created by user on 2018/1/10.
 */
public interface UserService {


    /*添加*/
    Result<User> addUser(@Valid User user, BindingResult bindingResult);

    Result<User> login(@Valid UserLoginInfo userLoginInfo, BindingResult bindingResult );
}
