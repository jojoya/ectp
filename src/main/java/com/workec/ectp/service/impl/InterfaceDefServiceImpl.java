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



    /*添加*/
    public Result<InterfaceDef> addInterfaceMain(@Valid InterfaceDef interfaceDef, BindingResult bindingResult) throws Exception{

        //检验字段值
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //名称去空格，不允许重复
        String value = interfaceDef.getValue().trim();
        Integer moduleId = interfaceDef.getModuleId();
        List<InterfaceDef> list = interfaceDefDao.findByModuleIdAndValue(moduleId,value);
        if(list.size()>0){
            return ResultUtil.error(
                    BaseResultEnum.DATA_EXIST.getCode(),
                    BaseResultEnum.DATA_EXIST.getMessage());
        }
        //添加
        interfaceDef.setModuleId(interfaceDef.getModuleId());
        interfaceDef.setValue(interfaceDef.getValue());
        interfaceDef.setDomainId(interfaceDef.getDomainId());
        interfaceDef.setRequestMethod(interfaceDef.getRequestMethod());
        interfaceDef.setDescription(interfaceDef.getDescription());

        return ResultUtil.success(interfaceDefDao.save(interfaceDef));
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
        interfaceDef.setValue(interfaceDef.getValue());
        interfaceDef.setDomainId(interfaceDef.getDomainId());
        interfaceDef.setRequestMethod(interfaceDef.getRequestMethod());
        interfaceDef.setDescription(interfaceDef.getDescription());

        return ResultUtil.success(interfaceDefDao.save(interfaceDef));
    }

    /*删除域名*/
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
