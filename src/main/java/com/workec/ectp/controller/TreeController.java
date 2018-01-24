package com.workec.ectp.controller;

import com.workec.ectp.entity.dto.Leaf;
import com.workec.ectp.entity.dto.Tree;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.service.TreeService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value="根据【模块ID】查询下级模块及接口树", notes="根据【模块ID】查询下级模块及接口树")
    @ApiImplicitParam(name = "moduleId", value = "模块ID", required = true, dataType = "Int",paramType = "path")
    @GetMapping(value = "/tree/findByModuleId/{moduleId}")
    public Result<Tree> findByModuleId(@PathVariable("moduleId") Integer moduleId) throws Exception {
        return treeService.findTreeByModuleId(moduleId);
    }

    @ApiOperation(value="根据【项目ID】查询下级模块及接口树", notes="根据【项目ID】查询下级模块及接口树")
    @ApiImplicitParam(name = "projectId", value = "项目ID", required = true, dataType = "Int",paramType = "path")
    @GetMapping(value = "/tree/findByProjectId/{projectId}")
    public Result<Tree> findByProjectId(@PathVariable("projectId") Integer projectId) throws Exception {
        return treeService.findTreeByProjectId(projectId);
    }

    /* 添加模块、接口 */
    @ApiOperation(value="在树中添加模块、接口", notes="在树中添加模块、接口")
    @ApiImplicitParam(name = "leaf", value = "叶子详细实体", required = true, dataType = "Leaf")
    @PostMapping(value = "/leaf/add")
    public Result<Leaf> addLeaf(@Valid @RequestBody Leaf leaf , BindingResult bindingResult) throws Exception {
        return treeService.addLeaf(leaf,bindingResult);
    }

    /* 修改模块、接口名*/
    @ApiOperation(value="在树中修改模块、接口名", notes="在树中修改模块、接口名")
    @ApiImplicitParam(name = "map", value = "要修改的模块、接口信息", required = true, dataType = "Map")
    @PostMapping(value = "/leaf/update")
    public Result updateLeaf(@RequestBody Map<String,String> map) throws Exception {
        return treeService.updateLeaf(map);
    }

    /* 删除模块、接口 */
    @ApiOperation(value="在树中删除模块、接口", notes="在树中删除模块、接口")
    @ApiImplicitParam(name = "map", value = "要删除的模块、接口id信息", required = true, dataType = "Map")
    @PostMapping(value = "/leaf/delete")
    public Result<Leaf> deleteLeaf(@RequestBody Map<String,Integer> map) throws Exception {
        return treeService.deleteLeaf(map.get("id"),map.get("type"));
    }
}
