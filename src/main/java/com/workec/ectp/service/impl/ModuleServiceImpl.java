package com.workec.ectp.service.impl;

import com.workec.ectp.dao.jpa.ModuleDao;
import com.workec.ectp.entity.Do.Module;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.ModuleService;
import com.workec.ectp.utils.ResultUtil;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jdk.nashorn.internal.objects.NativeString.trim;


@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    private Result<Module> checkResult;


    /* 查询模块列表 */
    public Result<Module> getModuleList() throws Exception {
        return ResultUtil.success(moduleDao.findAll());
    }

    /* 按照id查询模块 */
    public Result<Module> findModuleById(Integer id) {
        checkResult = CheckId(id);
        if(checkResult!=null){
            return checkResult;
        }else {
            return ResultUtil.success(moduleDao.findOne(id));
        }
    }

    /* 按照name查询模块 */
    public Result<Module> findModuleListByName(String name) {
        if (trim(name) == null || trim(name).equals("")) {
            return (Result) ResultUtil.error(
                    BaseResultEnum.PARAMETER_IS_NULL.getCode(),
                    "name"+BaseResultEnum.PARAMETER_IS_NULL.getMessage());
        }
        List<Module> list = moduleDao.findByLabelLike(name);
        if (list == null) {
            return (Result) ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        } else {
            return ResultUtil.success(list);
        }
    }


    /* 根据projectID统计直属模块列表 */
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


    public Result<Module> CheckId(Integer id){

        if (id == null || id.equals("")) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_IS_NULL.getCode(),
                    BaseResultEnum.PARAMETER_IS_NULL.getMessage());
        }else if (!moduleDao.exists(id)) {
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
        return null;
    }
}
