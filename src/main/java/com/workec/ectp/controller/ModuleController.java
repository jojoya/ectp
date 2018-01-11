package com.workec.ectp.controller;

import com.workec.ectp.entity.Module;
import com.workec.ectp.entity.Result;
import com.workec.ectp.service.impl.ModuleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController()
public class ModuleController {

    @Autowired
    ModuleServiceImpl moduleServiceImpl;

    /* 查询模块列表 */
    @GetMapping(value = "/module/getList")
    public Result<Module> getModuleList() throws Exception {
        return moduleServiceImpl.getModuleList();
    }

    /* 添加模块 */
//    @PostMapping(value = "/module/add")
//    public Result<Module> addModule(@Valid Module module, BindingResult bindingResult){
//        return moduleService.addModule(module,bindingResult);
//    }

    /* 添加模块 */
    @PostMapping(value = "/module/add")
    public Result<Module> addModule(@Valid @RequestBody Module module , BindingResult bindingResult){
        return moduleServiceImpl.addModule(module,bindingResult);
    }

    /* 修改模块 */
    @PatchMapping(value = "/module/update")
    public Result<Module> updateModule(@Valid @RequestBody Module module, BindingResult bindingResult){
        return moduleServiceImpl.updateModule(module,bindingResult);
    }

    /* 删除模块 */
    @DeleteMapping(value = "/module/delete/{id}")
    public Result<Module> deleteModuleById(@PathVariable("id") Integer id) throws Exception {
            return moduleServiceImpl.deleteModuleById(id);
}

    /* 按照id查询模块 */
    @GetMapping(value = "/module/findById/{id}")
    public Result<Module> findModuleById(@PathVariable("id") Integer id) throws Exception {
        return moduleServiceImpl.findModuleById(id);
    }

    /* 按照name查询模块 */
    @GetMapping(value = "/module/findListByName/{name}")
    public Result<Module> findModuleListByName(@PathVariable("name") String name) throws Exception {
        return moduleServiceImpl.findModuleListByName(name);
    }

    /* 根据项目ID查询直属模块 */
    @GetMapping(value = "/module/findByProjectId/{projectId}")
    public Result<List> findByProjectId(@PathVariable("projectId") Integer projectId) throws Exception {
        return moduleServiceImpl.findByProjectId(projectId);
    }

    /* 根据模块ID查询下级模块及接口信息 */
    @GetMapping(value = "/module/findTreeByModuleId/{moduleId}")
    public Result<Module> findTreeByModuleId(@PathVariable("moduleId") Integer moduleId) throws Exception {
        return moduleServiceImpl.findTreeByModuleId(moduleId);
    }

    /* 根据项目ID查询下级模块及接口信息 */
    @GetMapping(value = "/module/findTreeByProjectId/{projectId}")
    public Result<Module> findTreeByProjectId(@PathVariable("projectId") Integer projectId) throws Exception {
        return moduleServiceImpl.findTreeByProjectId(projectId);
    }

}
