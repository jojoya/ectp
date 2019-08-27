package com.workec.ectp.controller;

import com.workec.ectp.entity.Bo.*;
import com.workec.ectp.entity.Do.CaseAssert;
import com.workec.ectp.entity.Do.CallInterfaceData;
import com.workec.ectp.entity.Do.Case;
import com.workec.ectp.entity.Do.MiddleParam;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.entity.Vo.CallInterfaceStepAdjustment;
import com.workec.ectp.service.AssertService;
import com.workec.ectp.service.CallInterfaceService;
import com.workec.ectp.service.CaseService;
import com.workec.ectp.service.MiddleParamService;
import com.workec.ectp.service.impl.TestService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController()
public class CaseController {

    @Autowired
    CaseService caseService;

    @Autowired
    CallInterfaceService callInterfaceService;

    @Autowired
    AssertService assertService;

    @Autowired
    MiddleParamService middleParamService;

    @Autowired
    TestService testService;



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
    public Result<CaseExecuteResult> executeById(@RequestBody ExecuteOneCaseInputParams params){
        return caseService.executeOneCase(params);
    }





    @ApiOperation(value="获取单个步骤信息", notes="获取单个步骤信息")
    @GetMapping(value = "/case/step/info/{id}")
    public Result<CallInterfaceInfo> getCallInterface(@PathVariable("id") Integer id){
        return caseService.getCallInterfaceInfo(id);
    }

    @ApiOperation(value = "查询单个用例下步骤列表", notes = "按照caseId查询步骤列表")
    @GetMapping(value = "/case/step/getList/{caseId}")
    public Result<GroupedCallInterface> getListByCaseId(@PathVariable Integer caseId) {
        return callInterfaceService.getListByCaseId(caseId);
    }

    @ApiOperation(value = "保存步骤信息", notes = "保存步骤信息")
    @PostMapping(value = "/case/step/save")
    public Result<CallInterfaceData> updateCallInterface(@RequestBody CallInterfaceDataSave cifds) {
        return callInterfaceService.updateCallInterface(cifds);
    }

    @ApiOperation(value = "删除步骤信息", notes = "删除步骤信息")
    @GetMapping(value = "/case/step/delete/{id}")
    public Result deleteCallInterfaceById(@PathVariable Integer id) {
        return callInterfaceService.deleteCallInterfaceById(id);
    }

    @ApiOperation(value = "调整步骤顺序", notes = "调整步骤顺序")
    @ApiImplicitParam(name = "adjustment", value = "中间变量实体", required = true, dataType = "CallInterfaceStepAdjustment")
    @PostMapping(value = "/case/step/adjust")
    public Result adjustCallInterface(@RequestBody CallInterfaceStepAdjustment adjustment) {
        return callInterfaceService.adjustCallInterfaceStep(adjustment);
    }







    @ApiOperation(value = "获取中间变量", notes = "根据用例Id获取中间变量")
    @GetMapping(value = "/case/step/middleparam/list/{caseId}")
    public Result<List<CallInterfaceAndMiddleValues>> getCallInterfaceAndMiddleValuesByCaseId(@PathVariable Integer caseId) {
        return caseService.getCallInterfaceAndMiddleValuesByCaseId(caseId);
    }

    @ApiOperation(value="修改中间变量信息", notes="修改中间变量信息")
    @ApiImplicitParam(name = "mp", value = "中间变量实体", required = true, dataType = "MiddleParam")
    @PostMapping(value = "/case/step/middleparam/update")
    public Result<MiddleParam> updateMiddleParam(@RequestBody MiddleParam mp){
        return middleParamService.updateMiddleParam(mp);
    }

    @ApiOperation(value="删除中间变量信息", notes="删除中间变量信息")
    @GetMapping(value = "/case/step/middleparam/delete/{id}")
    public Result<MiddleParam> deleteMiddleParam(@PathVariable("id") Integer id){
        return middleParamService.deleteMiddleParam(id);
    }





    @ApiOperation(value="保存校验信息",notes="保存校验信息")
    @PostMapping(value = "/case/step/assert/update")
    public Result<CaseAssert> updateAssert(@RequestBody CaseAssert ast){
        return assertService.updateAssert(ast);
    }

    @ApiOperation(value="删除校验信息",notes="删除校验信息")
    @GetMapping(value = "/case/step/assert/delete/{id}")
    public Result deleteAssert(@PathVariable Integer id){
        return assertService.deleteAssert(id);
    }






    @ApiOperation(value = "测试应用环境缓存", notes = "测试应用环境缓存")
    @PostMapping(value = "/case/testEnv")
    public Result testEnv(@RequestBody Map<String,Object> map) {
        return testService.testEnv(map);
    }


    @ApiOperation(value = "测试全局变量缓存", notes = "测试全局变量缓存")
    @PostMapping(value = "/case/testGlobal")
    public Result testGlobal(@RequestBody Map<String,Object> map) {
        return testService.testGlobal(map);
    }



}
