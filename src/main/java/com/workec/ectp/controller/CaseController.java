package com.workec.ectp.controller;

import com.workec.ectp.entity.Bo.*;
import com.workec.ectp.entity.Do.CallInterfaceData;
import com.workec.ectp.entity.Do.Case;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.service.CallInterfaceService;
import com.workec.ectp.service.CaseService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController()
public class CaseController {

    @Autowired
    CaseService caseService;

    @Autowired
    CallInterfaceService callInterfaceService;

    @ApiOperation(value="获取用例列表", notes="按照接口id获取用例列表")
    @GetMapping(value = "/case/list/{interfaceId}")
    public Result<List<Case>> list(@PathVariable("interfaceId") Integer interfaceId){
        return caseService.getListByInterfaceId(interfaceId);
    }




    @ApiOperation(value="保存用例信息", notes="新增和更新用例信息")
    @PostMapping(value = "/case/save")
    public Result<Case> updateCase(@RequestBody Case cs){
        return caseService.updateCase(cs);
    }

    @ApiOperation(value="删除用例信息", notes="删除用例信息")
    @GetMapping(value = "/case/delete/{id}")
    public Result<Case> deleteCaseById(@PathVariable("id") Integer id){
        return caseService.deleteCaseById(id);
    }

    @ApiOperation(value="执行单个用例", notes="按照单个用例id执行用例")
    @ApiImplicitParam(name = "params", value = "输入用户、测试环境以及要执行的用例信息", required = true, dataType = "ExecuteOneCaseInputParams")
    @PostMapping(value = "/case/executeOne")
    public Result<CaseExecuteResult> executeById(@Valid @RequestBody ExecuteOneCaseInputParams params, BindingResult result){
        return caseService.executeOneCase(params,result);
    }





    @ApiOperation(value="获取单个步骤信息", notes="获取单个步骤信息")
    @GetMapping(value = "/case/step/info/{id}")
    public Result<CallInterfaceInfo> getCallInterface(@PathVariable("id") Integer id){
        return caseService.getCallInterfaceInfo(id);
    }

    @ApiOperation(value = "查询单个用例下步骤列表", notes = "按照caseId查询步骤列表")
    @PostMapping(value = "/case/step/getList/{caseId}")
    public Result<GroupedCallInterface> getListByCaseId(@PathVariable Integer caseId) {
        return callInterfaceService.getListByCaseId(caseId);
    }

    @ApiOperation(value = "保存步骤信息", notes = "保存步骤信息")
    @PostMapping(value = "/case/step/save")
    public Result<CallInterfaceData> updateCallInterface(@RequestBody CallInterfaceDataSave cifds) {
        return callInterfaceService.updateCallInterface(cifds);
    }

    @ApiOperation(value = "删除步骤信息", notes = "删除步骤信息")
    @PostMapping(value = "/case/step/delete/{id}")
    public Result deleteCallInterfaceById(@PathVariable Integer id) {
        return callInterfaceService.deleteCallInterfaceById(id);
    }

}
