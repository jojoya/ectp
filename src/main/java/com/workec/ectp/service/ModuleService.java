package com.workec.ectp.service;

import com.workec.ectp.dao.ModuleDao;
import com.workec.ectp.entity.Module;
import com.workec.ectp.entity.ModuleTree;
import com.workec.ectp.entity.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.utils.ResultUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeString.trim;


@Service
public class ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    private Result<Module> checkResult;

    public Result<Module> getModuleList() throws Exception {

        return ResultUtil.success(moduleDao.findAll());
    }

    public Result<Module> addModule(@Valid Module module, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        module.setName(module.getName());
        module.setParentId(module.getParentId());

        return ResultUtil.success(moduleDao.save(module));
    }

    public Result<Module> updateModule(@Valid Module module, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        module.setName(module.getName());
        module.setParentId(module.getParentId());

        return ResultUtil.success(moduleDao.save(module));
    }

    public Result<Module> deleteModuleById(Integer id) {
        checkResult = checkId(id);
        if(checkResult!=null){
            return checkResult;
        }else if (moduleDao.findChildsCountByParantId(id) > 0) {
            return ResultUtil.error(121, "存在下级");
        }else {
            moduleDao.delete(id);
            return ResultUtil.success();
        }
    }

    public Result<Module> findModuleById(Integer id) {
        checkResult = checkId(id);
        if(checkResult!=null){
            return checkResult;
        }else {
            return ResultUtil.success(moduleDao.findOne(id));
        }
    }

    public Result<Module> findModuleListByName(String name) {
        if (trim(name) == null || trim(name).equals("")) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_IS_NULL.getCode(),
                    BaseResultEnum.PARAMETER_IS_NULL.getMessage(), "[name]");
        }
        List<Module> list = moduleDao.findByName(name);
        if (list == null) {
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        } else {
            return ResultUtil.success(list);
        }

    }

    public Result<Module> findChildsCountByParantId(Integer id) throws JSONException {
        checkResult = checkId(id);
        if(checkResult!=null){
            return checkResult;
        }else {
            return ResultUtil.success(moduleDao.findChildsCountByParantId(id));
        }
    }

    public Result<Module> findChildsByParantId(Integer id) throws JSONException {
        checkResult = checkId(id);
        if(checkResult!=null){  //id不能为空，数据也不能为空
            return checkResult;
        }else {
            return ResultUtil.success(getModuleTree(id));
        }
    }

    public ModuleTree getModuleTree(Integer id){
        ModuleTree moduleTree = null;
        moduleTree.setId(id);
        moduleTree.setName(findModuleById(id).getData().getName());
        List<Module> list = moduleDao.findChildsByParantId(id);
        if(list.isEmpty()) {
            moduleTree.setChilds(null);
        }else {
            List<ModuleTree> treeList =getChildList(list);
            moduleTree.setChilds(treeList);
        }

        return moduleTree;
    }

    public List<ModuleTree> getChildList(List<Module> list){

        List<ModuleTree> list1 = null;

        for (int i = 0; i < list.size(); i++) {
            int id = list.get(i).getId();
            list1.add(getModuleTree(id));
        }

        return list1;
    }

    public Result<Module> checkId(Integer id) {

        if (id == null || id.equals("")) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_IS_NULL.getCode(),
                    BaseResultEnum.PARAMETER_IS_NULL.getMessage(), "[id]");
        }else if (id!=0 && moduleDao.findOne(id) == null) {
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
        return null;
    }
}
