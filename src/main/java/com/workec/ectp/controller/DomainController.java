package com.workec.ectp.controller;

import com.workec.ectp.entity.Domain;
import com.workec.ectp.entity.Result;
import com.workec.ectp.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController()
public class DomainController {

    @Autowired
    DomainService domainService;

    /* 查询域名列表 */
    @GetMapping(value = "/domain/getList")
    public Result<Domain> getDomainList() throws Exception {
        return domainService.getList();
    }

    /* 添加域名 */
    @PostMapping(value = "/domain/add")
    public Result<Domain> addDomain(@Valid @RequestBody Domain domain, BindingResult bindingResult){
        return domainService.addDomain(domain,bindingResult);
    }

    /* 修改域名 */
    @PatchMapping(value = "/domain/update")
    public Result<Domain> updateModule(@Valid @RequestBody Domain domain, BindingResult bindingResult){
        return domainService.updateById(domain,bindingResult);
    }

    /* 删除域名 */
    @DeleteMapping(value = "/domain/delete/{id}")
    public Result<Domain> deleteDomainById(@PathVariable("id") Integer id) throws Exception {
        return domainService.deleteById(id);
    }

    /* 按照id查询域名 */
    @GetMapping(value = "/domain/findById/{id}")
    public Result<Domain> findDomainById(@PathVariable("id") Integer id) throws Exception {
        return domainService.findById(id);
    }

}
