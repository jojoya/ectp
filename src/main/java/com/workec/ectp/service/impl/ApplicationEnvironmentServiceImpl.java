package com.workec.ectp.service.impl;

import com.workec.ectp.dao.ApplicationEnvironmentDao;
import com.workec.ectp.entity.DO.ApplicationEnvironment;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.service.ApplicationEnvironmentService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ApplicationEnvironmentServiceImpl implements ApplicationEnvironmentService {

    @Autowired
    private ApplicationEnvironmentDao applicationEnvironmentDao;

    @Override
    public Result<ApplicationEnvironment> getList() {

        List<ApplicationEnvironment> list = applicationEnvironmentDao.findAll();

        System.out.println("list:"+list.size());
        if(list.size()>0){
            return ResultUtil.success(list);
        }else {
            return ResultUtil.success();
        }
    }

}
