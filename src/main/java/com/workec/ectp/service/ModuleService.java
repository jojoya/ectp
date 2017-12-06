package com.workec.ectp.service;

import com.workec.ectp.dao.InterfaceMainDao;
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

    @Autowired
    private InterfaceMainDao interfaceMainDao;

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

    /* 根据projectID统计直属子节点列表 */
    public Result findByProjectId(Integer projectId) throws JSONException {
        List list = new ArrayList();
        List<Module> moduleList =moduleDao.findByProjectId(projectId);
        for (int i = 0; i <moduleList.size() ; i++) {
            Map map = new HashMap();
            map.put("id",moduleList.get(i).getId());
            map.put("label",moduleList.get(i).getLabel());
            list.add(map);
        }

            return ResultUtil.success(list);
    }

    /* 根据项目ID查询下级模块和接口 */
    /*public Result<List> findModulesAndInterfacesTreeByProjectId(Integer id) throws JSONException {
        //找直属模块
        List<ProjectModuleRelation> relationList = projectModuleRelationDao.findByProjectId(id);
        List<Integer> ids = new ArrayList();
        for(ProjectModuleRelation projectModuleRelation : relationList){
            ids.add(projectModuleRelation.getModuleId());
        }
        return ResultUtil.success(getModuleAndInterfaceTree(ids));
    }*/

    public List<ModuleAndInterfaceTree> getModuleAndInterfaceTree(List ids) throws JSONException {
        List<ModuleAndInterfaceTree> list = new ArrayList<>();
        int id;
        for (Object idn:ids) {
            id = Integer.valueOf(idn.toString());
            ModuleAndInterfaceTree item = new ModuleAndInterfaceTree();
            item.setId(id);
            item.setLabel(findModuleById(id).getData().getLabel());
            item.setModuleList(getModuleTreeList(id));
            item.setInterfaceList(getInterfaceList(id));

            list.add(item);
        }
        return list;
    }

    public List getModuleTreeList(Integer id) throws JSONException {
        List<Module> list = moduleDao.findChildrenByParentId(id);
        List<Integer> ids = new ArrayList();
        for(Module module : list){
            ids.add(module.getId());
        }

        return getModuleAndInterfaceTree(ids);
    }


    /* 根据项目ID查询下级模块信息 */
    public Result<Module> findModuleTreeByProjectId(Integer id) throws JSONException {
        List<ModuleTree> moduleList = new ArrayList<>();
        //找直属模块
        List<ProjectModuleRelation> relationList = projectModuleRelationDao.findByProjectId(id);
        for(ProjectModuleRelation projectModuleRelation : relationList){
            int moduleId = projectModuleRelation.getModuleId();
            moduleList.add(getModuleTree(moduleId));
        }

        return ResultUtil.success(moduleList);
    }

    /* 根据当前节点ID查询直属子节点信息 */
    public Result<Module> findChildrenByParentId(Integer moduleId) throws JSONException {
        checkResult = CheckId(moduleId);
        if(checkResult!=null){  //id不能为空，数据也不能为空
            return checkResult;
        }else {
            return ResultUtil.success(getModuleTree(moduleId));
        }
    }

    public ModuleTree getModuleTree(Integer moduleId){
        ModuleTree moduleTree = new ModuleTree();
        moduleTree.setId(moduleId);
        moduleTree.setLabel(findModuleById(moduleId).getData().getLabel());
        List<Module> moduleList = moduleDao.findChildrenByParentId(moduleId);
        moduleTree.setModuleList(moduleList.isEmpty()?null:getChildrenList(moduleList));
        moduleTree.setInterfaceList(getInterfaceList(moduleId).isEmpty()?null:getInterfaceList(moduleId));

        return moduleTree;
    }

    public List getInterfaceList(Integer moduleId){
        List list = new ArrayList();
        List<InterfaceMain> interfaceMainList = interfaceMainDao.findByModuleId(moduleId);
        for (int i=0;i<interfaceMainList.size();i++) {
            Map map = new HashMap();
            map.put("id",interfaceMainList.get(i).getId());
            map.put("label",interfaceMainList.get(i).getValue());
            list.add(map);
        }
        return list;
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
