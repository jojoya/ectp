package com.workec.ectp.service.impl;

import com.workec.ectp.dao.jpa.DomainDao;
import com.workec.ectp.entity.DoBak.Domain;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.DomainService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;


@Service
public class DomainServiceImpl implements DomainService {

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
        String value = domain.getName().trim();
        List<Domain> list = domainDao.findByName(value);
        if(list.size()>0){
            return ResultUtil.error(
                    BaseResultEnum.DATA_EXIST.getCode(),
                    BaseResultEnum.DATA_EXIST.getMessage());
        }
        //添加
        domain.setName(domain.getName());

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
        domain.setName(domain.getName());

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
