package com.workec.ectp.controller;

import com.workec.ectp.entity.DoBak.Domain;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.service.DomainService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController()
public class DomainController {

    @Autowired
    DomainService domainService;


    @ApiOperation(value="查询域名列表", notes="查询所有域名")
    @GetMapping(value = "/domain/getList")
    public Result<Domain> getDomainList() throws Exception {
        return domainService.getList();
    }


    @ApiOperation(value="添加域名", notes="添加域名")
    @ApiImplicitParam(name = "domain", value = "域名详细实体", required = true, dataType = "Domain")
    @PostMapping(value = "/domain/add")
    public Result<Domain> addDomain(@Valid @RequestBody Domain domain, BindingResult bindingResult){
        return domainService.addDomain(domain,bindingResult);
    }


    @ApiOperation(value="修改域名", notes="修改域名")
    @ApiImplicitParam(name = "domain", value = "域名详细实体", required = true, dataType = "Domain")
    @PostMapping(value = "/domain/update")
    public Result<Domain> updateModule(@Valid @RequestBody Domain domain, BindingResult bindingResult){
        return domainService.updateById(domain,bindingResult);
    }


    @ApiOperation(value="删除域名", notes="根据域名id删除域名")
    @GetMapping(value = "/domain/delete/{id}")
    public Result<Domain> deleteDomainById(@PathVariable("id") Integer id) throws Exception {
        return domainService.deleteById(id);
    }


    @ApiOperation(value="查询域名", notes="根据域名id查询域名")
    @GetMapping(value = "/domain/findById/{id}")
    public Result<Domain> findDomainById(@PathVariable("id") Integer id) throws Exception {
        return domainService.findById(id);
    }

}
