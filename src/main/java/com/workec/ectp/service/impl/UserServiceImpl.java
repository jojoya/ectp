package com.workec.ectp.service.impl;

import com.workec.ectp.components.DataCacheComponent;
import com.workec.ectp.dao.jpa.UserDao;
import com.workec.ectp.entity.Do.User;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.entity.Bo.UserLoginInfo;
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

    @Autowired
    private DataCacheComponent dataCacheComponent;


    /*登录*/
    @Override
    public Result<User> login(UserLoginInfo userLoginInfo, BindingResult bindingResult) {
        //检验字段值
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        List<User> list = userDao.findByAccountAndPassword(userLoginInfo.getAccount().trim(), userLoginInfo.getPassword());
        if(list.size()==1) {
            return ResultUtil.success(list.get(0));
        }else if(list.size()>1){
            return ResultUtil.error(
                    BaseResultEnum.USER_DUPLICATION.getCode(),
                    BaseResultEnum.USER_DUPLICATION.getMessage());
        }else {
            return ResultUtil.error(
                    BaseResultEnum.USER_ERROR.getCode(),
                    BaseResultEnum.USER_ERROR.getMessage());
        }
    }

    /*添加*/
    @Override
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

        User saveResult = userDao.save(user);

        //缓存用户的全局变量信息
        if(saveResult!=null) {
            dataCacheComponent.initGlobalParamInfo();
        }

        return ResultUtil.success(saveResult);
    }


}
