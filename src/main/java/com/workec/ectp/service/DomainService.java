package com.workec.ectp.service;

import com.workec.ectp.dao.DomainDao;
import com.workec.ectp.dao.ProjectDao;
import com.workec.ectp.entity.Domain;
import com.workec.ectp.entity.Module;
import com.workec.ectp.entity.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.utils.ResultUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;


@Service
public class DomainService {

    @Autowired
    private DomainDao domainDao;

    /*查询列表*/
    public Result<Domain> getList() throws Exception {

        return ResultUtil.success(domainDao.findAll());
    }

    /*添加*/
    public Result<Domain> addDomain(@Valid Domain domain, BindingResult bindingResult) {

        //检验字段值
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //名称去空格，不允许重复
        String value = domain.getValue().trim();
        if(domainDao.findByValue(value)!=null){
            return ResultUtil.error(
                    BaseResultEnum.DATA_EXIST.getCode(),
                    BaseResultEnum.DATA_EXIST.getMessage());
        }
        //添加
        domain.setValue(domain.getValue());

        return ResultUtil.success(domainDao.save(domain));
    }

    public Result<Domain> updateById(@Valid Domain domain, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }else if (!domainDao.exists(domain.getId())){
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
        domain.setValue(domain.getValue());

        return ResultUtil.success(domainDao.save(domain));
    }

    /*删除域名*/
    public Result<Domain> deleteById(Integer id) throws Exception{
        if (!domainDao.exists(id)){
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
        domainDao.delete(id);
        return ResultUtil.success();
    }

    /*按照id查询域名*/
    public Result<Domain> findById(Integer id) {
            return ResultUtil.success(domainDao.findOne(id));
    }

}
