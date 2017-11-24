package com.workec.ectp.controller;

import com.workec.ectp.entity.Module;
import com.workec.ectp.entity.Result;
import com.workec.ectp.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController()
public class ProjectController {

    @Autowired
    ProjectService projectService;

    /* 查询项目列表 */
    @GetMapping(value = "/project/getList")
    public Result<Module> getProjectList() throws Exception {
        return projectService.getProjectList();
    }

////    /* 添加模块 */
////    @PostMapping(value = "/module/addModule")
////    public Result<Module> addModule(@Valid Module module, BindingResult bindingResult){
////        return moduleService.addModule(module,bindingResult);
////    }
////
////    /* 修改模块 */
////    @PostMapping(value = "/module/updateModule")
////    public Result<Module> updateModule(@Valid Module module, BindingResult bindingResult){
////        return moduleService.updateModule(module,bindingResult);
////    }
//
//    /* 删除模块 */
//    @PostMapping(value = "/module/deleteModule")
//    public Result<Module> deleteModuleById(@RequestParam("id") Integer id) throws Exception {
//        return moduleService.deleteModuleById(id);
//    }

    /* 按照id查询项目 */
    @GetMapping(value = "/project/findById/{id}")
    public Result<Module> findProjectById(@PathVariable("id") Integer id) throws Exception {
        return projectService.findProjectById(id);
    }

}
