package com.workec.ectp.service;

import com.workec.ectp.entity.Project;
import com.workec.ectp.entity.Result;

/**
 * Created by user on 2018/1/10.
 */
public interface ProjectService {

    /*查询列表*/
    Result<Project> getProjectList() throws Exception;

    /*查询*/
    Result<Project> findById(Integer id);

    /*删除*/
    public Result<Project> deleteById(Integer id);
}
