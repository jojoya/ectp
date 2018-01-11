package com.workec.ectp.service;

import com.workec.ectp.entity.InterfaceMain;
import com.workec.ectp.entity.Result;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * Created by user on 2018/1/10.
 */
public interface InterfaceMainService {

    /*查询列表*/
    Result<InterfaceMain> getList() throws Exception;

    /*添加*/
    Result<InterfaceMain> addInterfaceMain(@Valid InterfaceMain interfaceMain, BindingResult bindingResult) throws Exception;

    /*修改*/
    Result<InterfaceMain> updateById(@Valid InterfaceMain interfaceMain, BindingResult bindingResult);

    /*删除域名*/
    Result<InterfaceMain> deleteById(Integer id) throws Exception;

    /*按照id查询域名*/
    Result<InterfaceMain> findById(Integer id);

}
