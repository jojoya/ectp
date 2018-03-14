package com.workec.ectp.service.impl;

import com.workec.ectp.dao.jpa.InterfaceDefDao;
import com.workec.ectp.dao.jpa.ModuleDao;
import com.workec.ectp.dao.jpa.ProjectModuleRelationDao;
import com.workec.ectp.entity.Do.InterfaceDef;
import com.workec.ectp.entity.Do.Module;
import com.workec.ectp.entity.Do.ProjectModuleRelation;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.entity.Bo.Leaf;
import com.workec.ectp.entity.Bo.Tree;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.TreeService;
import com.workec.ectp.utils.ResultUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by user on 2018/1/15.
 */
@Service
public class TreeServiceImpl implements TreeService{


    @Autowired
    private ProjectModuleRelationDao projectModuleRelationDao;

    @Autowired
    private InterfaceDefDao interfaceDefDao;

    @Autowired
    private ModuleDao moduleDao;


    /* 根据项目ID查询下级模块信息 */
    public Result<Tree> findTreeByProjectId(Integer id) throws JSONException {
        List<Tree> moduleList = new ArrayList<>();
        //找直属模块
        List<ProjectModuleRelation> relationList = projectModuleRelationDao.findByProjectId(id);
        for(ProjectModuleRelation projectModuleRelation : relationList){
            int moduleId = projectModuleRelation.getModuleId();
            moduleList.add(getModuleTree(moduleId));
        }
        return ResultUtil.success(moduleList);
    }

    /* 根据ModuleID查询子节点树 */
    public Result<Tree> findTreeByModuleId(Integer moduleId) throws JSONException {
        List<Tree> moduleList = new ArrayList<>();
//        if(null!=moduleServiceimpl.CheckId(moduleId)) {  //service调用service有问题
        if(null!=moduleDao.findOne(moduleId)) {  //id不能为空，数据也不能为空
            moduleList.add(getModuleTree(moduleId));
        }
        return ResultUtil.success(moduleList);
    }

    public Tree getModuleTree(Integer moduleId){

        Tree tree = new Tree();
        tree.setType(0); //标记为模块 type=0
        tree.setId(moduleId);
        if(moduleId!=null&&moduleId!=0) {
            tree.setLabel(moduleDao.findOne(moduleId).getLabel());
        }

        List<Tree> childrenList = new ArrayList<>();
        //添加module
        List<Module> moduleList = moduleDao.findByParentId(moduleId);
        System.out.println("moduleList.size:"+ moduleList.size());
        for (Module module : moduleList) {
            Tree children = getModuleTree(module.getId());  //递归
            childrenList.add(children);
        }

        //添加interface
        List<InterfaceDef> interfaceList = interfaceDefDao.findByModuleId(moduleId);
        for (InterfaceDef interfaceDef:interfaceList) {
            Tree children = new Tree();
            children.setId(interfaceDef.getId());
            children.setLabel(interfaceDef.getLabel());
            children.setType(1); //标记为接口 type=1
            childrenList.add(children);
        }
        tree.setChildren(childrenList);

        return tree;
    }

    @Transactional
    public Result<Leaf> addLeaf(Leaf leaf, BindingResult bindingResult) throws Exception {

        Leaf result_leaf = new Leaf();
        //检验字段值
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        if(0==leaf.getType()){  //0 添加模块

            Module module = new Module();
            module.setLabel(leaf.getName());
            module.setId(leaf.getId());
            module.setParentId(leaf.getParentId());

            Module result = moduleDao.save(module);

            result_leaf.setId(result.getId());
            result_leaf.setParentId(result.getParentId());
            result_leaf.setType(leaf.getType());
            result_leaf.setName(result.getLabel());

            return ResultUtil.success(result_leaf);

        }else if(1==leaf.getType()&&0!=leaf.getParentId()){    //1 添加接口

            InterfaceDef interfaceDef = new InterfaceDef();
            interfaceDef.setLabel(leaf.getName());
            interfaceDef.setId(leaf.getId());
            interfaceDef.setModuleId(leaf.getParentId());

            InterfaceDef result = interfaceDefDao.save(interfaceDef);
            result_leaf.setId(result.getId());
            result_leaf.setParentId(result.getModuleId());
            result_leaf.setType(leaf.getType());
            result_leaf.setName(result.getLabel());

            return ResultUtil.success(result_leaf);

        }else {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    BaseResultEnum.PARAMETER_INVALID.getMessage());
        }
    }

    @Transactional
    public Result updateLeaf(Map<String,String> map) throws Exception {

        int type = Integer.valueOf(map.get("type"));
        int id = Integer.valueOf(map.get("id"));
        String name = map.get("name");

        //检验字段值
        if (id==0||name==null||name.equals("")) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    BaseResultEnum.PARAMETER_INVALID.getMessage());
        }

        if(0==type){  //0 修改模块
            int result = moduleDao.updateName(id,name);
            return ResultUtil.success(result);

        }else if(1==type){    //1 修改接口
            int result = interfaceDefDao.updateName(id,name);
            return ResultUtil.success(result);

        }else {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    BaseResultEnum.PARAMETER_INVALID.getMessage());
        }
    }

    @Transactional
    public Result<Leaf> deleteLeaf(Integer id,Integer type) throws Exception {

        if (id!=0&&type==0) {  //0 删除模块
            List<Module> moduleList = moduleDao.findByParentId(id);
            List<InterfaceDef> defList = interfaceDefDao.findByModuleId(id);
            if(moduleList.size()==0&&defList.size()==0) { //没有下级，才允许删除
                moduleDao.delete(id);
                return ResultUtil.success();
            }else {
                return ResultUtil.error(
                        BaseResultEnum.PARAMETER_INVALID.getCode(),
                        "存在下级，不允许删除。");
            }
        }else if (id!=0&&type==1) {  //1 删除接口
            interfaceDefDao.delete(id);
            return ResultUtil.success();

        }else{
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    BaseResultEnum.PARAMETER_INVALID.getMessage());
        }
    }
}
