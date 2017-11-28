package com.workec.ectp.service;

import com.workec.ectp.dao.ProjectDao;
import com.workec.ectp.entity.Module;
import com.workec.ectp.entity.Project;
import com.workec.ectp.entity.Result;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;

//    查询项目列表
    public Result<Project> getProjectList() throws Exception {

        return ResultUtil.success(projectDao.findAll());
    }
//
//    public Result<Project> addProject(@Valid Project project, BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return (Result)ResultUtil.error(
//                    BaseResultEnum.PARAMETER_INVALID.getCode(),
//                    bindingResult.getFieldError().getDefaultMessage());
//        }
//        project.setName(project.getName());
//
//        return ResultUtil.success(projectDao.save(project));
//    }
//
//    public Result<Module> updateProject(@Valid Project project, BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return (Result)ResultUtil.error(
//                    BaseResultEnum.PARAMETER_INVALID.getCode(),
//                    bindingResult.getFieldError().getDefaultMessage());
//        }
//        project.setName(project.getName());
//
//        return ResultUtil.success(projectDao.save(project));
//    }

    public Result<Project> deleteById(Integer id) {
            projectDao.delete(id);
            return ResultUtil.success();
    }

//    按照id查询项目
    public Result<Project> findById(Integer id) {
            return ResultUtil.success(projectDao.findOne(id));
    }

}
