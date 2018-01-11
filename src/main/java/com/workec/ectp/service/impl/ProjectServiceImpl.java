package com.workec.ectp.service.impl;

import com.workec.ectp.dao.ProjectDao;
import com.workec.ectp.entity.Project;
import com.workec.ectp.entity.Result;
import com.workec.ectp.service.ProjectService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;

//    查询项目列表
    public Result<Project> getProjectList() throws Exception {

        return ResultUtil.success(projectDao.findAll());
    }

//    按照id查询项目
    public Result<Project> findById(Integer id) {
            return ResultUtil.success(projectDao.findOne(id));
    }

//    删除
    public Result<Project> deleteById(Integer id) {
        projectDao.delete(id);
        return ResultUtil.success();
    }
}
