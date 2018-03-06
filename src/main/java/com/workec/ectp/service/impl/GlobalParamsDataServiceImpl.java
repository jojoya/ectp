package com.workec.ectp.service.impl;

import com.workec.ectp.dao.jpa.GlobalParamsDataDao;
import com.workec.ectp.dao.jdbc.Impl.GlobalParamsDataDaoImpl;
import com.workec.ectp.entity.Bo.GlobalParamsDataInfo;
import com.workec.ectp.entity.Do.GlobalParamsData;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.GlobalParamsDataService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;


@Service
public class GlobalParamsDataServiceImpl implements GlobalParamsDataService {

    @Autowired
    private GlobalParamsDataDao globalParamsDataDao;
    @Autowired
    GlobalParamsDataDaoImpl iGlobalParamsDataDao;

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
        int userId = Integer.valueOf(reqMap.get("userId").toString());
        int envId = Integer.valueOf(reqMap.get("envId").toString());

        return ResultUtil.success(iGlobalParamsDataDao.findByUserAndEnv(userId,envId));
    }
}
