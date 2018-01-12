package com.workec.ectp.controller;

import com.workec.ectp.entity.InterfaceDef;
import com.workec.ectp.entity.Result;
import com.workec.ectp.service.InterfaceDefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin
@RestController()
public class InterfaceController {

    @Autowired
    InterfaceDefService interfaceDefService;

    /* 查询接口main列表 */
    @GetMapping(value = "/interface/main/getList")
    public Result<InterfaceDef> getDomainList() throws Exception {
        return interfaceDefService.getList();
    }

    /* 添加接口main */
    @RequestMapping(value = "/interface/main/add",method = POST)
    public Result<InterfaceDef> addInterfaceMain(@Valid @RequestBody InterfaceDef interfaceDef, BindingResult bindingResult) throws Exception{
        return interfaceDefService.addInterfaceMain(interfaceDef,bindingResult);
    }

    /* 修改接口main */
    @PatchMapping(value = "/interface/main/update")
    public Result<InterfaceDef> updateInterfaceMain(@Valid @RequestBody InterfaceDef interfaceDef, BindingResult bindingResult){
        return interfaceDefService.updateById(interfaceDef,bindingResult);
    }

    /* 删除接口main */
    @DeleteMapping(value = "/interface/main/delete/{id}")
    public Result<InterfaceDef> deleteDomainById(@PathVariable("id") Integer id) throws Exception {
        return interfaceDefService.deleteById(id);
    }

    /* 按照id查询接口main */
    @GetMapping(value = "/interface/main/findById/{id}")
    public Result<InterfaceDef> findInterfaceMainById(@PathVariable("id") Integer id) throws Exception {
        return interfaceDefService.findById(id);
    }

}
