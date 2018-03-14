package com.workec.ectp.dao.jdbc.Impl;

import com.workec.ectp.dao.jpa.GlobalParamsDataDao;
import com.workec.ectp.entity.Bo.GlobalParamsDataInfo;
import com.workec.ectp.entity.DoBak.GlobalParamsData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GlobalParamsDataDaoImplTest {

    @Autowired
    GlobalParamsDataDaoImpl iGlobalParamsDataDao;

    @Autowired
    GlobalParamsDataDao globalParamsDataDao;


    @Test
    public void myIGlobalParamsDataDaoImpl(){
        List<GlobalParamsDataInfo> lists =  iGlobalParamsDataDao.findByUserAndEnv(0,0);
        System.out.println(lists);
    }


    @Test
    public void findByUserIdAndDbEnvIdAndGlobalParamId(){
        List<GlobalParamsData> lists =  globalParamsDataDao.findByUserIdAndDbEnvIdAndGlobalParamId(1,1,1);
        System.out.println(lists);
    }
}