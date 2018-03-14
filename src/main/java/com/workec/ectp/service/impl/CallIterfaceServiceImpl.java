package com.workec.ectp.service.impl;

import com.workec.ectp.components.CaseComponent;
import com.workec.ectp.dao.jpa.CallInterfaceDao;
import com.workec.ectp.entity.Bo.CallInterfaceDataSave;
import com.workec.ectp.entity.Do.CallInterface;
import com.workec.ectp.entity.Do.CallInterfaceData;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.CallInterfaceService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallIterfaceServiceImpl implements CallInterfaceService {

    @Autowired
    private CallInterfaceDao callInterfaceDao;

    @Autowired
    private CaseComponent caseComponent;

    @Override
    public Result<List<CallInterface>> getListByCaseId(Integer id){
        return ResultUtil.success(callInterfaceDao.getListByCaseId(id));
    }


    @Override
    public Result<CallInterfaceData> updateCallInterface(CallInterfaceDataSave cifds) {

        return ResultUtil.success(caseComponent.saveStep(cifds));

    }

    @Override
    public Result deleteCallInterfaceById(Integer id) {

        if(callInterfaceDao.exists(id)){
            caseComponent.deleteStep(id);
        }else {
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }



        if(!callInterfaceDao.exists(id)){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(1, "删除失败");
        }
    }

}
