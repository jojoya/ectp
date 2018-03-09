package com.workec.ectp.controller;

import com.workec.ectp.entity.Do.Case;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.service.CaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController()
public class CaseController {
    @Autowired
    CaseService caseService;

    @ApiOperation(value="获取接口对应用例列表", notes="获取接口对应用例列表")
    @GetMapping(value = "/case/list/{interfaceId}")
    public Result<Case> list(@PathVariable("interfaceId") Integer interfaceId){
        return caseService.getListByInterfaceId(interfaceId);
    }

    @ApiOperation(value="保存用例信息", notes="保存用例信息")
    @PostMapping(value = "/case/save")
    public Result<Case> updateCase(@RequestBody Case cs){
        return caseService.updateCase(cs);
    }

    @ApiOperation(value="删除用例信息", notes="删除用例信息")
    @GetMapping(value = "/case/delete/{id}")
    public Result<Case> deleteCaseById(@PathVariable("id") Integer id){
        return caseService.deleteCaseById(id);
    }
}
