package com.workec.ectp.controller;

import com.workec.ectp.entity.InterfaceMain;
import com.workec.ectp.entity.Result;
import com.workec.ectp.service.InterfaceMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@CrossOrigin
@RestController()
public class InterfaceMainController {

    @Autowired
    InterfaceMainService interfaceMainService;

    /* 查询接口main列表 */
    @GetMapping(value = "/interface/main/getList")
    public Result<InterfaceMain> getDomainList() throws Exception {
        return interfaceMainService.getList();
    }

    /* 添加接口main */
    @RequestMapping(value = "/interface/main/add",method = POST)
    public Result<InterfaceMain> addInterfaceMain(@Valid @RequestBody InterfaceMain interfaceMain, BindingResult bindingResult) throws Exception{
        return interfaceMainService.addInterfaceMain(interfaceMain,bindingResult);
    }

    /* 修改接口main */
    @PatchMapping(value = "/interface/main/update")
    public Result<InterfaceMain> updateInterfaceMain(@Valid @RequestBody InterfaceMain interfaceMain, BindingResult bindingResult){
        return interfaceMainService.updateById(interfaceMain,bindingResult);
    }

    /* 删除接口main */
    @DeleteMapping(value = "/interface/main/delete/{id}")
    public Result<InterfaceMain> deleteDomainById(@PathVariable("id") Integer id) throws Exception {
        return interfaceMainService.deleteById(id);
    }

    /* 按照id查询接口main */
    @GetMapping(value = "/interface/main/findById/{id}")
    public Result<InterfaceMain> findInterfaceMainById(@PathVariable("id") Integer id) throws Exception {
        return interfaceMainService.findById(id);
    }

}
