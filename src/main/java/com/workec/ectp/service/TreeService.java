package com.workec.ectp.service;

import com.workec.ectp.entity.Leaf;
import com.workec.ectp.entity.Result;
import com.workec.ectp.entity.Tree;
import org.json.JSONException;
import org.springframework.validation.BindingResult;

import java.util.Map;

/**
 * Created by user on 2018/1/15.
 */
public interface TreeService {

    /* 根据projectID查询下级模块树 */
    Result<Tree> findTreeByProjectId(Integer id) throws JSONException;

    /* 根据ModuleId查询直属子节点信息 */
    Result<Tree> findTreeByModuleId(Integer moduleId) throws JSONException;

    /* 添加节点：0模块 1接口 2用例 */
    Result<Leaf> addLeaf(Leaf leaf, BindingResult bindingResult) throws Exception;

    /* 添加节点：0模块 1接口 2用例 */
    Result updateLeaf(Map<String,String> map) throws Exception;

    /* 删除节点：0模块 1接口 2用例 */
    Result<Leaf> deleteLeaf(Integer id,Integer type) throws Exception;
}
