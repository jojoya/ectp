package com.workec.ectp.service;

import com.workec.ectp.dao.ProjectDao;
import com.workec.ectp.entity.Module;
import com.workec.ectp.entity.Result;
import com.workec.ectp.entity.ResultWithData;
import com.workec.ectp.utils.ResultWithDataObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectService {

    @Autowired
    private ProjectDao projectDao;

    public ResultWithData<Module> getProjectList() throws Exception {

        return ResultWithDataObjectUtil.success(projectDao.findAll());
    }
//
//    public ResultWithData<Module> addProject(@Valid Project project, BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return (ResultWithData)ResultWithDataObjectUtil.error(
//                    BaseResultEnum.PARAMETER_INVALID.getCode(),
//                    bindingResult.getFieldError().getDefaultMessage());
//        }
//        project.setName(project.getName());
//
//        return ResultWithDataObjectUtil.success(projectDao.save(project));
//    }
//
//    public ResultWithData<Module> updateProject(@Valid Project project, BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors()) {
//            return (ResultWithData)ResultWithDataObjectUtil.error(
//                    BaseResultEnum.PARAMETER_INVALID.getCode(),
//                    bindingResult.getFieldError().getDefaultMessage());
//        }
//        project.setName(project.getName());
//
//        return ResultWithDataObjectUtil.success(projectDao.save(project));
//    }

    public Result<Module> deleteProjectById(Integer id) {
            projectDao.delete(id);
            return ResultWithDataObjectUtil.success();
    }

    public ResultWithData<Module> findProjectById(Integer id) {
            return ResultWithDataObjectUtil.success(projectDao.findOne(id));
    }

}
