package com.workec.ectp.service;

import com.workec.ectp.entity.Do.Module;
import com.workec.ectp.entity.Dto.Result;
import org.json.JSONException;

/**
 * Created by user on 2018/1/10.
 */
public interface ModuleService {

    /* 查询列表 */
    Result<Module> getModuleList() throws Exception;

    /* 按照id查询 */
    Result<Module> findModuleById(Integer id);

    /*按照name查询*/
    Result<Module> findModuleListByName(String name);

    /* 根据projectID获取直属子节点列表 */
    Result findByProjectId(Integer projectId) throws JSONException;

}
