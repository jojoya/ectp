package com.workec.ectp.service;

import com.workec.ectp.entity.Module;
import com.workec.ectp.entity.Result;
import org.json.JSONException;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;

/**
 * Created by user on 2018/1/10.
 */
public interface ModuleService {

    /* 查询列表 */
    Result<Module> getModuleList() throws Exception;

    /* 添加 */
    Result<Module> addModule(@Valid Module module, BindingResult bindingResult);

    /* 修改 */
    Result<Module> updateModule(@Valid Module module, BindingResult bindingResult);

    /* 删除 */
    Result<Module> deleteModuleById(Integer id);

    /* 按照id查询 */
    Result<Module> findModuleById(Integer id);

    /*按照name查询*/
    Result<Module> findModuleListByName(String name);

    /* 根据projectID获取直属子节点列表 */
    Result findByProjectId(Integer projectId) throws JSONException;

    /* 根据projectID查询下级模块树 */
    Result<Module> findTreeByProjectId(Integer id) throws JSONException;

    /* 根据ModuleId查询直属子节点信息 */
    Result<Module> findTreeByModuleId(Integer moduleId) throws JSONException;


}
