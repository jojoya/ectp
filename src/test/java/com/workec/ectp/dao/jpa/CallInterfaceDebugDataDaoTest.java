package com.workec.ectp.dao.jpa;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.workec.ectp.entity.Do.CallInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by user on 2018/3/9.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CallInterfaceDebugDataDaoTest {

    @Autowired
    CallInterfaceDao callInterfaceDao;


    @Test
    public void findByInterfaceId() throws Exception {

        int caseId =2;

        //获取前置步骤
        List<CallInterface> listBefore = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId,1);

        //获取接口步骤
        List<CallInterface> listTest = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId,2);

        //获取后置步骤
        List<CallInterface> listAfter = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(caseId,3);

        System.out.println("listBefore:"+listBefore);
        System.out.println("listTest:"+listTest);
        System.out.println("listAfter:"+listAfter);
    }

}