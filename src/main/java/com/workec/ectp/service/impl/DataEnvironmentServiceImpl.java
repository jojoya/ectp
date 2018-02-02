package com.workec.ectp.service.impl;

import com.workec.ectp.dao.DataEnvironmentDao;
import com.workec.ectp.entity.DO.DataEnvironment;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.service.DataEnvironmentService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DataEnvironmentServiceImpl implements DataEnvironmentService {

    @Autowired
    private DataEnvironmentDao dataEnvironmentDao;

    @Override
    public Result<DataEnvironment> getList() {

        List<DataEnvironment> list = dataEnvironmentDao.findAll();

        System.out.println("list:"+list.size());
        if(list.size()>0){
            return ResultUtil.success(list);
        }else {
            return ResultUtil.success();
        }
    }

}
