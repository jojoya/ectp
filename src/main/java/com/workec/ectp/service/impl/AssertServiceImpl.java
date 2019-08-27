package com.workec.ectp.service.impl;


import com.workec.ectp.dao.jpa.CaseAssertDao;
import com.workec.ectp.entity.Do.CaseAssert;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.service.AssertService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssertServiceImpl implements AssertService {

    @Autowired
    private CaseAssertDao caseAssertDao;


    @Override
    public Result<CaseAssert> updateAssert(CaseAssert ast) {

        return ResultUtil.success(caseAssertDao.save(ast));
    }

    @Override
    public Result deleteAssert(Integer id) {
        caseAssertDao.delete(id);
        return ResultUtil.success();
    }
}
