package com.workec.ectp.service.impl;

import com.workec.ectp.components.CaseComponent;
import com.workec.ectp.dao.jpa.CaseDao;
import com.workec.ectp.entity.Bo.CallInterfaceAndMiddleValues;
import com.workec.ectp.entity.Bo.CallInterfaceInfo;
import com.workec.ectp.entity.Bo.CaseExecuteResult;
import com.workec.ectp.entity.Bo.ExecuteOneCaseInputParams;
import com.workec.ectp.entity.Do.Case;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.CaseService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;


import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseDao caseDao;

    @Autowired
    CaseComponent caseComponent;




    @Override
    public Result<List<Case>> getListByInterfaceId(Integer interfaceId) {
        List<Case> caseList = caseDao.getListByInterfaceId(interfaceId);
        return ResultUtil.success(caseList);
    }


    @Override
    public Result<Case> updateCase(Case cs) {

        return ResultUtil.success(caseDao.save(cs));

    }

    @Override
    public Result deleteCaseById(Integer id){
        if(caseDao.exists(id)){
            caseComponent.deleteCaseById(id);
            if(!caseDao.exists(id)) {
                return ResultUtil.success();
            }else {
                return ResultUtil.error(1,"删除失败");
            }
        }else{
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
    }


    /**
     * 根据用例id执行用例
     * */
    @Override
    public Result<CaseExecuteResult> executeOneCase(ExecuteOneCaseInputParams params){

        //检验字段值
        Integer caseId = params.getCaseId();
        Integer applicationEnvironmentId = params.getApplicationEnvironmentId();
        Integer executeUserId = params.getExecuteUserId();

        if (caseId==0||applicationEnvironmentId==0||executeUserId==0) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    BaseResultEnum.PARAMETER_INVALID.getMessage());
        }



        if(caseDao.exists(caseId)) {
            return ResultUtil.success(caseComponent.executeOneCase(caseId,applicationEnvironmentId,executeUserId));
        }else {
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
    }

    /**
     * 根接口调用id获取调用数据详情
     * */
    @Override
    public Result<CallInterfaceInfo> getCallInterfaceInfo(Integer callInterfaceId) {
        if(caseComponent.existsCallInterfaceId(callInterfaceId)) {
            return ResultUtil.success(caseComponent.getCallInterfaceInfo(callInterfaceId));
        }else {
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }

    }


    @Override
    public Result<List<CallInterfaceAndMiddleValues>> getCallInterfaceAndMiddleValuesByCaseId(Integer caseId){
        return ResultUtil.success(caseComponent.getCallInterfaceAndMiddleValuesByCaseId(caseId));
    }
}
