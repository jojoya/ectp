package com.workec.ectp.service;

import com.workec.ectp.entity.DoBak.InterfaceDef;
import com.workec.ectp.entity.Dto.Result;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * Created by user on 2018/1/10.
 */
public interface InterfaceDefService {

    /*查询列表*/
    Result<InterfaceDef> getList() throws Exception;

    /*修改*/
    Result<InterfaceDef> updateById(@Valid InterfaceDef interfaceDef, BindingResult bindingResult);

    /*删除域名*/
    Result<InterfaceDef> deleteById(Integer id) throws Exception;

    /*按照id查询域名*/
    Result<InterfaceDef> findById(Integer id);

}
