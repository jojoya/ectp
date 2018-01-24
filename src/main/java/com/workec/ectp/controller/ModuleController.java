package com.workec.ectp.controller;

import com.workec.ectp.entity.DO.Module;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.service.ModuleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController()
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    @ApiOperation(value="查询模块列表", notes="查询所有模块")
    @GetMapping(value = "/module/getList")
    public Result<Module> getModuleList() throws Exception {
        return moduleService.getModuleList();
    }

    @ApiOperation(value="按照id查询模块", notes="按照id查询模块")
    @ApiImplicitParam(name = "id", value = "模块ID", required = true, dataType = "Int",paramType = "path")
    @GetMapping(value = "/module/findById/{id}")
    public Result<Module> findModuleById(@PathVariable("id") Integer id) throws Exception {
        return moduleService.findModuleById(id);
    }

    @ApiOperation(value="按照name查询模块", notes="按照name查询模块")
    @ApiImplicitParam(name = "name", value = "模块名称", required = true, dataType = "String",paramType = "path")
    @GetMapping(value = "/module/findListByName/{name}")
    public Result<Module> findModuleListByName(@PathVariable("name") String name) throws Exception {
        return moduleService.findModuleListByName(name);
    }

    @ApiOperation(value="按照项目id查询模块", notes="按照项目id查询模块")
    @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, dataType = "Int",paramType = "path")
    @GetMapping(value = "/module/findByProjectId/{projectId}")
    public Result<List> findByProjectId(@PathVariable("projectId") Integer projectId) throws Exception {
        return moduleService.findByProjectId(projectId);
    }

}
