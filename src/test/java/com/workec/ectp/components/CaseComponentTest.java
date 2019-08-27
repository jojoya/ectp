package com.workec.ectp.components;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;

/**
 * Created by user on 2018/3/12.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CaseComponentTest {


    @Autowired
    CaseComponent caseComponent = new CaseComponent();


    @Test
    public void testExecuteByCaseId() throws Exception {

    }

}