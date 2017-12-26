package com.workec.ectp.http;

import com.workec.ectp.EctpApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by user on 2017/12/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EctpApplication.class)
@WebAppConfiguration
public class HttpAPIServiceTest {
    @Test
    public void doPost() throws Exception {

        String expectedResult="hello world!";
        HttpAPIService httpAPIService = new HttpAPIService();
        String result = httpAPIService.doPost().toString();

        Assert.assertTrue("数据一致", expectedResult.equals(result));
        Assert.assertFalse("数据不一致", !expectedResult.equals(result));

    }

}