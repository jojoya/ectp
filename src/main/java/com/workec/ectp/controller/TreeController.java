package com.workec.ectp.controller;

import com.workec.ectp.entity.Leaf;
import com.workec.ectp.entity.Module;
import com.workec.ectp.entity.Tree;
import com.workec.ectp.entity.Result;
import com.workec.ectp.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by user on 2018/1/15.
 */
@CrossOrigin
@RestController()
public class TreeController {

    @Autowired
    TreeService treeService;

    /* 根据【模块ID】查询下级模块及接口信息 */
    @GetMapping(value = "/tree/findByModuleId/{moduleId}")
    public Result<Tree> findByModuleId(@PathVariable("moduleId") Integer moduleId) throws Exception {
        return treeService.findTreeByModuleId(moduleId);
    }

    /* 根据【项目ID】查询下级模块及接口信息 */
    @GetMapping(value = "/tree/findByProjectId/{projectId}")
    public Result<Tree> findByProjectId(@PathVariable("projectId") Integer projectId) throws Exception {
        return treeService.findTreeByProjectId(projectId);
    }

    /* 添加模块、接口 */
    @PostMapping(value = "/leaf/add")
    public Result<Leaf> addLeaf(@Valid @RequestBody Leaf leaf , BindingResult bindingResult) throws Exception {
        return treeService.addLeaf(leaf,bindingResult);
    }

    /* 修改模块、接口名*/
    @PostMapping(value = "/leaf/update")
    public Result updateLeaf(@RequestBody Map<String,String> map) throws Exception {
        return treeService.updateLeaf(map);
    }

    /* 删除模块、接口 */
    @PostMapping(value = "/leaf/delete")
    public Result<Leaf> deleteLeaf(@RequestBody Map<String,Integer> reqMap) throws Exception {
        return treeService.deleteLeaf(reqMap.get("id"),reqMap.get("type"));
    }
}
