package com.workec.ectp.controller;

import com.workec.ectp.entity.Do.Project;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.service.ProjectService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value="查询项目列表", notes="查询所有项目")
    @GetMapping(value = "/project/getList")
    public Result<Project> getProjectList() throws Exception {
        return projectService.getProjectList();
    }

    @ApiOperation(value="按照id查询项目", notes="按照id查询项目")
    @ApiImplicitParam(name = "id", value = "项目ID", required = true, dataType = "Int",paramType = "path")
    @GetMapping(value = "/project/findById/{id}")
    public Result<Project> findProjectById(@PathVariable("id") Integer id) throws Exception {
        return projectService.findById(id);
    }

}
