package com.workec.ectp.service;

import com.workec.ectp.dao.InterfaceMainDao;
import com.workec.ectp.entity.InterfaceMain;
import com.workec.ectp.entity.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class InterfaceMainService {

    @Autowired
    private InterfaceMainDao interfaceMainDao;

    /*查询列表*/
    public Result<InterfaceMain> getList() throws Exception {

        return ResultUtil.success(interfaceMainDao.findAll());
    }



    /*添加*/
    public Result<InterfaceMain> addInterfaceMain(@Valid InterfaceMain interfaceMain, BindingResult bindingResult) throws Exception{

        //检验字段值
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //名称去空格，不允许重复
        String value = interfaceMain.getValue().trim();
        Integer moduleId = interfaceMain.getModuleId();
        List<InterfaceMain> list = interfaceMainDao.findByModuleIdAndValue(moduleId,value);
        if(list.size()>0){
            return ResultUtil.error(
                    BaseResultEnum.DATA_EXIST.getCode(),
                    BaseResultEnum.DATA_EXIST.getMessage());
        }
        //添加
        interfaceMain.setModuleId(interfaceMain.getModuleId());
        interfaceMain.setValue(interfaceMain.getValue());
        interfaceMain.setDomainId(interfaceMain.getDomainId());
        interfaceMain.setRequestMethod(interfaceMain.getRequestMethod());
        interfaceMain.setDescription(interfaceMain.getDescription());

        return ResultUtil.success(interfaceMainDao.save(interfaceMain));
    }


    /*修改*/
    public Result<InterfaceMain> updateById(@Valid InterfaceMain interfaceMain, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }else if (!interfaceMainDao.exists(interfaceMain.getId())){
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
        interfaceMain.setId(interfaceMain.getId());
        interfaceMain.setModuleId(interfaceMain.getModuleId());
        interfaceMain.setValue(interfaceMain.getValue());
        interfaceMain.setDomainId(interfaceMain.getDomainId());
        interfaceMain.setRequestMethod(interfaceMain.getRequestMethod());
        interfaceMain.setDescription(interfaceMain.getDescription());

        return ResultUtil.success(interfaceMainDao.save(interfaceMain));
    }

    /*删除域名*/
    public Result<InterfaceMain> deleteById(Integer id) throws Exception{
        if (!interfaceMainDao.exists(id)){
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
        interfaceMainDao.delete(id);
        return ResultUtil.success();
    }

    /*按照id查询域名*/
    public Result<InterfaceMain> findById(Integer id) {
            return ResultUtil.success(interfaceMainDao.findOne(id));
    }

}
