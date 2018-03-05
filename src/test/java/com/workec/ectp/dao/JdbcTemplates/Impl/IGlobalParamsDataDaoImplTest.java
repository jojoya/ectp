package com.workec.ectp.dao.JdbcTemplates.Impl;

import com.workec.ectp.entity.Bo.GlobalParamsDataInfo;
import com.workec.ectp.entity.DO.GlobalParamsData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class IGlobalParamsDataDaoImplTest {

    @Autowired
    IGlobalParamsDataDaoImpl iGlobalParamsDataDao;

    @Test
    public void myIGlobalParamsDataDaoImpl(){
        List<GlobalParamsDataInfo> lists =  iGlobalParamsDataDao.findByUserAndEnv(0,0);
        System.out.println(lists);
    }
}