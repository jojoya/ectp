package com.workec.ectp.service.impl;

import com.workec.ectp.components.CaseComponent;
import com.workec.ectp.dao.jpa.CaseDao;
import com.workec.ectp.dao.jpa.MiddleParamDao;
import com.workec.ectp.entity.Bo.CallInterfaceInfo;
import com.workec.ectp.entity.Bo.CaseExecuteResult;
import com.workec.ectp.entity.Do.Case;
import com.workec.ectp.entity.Do.MiddleParam;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.CaseService;
import com.workec.ectp.service.MiddleParamService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiddleParamServiceImpl implements MiddleParamService {

    @Autowired
    private MiddleParamDao middleParamDao;

    @Override
    public Result<MiddleParam> updateMiddleParam(MiddleParam mp) {

        return ResultUtil.success(middleParamDao.save(mp));

    }

    @Override
    public Result<MiddleParam> deleteMiddleParam(Integer id) {

        middleParamDao.delete(id);
        return ResultUtil.success();

    }

}
