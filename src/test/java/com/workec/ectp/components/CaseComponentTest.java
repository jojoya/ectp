package com.workec.ectp.components;

import com.fasterxml.jackson.databind.deser.Deserializers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by user on 2018/3/12.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CaseComponentTest {


    @Autowired
    CaseComponent caseComponent = new CaseComponent();

    @Test
    public void initCase() throws Exception {

        Map map = caseComponent.initCase(46);

        System.out.println("beforeCallsDataList:" + map.get("beforeCallsDataList"));
        System.out.println("testCallsDataList:" + map.get("testCallsDataList"));
        System.out.println("afterCallsDataList:" + map.get("afterCallsDataList"));
    }

    @Test
    public void testExecuteByCaseId() throws Exception {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("开始："+fm.format(System.currentTimeMillis()));
        System.out.println("开始："+System.currentTimeMillis());

        caseComponent.executeOneCase(47,0,0);

        System.out.println("结束："+ System.currentTimeMillis());
        System.out.println("结束："+ fm.format(System.currentTimeMillis()));
    }

}