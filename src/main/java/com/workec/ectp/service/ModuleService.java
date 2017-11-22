package com.workec.ectp.service;

import com.workec.ectp.dao.ModuleDao;
import com.workec.ectp.dao.ProjectModuleRelationDao;
import com.workec.ectp.entity.*;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.utils.ResultWithDataListUtil;
import com.workec.ectp.utils.ResultWithDataObjectUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.trim;


@Service
public class ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    private Result<Module> checkResult;

    public ResultWithData<Module> getModuleList() throws Exception {

        return ResultWithDataObjectUtil.success(moduleDao.findAll());
    }

    public ResultWithData<Module> addModule(@Valid Module module, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return (ResultWithData)ResultWithDataObjectUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        module.setName(module.getName());
        module.setParentId(module.getParentId());

        return ResultWithDataObjectUtil.success(moduleDao.save(module));
    }

    public ResultWithData<Module> updateModule(@Valid Module module, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return (ResultWithData)ResultWithDataObjectUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        module.setName(module.getName());
        module.setParentId(module.getParentId());

        return ResultWithDataObjectUtil.success(moduleDao.save(module));
    }

    public Result<Module> deleteModuleById(Integer id) {
        checkResult = CheckId(id);
        if(checkResult!=null){
            return checkResult;
        }else if (moduleDao.findChildrenCountByParentId(id) > 0) {
            return ResultWithDataObjectUtil.error(121, "存在下级");
        }else {
            moduleDao.delete(id);
            return ResultWithDataObjectUtil.success();
        }
    }

    public ResultWithData<Module> findModuleById(Integer id) {
        checkResult = CheckId(id);
        if(checkResult!=null){
            return (ResultWithData)checkResult;
        }else {
            return ResultWithDataObjectUtil.success(moduleDao.findOne(id));
        }
    }

    public ResultWithData<Module> findModuleListByName(String name) {
        if (trim(name) == null || trim(name).equals("")) {
            return (ResultWithData)ResultWithDataListUtil.error(
                    BaseResultEnum.PARAMETER_IS_NULL.getCode(),
                    "name"+BaseResultEnum.PARAMETER_IS_NULL.getMessage());
        }
        List<Module> list = moduleDao.findByName(name);
        if (list == null) {
            return (ResultWithData)ResultWithDataListUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        } else {
            return ResultWithDataListUtil.success((ArrayList)list);
        }

    }

    public ResultWithData<Module> findChildrenCountByParentId(Integer id) throws JSONException {
        checkResult = CheckId(id);
        if(checkResult!=null){
            return (ResultWithData)checkResult;
        }else {
            return ResultWithDataObjectUtil.success(moduleDao.findChildrenCountByParentId(id));
        }
    }

//xxxxxxxxxxxxxxxxxxxxxx
    public ResultWithData<Module> findModuleTreeByProjectId(Integer id) {
        ProjectModuleRelationDao projectModuleRelationDao = new ProjectModuleRelationDao;
        projectModuleRelationDao.findModuleIdByProjectId(id);
            return null;
    }


    public ResultWithData<Module> findChildrenByParentId(Integer id) throws JSONException {
        checkResult = CheckId(id);
        if(checkResult!=null){  //id不能为空，数据也不能为空
            return (ResultWithData)checkResult;
        }else {
            return ResultWithDataObjectUtil.success(getModuleTree(id));
        }
    }

    public ModuleTree getModuleTree(Integer id){
        ModuleTree moduleTree = new ModuleTree();
        moduleTree.setId(id);
        moduleTree.setName(findModuleById(id).getData().getName());
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

        List<ModuleTree> list1 = new ArrayList<ModuleTree>();

        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            list1.add(getModuleTree(id));
        }

        return list1;
    }

    public ResultWithData<Module> CheckId(Integer id) {

        if (id == null || id.equals("")) {
            return ResultWithDataObjectUtil.error(
                    BaseResultEnum.PARAMETER_IS_NULL.getCode(),
                    BaseResultEnum.PARAMETER_IS_NULL.getMessage(), "[id]");
        }else if (id!=0 && moduleDao.findOne(id) == null) {
            return ResultWithDataObjectUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage(),"[id]");
        }
        return null;
    }
}
