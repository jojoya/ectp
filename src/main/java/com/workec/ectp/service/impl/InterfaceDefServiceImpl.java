package com.workec.ectp.service.impl;

import com.workec.ectp.dao.InterfaceDefDao;
import com.workec.ectp.entity.InterfaceDef;
import com.workec.ectp.entity.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.InterfaceDefService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;


@Service
public class InterfaceDefServiceImpl implements InterfaceDefService {

    @Autowired
    private InterfaceDefDao interfaceDefDao;

    /*查询列表*/
    public Result<InterfaceDef> getList() throws Exception {
        return ResultUtil.success(interfaceDefDao.findAll());
    }

    /*修改*/
    public Result<InterfaceDef> updateById(@Valid InterfaceDef interfaceDef, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }else if (!interfaceDefDao.exists(interfaceDef.getId())){
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
        interfaceDef.setId(interfaceDef.getId());
        interfaceDef.setModuleId(interfaceDef.getModuleId());
        interfaceDef.setLabel(interfaceDef.getLabel());
        interfaceDef.setReqMethod(interfaceDef.getReqMethod());
        interfaceDef.setReqProtocol(interfaceDef.getReqProtocol());
        interfaceDef.setDomainId(interfaceDef.getDomainId());
        interfaceDef.setUrl(interfaceDef.getUrl());
        interfaceDef.setDescription(interfaceDef.getDescription());

        return ResultUtil.success(interfaceDefDao.save(interfaceDef));
    }

    /*删除*/
    public Result<InterfaceDef> deleteById(Integer id) throws Exception{
        if (!interfaceDefDao.exists(id)){
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
        interfaceDefDao.delete(id);
        return ResultUtil.success();
    }

    /*按照id查询域名*/
    public Result<InterfaceDef> findById(Integer id) {
            return ResultUtil.success(interfaceDefDao.findOne(id));
    }

}
