package com.workec.ectp.dao.jdbc.Impl;

import com.workec.ectp.entity.Bo.AppEnvDetailInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by user on 2018/3/6.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppEnvDataDaoImplTest {

    @Autowired
    AppEnvDataDaoImpl appEnvDataDao;

    @Test
    public void findByEnvId() throws Exception {
        List<AppEnvDetailInfo> list = appEnvDataDao.findByEnvId(1);
        System.out.println(list);
    }

}