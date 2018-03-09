package com.workec.ectp.service.impl;

import com.workec.ectp.dao.jpa.CaseDao;
import com.workec.ectp.entity.Do.Case;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.service.CaseService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    private CaseDao caseDao;

    @Override
    public Result<Case> getListByInterfaceId(Integer interfaceId) {
        List<Case> caseList = caseDao.getListByInterfaceId(interfaceId);
        return ResultUtil.success(caseList);
    }


    @Override
    public Result<Case> updateCase(Case cs) {

        return ResultUtil.success(caseDao.save(cs));

    }

    @Override
    public Result<Case> deleteCaseById(Integer id){
        caseDao.delete(id);
        if(!caseDao.exists(id)) {
            return ResultUtil.success();
        }else {
            return ResultUtil.error(1,"删除失败");
        }
    }


}
