package com.workec.ectp.service.impl;

import com.workec.ectp.dao.GlobalParamsDao;
import com.workec.ectp.dao.GlobalParamsDataDao;
import com.workec.ectp.entity.DO.GlobalParams;
import com.workec.ectp.entity.DO.GlobalParamsData;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.GlobalParamsDataService;
import com.workec.ectp.service.GlobalParamsService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


@Service
public class GlobalParamsDataServiceImpl implements GlobalParamsDataService {

    @Autowired
    private GlobalParamsDataDao globalParamsDataDao;

    /*登录*/
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

}
