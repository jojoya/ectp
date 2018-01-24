package com.workec.ectp.service;

import com.workec.ectp.entity.DO.Domain;
import com.workec.ectp.entity.dto.Result;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * Created by user on 2018/1/10.
 */
public interface DomainService {

    /*查询列表*/
    Result<Domain> getList() throws Exception;

    /*添加*/
    Result<Domain> addDomain(@Valid Domain domain, BindingResult bindingResult);

    /*更新*/
    Result<Domain> updateById(@Valid Domain domain, BindingResult bindingResult);

    /*删除*/
    Result<Domain> deleteById(Integer id) throws Exception;

    /*查询*/
    Result<Domain> findById(Integer id);
}
