package com.workec.ectp.controller;

import com.workec.ectp.entity.Bo.CallInterfaceDataSave;
import com.workec.ectp.entity.Bo.CallInterfaceInfo;
import com.workec.ectp.entity.Bo.CaseExecuteResult;
import com.workec.ectp.entity.Bo.GroupedCallInterface;
import com.workec.ectp.entity.Do.CallInterface;
import com.workec.ectp.entity.Do.CallInterfaceData;
import com.workec.ectp.entity.Do.Case;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.service.CallInterfaceService;
import com.workec.ectp.service.CaseService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping(value = "/case/execute/{id}")
    public Result<CaseExecuteResult> executeById(@PathVariable("id") Integer id){
        return caseService.executeById(id);
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
