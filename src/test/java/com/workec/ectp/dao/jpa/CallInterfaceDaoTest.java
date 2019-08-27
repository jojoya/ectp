package com.workec.ectp.dao.jpa;

import com.workec.ectp.entity.Do.CallInterface;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CallInterfaceDaoTest {

    @Autowired
    CallInterfaceDao callInterfaceDao;

    @Test
    public void findByCaseIdAndLocationOrderByStepAsc() throws Exception {
        List<CallInterface> list = callInterfaceDao.findByCaseIdAndLocationOrderByStepAsc(1,2);
        System.out.println(list.size());
    }

}