package com.workec.ectp.service.impl;

import com.workec.ectp.dao.GlobalParamsDao;
import com.workec.ectp.dao.GlobalParamsDataDao;
import com.workec.ectp.entity.Bo.GlobalParamsDataInfo;
import com.workec.ectp.entity.DO.GlobalParamsData;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.GlobalParamsDataService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class GlobalParamsDataServiceImpl implements GlobalParamsDataService {

    @Autowired
    private GlobalParamsDataDao globalParamsDataDao;

    @Override
    public Result<GlobalParamsData> save(GlobalParamsData globalParamsData, BindingResult bindingResult) {
        //检验字段值
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        return ResultUtil.success(globalParamsDataDao.save(globalParamsData));
    }


    @Override
    public Result<List<GlobalParamsDataInfo>> getListByUserAndEnv(Map<String, Object> reqMap) {
        List<GlobalParamsDataInfo> lists = new ArrayList<>();
        String userId = reqMap.get("userId").toString();
        String envId = reqMap.get("envId").toString();

        if(userId!=null&&envId!=null){
            int userInfo = Integer.valueOf(userId);
            int envInfo = Integer.valueOf(envId);
            System.out.println("userInfo:"+userInfo+",envInfo:"+envInfo);
            List<Map<String,Object>> mapLists = globalParamsDataDao.getListByUserIdAndEnvId(userInfo,envInfo);
            System.out.println("mapLists:"+mapLists);
            if(mapLists.size()>0){
                for(Map<String,Object> map: mapLists){
                    GlobalParamsDataInfo info = new GlobalParamsDataInfo();
                    //未重命名的方法
                    info.setId(Integer.valueOf(map.get("id").toString()));
/*                    info.setDbEnvId(Integer.valueOf(map.get("db_env_id").toString()));
                    info.setGlobalParamId(Integer.valueOf(map.get("global_param_id").toString()));
                    info.setUserId(Integer.valueOf(map.get("user_id").toString()));*/
                    info.setParamName(map.get("param_name").toString());
                    /*info.setParamValue(map.get("param_value").toString());
                    info.setDbEnvName(map.get("name").toString());*/
                    info.setRemark(map.get("remark").toString());

                    /*//重命名的方法
                    info.setId(Integer.valueOf(map.get("id").toString()));
                    info.setDbEnvId(Integer.valueOf(map.get("dbEnvId").toString()));
                    info.setGlobalParamId(Integer.valueOf(map.get("globalParamId").toString()));
                    info.setUserId(Integer.valueOf(map.get("userId").toString()));
                    info.setParamName(map.get("paramName").toString());
                    info.setParamValue(map.get("paramValue").toString());*/
                    lists.add(info);
                }
            }
        }

        return ResultUtil.success(lists);
    }
}
