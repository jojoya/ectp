package com.workec.ectp.controller;

import com.workec.ectp.entity.Project;
import com.workec.ectp.entity.Result;
import com.workec.ectp.service.impl.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController()
public class ProjectController {

    @Autowired
    ProjectServiceImpl projectServiceImpl;

    /* 查询项目列表 */
    @GetMapping(value = "/project/getList")
    public Result<Project> getProjectList() throws Exception {
        return projectServiceImpl.getProjectList();
    }

    /* 按照id查询项目 */
    @GetMapping(value = "/project/findById/{id}")
    public Result<Project> findProjectById(@PathVariable("id") Integer id) throws Exception {
        return projectServiceImpl.findById(id);
    }

}
