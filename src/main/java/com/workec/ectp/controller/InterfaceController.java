package com.workec.ectp.controller;

import com.workec.ectp.entity.Bo.HttpDebugInformation;
import com.workec.ectp.entity.Bo.HttpResult;
import com.workec.ectp.entity.Bo.Interface;
import com.workec.ectp.entity.Bo.ParamIdList;
import com.workec.ectp.entity.Do.InterfaceDef;
import com.workec.ectp.entity.Dto.*;
import com.workec.ectp.service.HttpService;
import com.workec.ectp.service.InterfaceDefService;
import com.workec.ectp.service.InterfaceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController()
public class InterfaceController {

    @Autowired
    InterfaceService interfaceService;

    @Autowired
    InterfaceDefService interfaceDefService;

    @Autowired
    HttpService httpService;

    @ApiOperation(value="http接口调试", notes="http接口调试")
    @ApiImplicitParam(name = "httpDebugInformation", value = "调试信息", required = true, dataType = "HttpDebugInformation")
    @PostMapping(value = "/interface/debug")
    public Result<HttpResult> doDebug(@Valid @RequestBody HttpDebugInformation httpDebugInformation , BindingResult bindingResult) throws Exception{
        return httpService.doDebug(httpDebugInformation,bindingResult);
    }


    @ApiOperation(value="查询接口def列表", notes="查询所有接口")
    @GetMapping(value = "/interface/def/getList")
    public Result<InterfaceDef> getinterfaceDefList() throws Exception {
        return interfaceDefService.getList();
    }


    @ApiOperation(value="修改接口def", notes="修改接口def")
    @ApiImplicitParam(name = "interfaceDef", value = "接口def详细实体", required = true, dataType = "InterfaceDef")
    @PostMapping(value = "/interface/def/update")
    public Result<InterfaceDef> updateInterfaceDef(@Valid @RequestBody InterfaceDef interfaceDef, BindingResult bindingResult){
        return interfaceDefService.updateById(interfaceDef,bindingResult);
    }


    @ApiOperation(value="删除接口def", notes="根据接口defid删除接口def")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Int",paramType = "path")
    @PostMapping(value = "/interface/def/delete/{id}")
    public Result<InterfaceDef> deleteDefById(@PathVariable("id") Integer id) throws Exception {
        return interfaceDefService.deleteById(id);
    }


    @ApiOperation(value="按照id查询接口def", notes="按照id查询接口def")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Int",paramType = "path")
    @GetMapping(value = "/interface/def/findById/{id}")
    public Result<InterfaceDef> findInterfaceDefById(@PathVariable("id") Integer id) throws Exception {
        return interfaceDefService.findById(id);
    }


    @ApiOperation(value="修改接口及参数信息", notes="修改接口及参数信息")
    @ApiImplicitParam(name = "itf", value = "接口及参数详细实体", required = true, dataType = "Interface")
    @PostMapping(value = "/interface/save")
    public Result<Interface> updateInterface(@Valid @RequestBody Interface itf, BindingResult bindingResult){
        return interfaceService.updateInterface(itf,bindingResult);
    }


    @ApiOperation(value="按照接口id查询接口及参数详情", notes="按照接口id查询接口及参数详情")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Int",paramType = "path")
    @GetMapping(value = "/interface/info/{id}")
    public Result<Interface> getInterface(@PathVariable("id") Integer id){
        return interfaceService.getInterface(id);
    }


    @ApiOperation(value="批量删除接口参数", notes="按照参数id集合删除参数，id中间用英文逗号隔开")
    @ApiImplicitParam(name = "paramIdList", value = "参数集合ids，id中间用英文逗号隔开", required = true, dataType = "ParamIdList")
    @PostMapping(value = "/interface/param/delete")
    public Result getInterface(@RequestBody ParamIdList paramIdList){
        return interfaceService.deleteParams(paramIdList);
    }

}
