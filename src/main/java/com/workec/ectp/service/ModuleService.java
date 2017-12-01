package com.workec.ectp.service;

import com.workec.ectp.dao.ModuleDao;
import com.workec.ectp.dao.ProjectModuleRelationDao;
import com.workec.ectp.entity.*;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.utils.ResultUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jdk.nashorn.internal.objects.NativeString.trim;


@Service
public class ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Autowired
    private ProjectModuleRelationDao projectModuleRelationDao;


    private Result<Module> checkResult;


    /* 查询模块列表 */
    public Result<Module> getModuleList() throws Exception {
        return ResultUtil.success(moduleDao.findAll());
    }


    /* 添加模块 */
    public Result<Module> addModule(@Valid Module module, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (Result) ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        module.setLabel(module.getLabel());
        module.setParentId(module.getParentId());

        Module resultData = moduleDao.save(module);
        Map map = new HashMap();
        map.put("id",resultData.getId());
        map.put("label",resultData.getLabel());
        map.put("parentId",resultData.getParentId());

        return ResultUtil.success(map);
    }

    /* 修改模块 */
    public Result<Module> updateModule(@Valid Module module, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (Result) ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }else if (!moduleDao.exists(module.getId())){
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
        module.setLabel(module.getLabel());
        module.setParentId(module.getParentId());

        return ResultUtil.success(moduleDao.save(module));
    }

    /* 删除模块 */
    public Result<Module> deleteModuleById(Integer id) {
        if(id!=null) {
            checkResult = CheckId(id);
            if(checkResult!=null){
                return checkResult;
            }else if (moduleDao.findChildrenCountByParentId(id) > 0) {
                return ResultUtil.error(121, "存在下级");
            }else {
                moduleDao.delete(id);
                return ResultUtil.success();
            }
        }else {
            return ResultUtil.error(BaseResultEnum.PARAMETER_IS_NULL.getCode(),
                     BaseResultEnum.PARAMETER_IS_NULL.getMessage());
        }

    }

    /* 按照id查询模块 */
    public Result<Module> findModuleById(Integer id) {
        checkResult = CheckId(id);
        if(checkResult!=null){
            return (Result)checkResult;
        }else {
            return ResultUtil.success(moduleDao.findOne(id));
        }
    }

//    按照name查询模块
    public Result<Module> findModuleListByName(String name) {
        if (trim(name) == null || trim(name).equals("")) {
            return (Result) ResultUtil.error(
                    BaseResultEnum.PARAMETER_IS_NULL.getCode(),
                    "name"+BaseResultEnum.PARAMETER_IS_NULL.getMessage());
        }
        List<Module> list = moduleDao.findByName(name);
        if (list == null) {
            return (Result) ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        } else {
            return ResultUtil.success(list);
        }

    }

    /* 根据当前节点ID统计直属子节点数量 */
    public Result<Module> findChildrenCountByParentId(Integer id) throws JSONException {
        checkResult = CheckId(id);
        if(checkResult!=null){
            return checkResult;
        }else {
            return ResultUtil.success(moduleDao.findChildrenCountByParentId(id));
        }
    }

    /* 根据项目ID查询下级模块信息 */
    public Result<Module> findModuleTreeByProjectId(Integer id) throws JSONException {
        List<ModuleTree> moduleList = new ArrayList<>();
        List<ProjectModuleRelation> relationList = projectModuleRelationDao.findByProjectId(id);
        for(ProjectModuleRelation projectModuleRelation : relationList){
            int moduleId = projectModuleRelation.getModuleId();
            moduleList.add(getModuleTree(moduleId));
        }

        return ResultUtil.success(moduleList);
    }

    /* 根据当前节点ID查询直属子节点信息 */
    public Result<Module> findChildrenByParentId(Integer id) throws JSONException {
        checkResult = CheckId(id);
        if(checkResult!=null){  //id不能为空，数据也不能为空
            return checkResult;
        }else {
            return ResultUtil.success(getModuleTree(id));
        }
    }

    public ModuleTree getModuleTree(Integer id){
        ModuleTree moduleTree = new ModuleTree();
        moduleTree.setId(id);
        moduleTree.setLabel(findModuleById(id).getData().getLabel());
        List<Module> list = moduleDao.findChildrenByParentId(id);
        if(list.isEmpty()) {
            moduleTree.setChildren(null);
        }else {
            List<ModuleTree> treeList =getChildrenList(list);
            moduleTree.setChildren(treeList);
        }

        return moduleTree;
    }

    public List<ModuleTree> getChildrenList(List<Module> list){

        List<ModuleTree> list1 = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            list1.add(getModuleTree(id));
        }

        return list1;
    }

    public Result<Module> CheckId(Integer id) {

        if (id == null || id.equals("")) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_IS_NULL.getCode(),
                    BaseResultEnum.PARAMETER_IS_NULL.getMessage(), "[id]");
        }else if (id!=0 && moduleDao.findOne(id) == null) {
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage(),"[id]");
        }
        return null;
    }
}
