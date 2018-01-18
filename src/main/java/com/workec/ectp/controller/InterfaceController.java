package com.workec.ectp.controller;

import com.workec.ectp.entity.Interface;
import com.workec.ectp.entity.InterfaceDef;
import com.workec.ectp.entity.InterfaceParam;
import com.workec.ectp.entity.Result;
import com.workec.ectp.service.InterfaceDefService;
import com.workec.ectp.service.InterfaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin
@RestController()
public class InterfaceController {

    @Autowired
    InterfaceService interfaceService;

    @Autowired
    InterfaceDefService interfaceDefService;

    /* 查询接口def列表 */
    @GetMapping(value = "/interface/def/getList")
    public Result<InterfaceDef> getinterfaceDefList() throws Exception {
        return interfaceDefService.getList();
    }

    /* 修改接口def */
    @PatchMapping(value = "/interface/def/update")
    public Result<InterfaceDef> updateInterfaceDef(@Valid @RequestBody InterfaceDef interfaceDef, BindingResult bindingResult){
        return interfaceDefService.updateById(interfaceDef,bindingResult);
    }

    /* 删除接口def */
    @DeleteMapping(value = "/interface/def/delete/{id}")
    public Result<InterfaceDef> deleteDefById(@PathVariable("id") Integer id) throws Exception {
        return interfaceDefService.deleteById(id);
    }

    /* 按照id查询接口def */
    @GetMapping(value = "/interface/def/findById/{id}")
    public Result<InterfaceDef> findInterfaceDefById(@PathVariable("id") Integer id) throws Exception {
        return interfaceDefService.findById(id);
    }

    /* 修改接口 */
    @PostMapping(value = "/interface/save")
    public Result<Interface> updateInterface(@Valid @RequestBody Interface itf, BindingResult bindingResult){
        return interfaceService.updateInterface(itf,bindingResult);
    }

    /* 查询接口及参数详情 */
    @GetMapping(value = "/interface/info/{id}")
    public Result<Interface> getInterface(@PathVariable("id") Integer id){
        return interfaceService.getInterface(id);
    }

    /* 批量删除接口参数 */
    @PostMapping(value = "/interface/param/delete")
    public Result getInterface(@RequestBody Map<String,String> map){
        return interfaceService.deleteParams(map.get("ids"));
    }

    /* 修改param */
//    @PostMapping(value = "/interfaceParam/update")
//    public Result<InterfaceParam> updateInterfaceParam(@Valid @RequestBody InterfaceParam param, BindingResult bindingResult){
//        return interfaceService.updateInterfaceParam(param,bindingResult);
//    }

}
