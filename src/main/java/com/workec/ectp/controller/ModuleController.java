package com.workec.ectp.controller;

import com.workec.ectp.entity.Module;
import com.workec.ectp.entity.Result;
import com.workec.ectp.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    /* 查询模块列表 */
    @GetMapping(value = "/module/getlist")
    public Result<Module> getModuleList() throws Exception {
        return moduleService.getModuleList();
    }

    /* 添加模块 */
    @PostMapping(value = "/module/add")
    public Result<Module> addModule(@Valid Module module, BindingResult bindingResult){
        return moduleService.addModule(module,bindingResult);
    }

    /* 修改模块 */
    @PostMapping(value = "/module/update")
    public Result<Module> updateModule(@Valid Module module, BindingResult bindingResult){
        return moduleService.updateModule(module,bindingResult);
    }

    /* 删除模块 */
    @PostMapping(value = "/module/delete")
    public Result<Module> deleteModuleById(@RequestParam("id") Integer id) throws Exception {
        return moduleService.deleteModuleById(id);
    }

    /* 按照id查询模块 */
    @GetMapping(value = "/module/findbyid/{id}")
    public Result<Module> findModuleById(@PathVariable("id") Integer id) throws Exception {
        return moduleService.findModuleById(id);
    }

    /* 根据当前节点ID统计直属子节点数量 */
    @GetMapping(value = "/module/getchildrencountbyparentid/{id}")
    public Result<Module> findChildrenCountByParentId(@PathVariable("id") Integer id) throws Exception {
        return moduleService.findChildrenCountByParentId(id);
    }

    /* 根据当前节点ID查询直属子节点信息 */
    @GetMapping(value = "/module/findchildrenbyparentid/{id}")
    public Result<Module> findChildrenByParentId(@PathVariable("id") Integer id) throws Exception {
        return moduleService.findChildrenByParentId(id);
    }

    /* 根据项目ID查询下级模块信息 */
    @GetMapping(value = "/module/findtreebyprojectid/{id}")
    public Result<Module> findModuleTreeByProjectId(@PathVariable("id") Integer id) throws Exception {
        return moduleService.findModuleTreeByProjectId(id);
    }

    /* 按照name查询模块 */
    @GetMapping(value = "/module/findlistbyname/{name}")
    public Result<Module> findModuleListByName(@PathVariable("name") String name) throws Exception {
        return moduleService.findModuleListByName(name);
    }

}
