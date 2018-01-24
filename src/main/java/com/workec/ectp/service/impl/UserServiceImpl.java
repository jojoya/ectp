package com.workec.ectp.service.impl;

import com.workec.ectp.dao.UserDao;
import com.workec.ectp.entity.DO.User;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.UserService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    /*添加*/
    public Result<User> addUser(@Valid User user, BindingResult bindingResult) {

        //检验字段值
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //名称去空格，不允许重复
        List<User> listuser = userDao.findByName(user.getName().trim());
        if(listuser.size()>0){
            return ResultUtil.error(
                    BaseResultEnum.DATA_EXIST.getCode(),
                    BaseResultEnum.DATA_EXIST.getMessage());
        }
        //帐号去空格，不允许重复
        List<User> listaccount = userDao.findByAccount(user.getAccount().trim());
        if(listaccount.size()>0){
            return ResultUtil.error(
                    BaseResultEnum.DATA_EXIST.getCode(),
                    BaseResultEnum.DATA_EXIST.getMessage());
        }

        return ResultUtil.success(userDao.save(user));
    }


}
