package com.workec.ectp.http.testController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.workec.ectp.EctpApplication;
import com.workec.ectp.http.HttpAPIService;
import com.workec.ectp.http.HttpResult;
import javafx.application.Application;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * Created by user on 2017/12/26.
 */

//SpringApplication.run(EctpApplication.class, args);

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EctpApplication.class)
@WebAppConfiguration
public class HttpClientControllerTest {

    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationConnect;

    @Autowired
    HttpAPIService httpAPIService;

    String expectedJson;

    private String accessToken;

    @Before
    public void setUp() throws JsonProcessingException {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();

    }

    @Test
    public void doPOST() throws Exception {

        long startTime = System.currentTimeMillis();
        System.out.println("startTime:" + startTime);

        /**
         * 获取accessToken接口
         * */
        String uri = "/doPOST";

        //ConnectTimeoutException
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        String content = mvcResult.getResponse().getContentAsString();

        System.out.println("content:"+content);

        Assert.assertTrue("错误，正确的返回值为200", status == 200);
        Assert.assertFalse("错误，正确的返回值为200", status != 200);

        String expectedResult = "{\"errCode\":200,\"errMsg\":\"OK\",\"data\":{\"accessToken\"";
        Assert.assertThat(content, containsString(expectedResult));
//        Assert.assertTrue("数据一致", expectedResult.equals(content));
//        Assert.assertFalse("数据不一致", !expectedResult.equals(content));

        String regexStr = "\"accessToken\":\"(.*?)\",\"expiresIn\"";// 不包含特定字符串的表达式

        Pattern pattern = Pattern.compile(regexStr);
        Matcher macher = pattern.matcher(content);

        while(macher.find()){
            System.out.println("macher："+macher.group(0).trim());
            accessToken = macher.group(1).trim();
            System.out.println("accessToken："+ accessToken);
        }


        /**
         * 调用获取userId接口
         * */
        String uri1 = "/doPOST1";

        //ConnectTimeoutException
//        MvcResult mvcResult1 = mvc.perform(MockMvcRequestBuilders.post(uri1).accept(MediaType.APPLICATION_JSON))
//                .andReturn();

        JSONObject js = new JSONObject();
        js.put("accessToken",accessToken);
        js.put("responseBody",content);
        String requestJson = js.toString();

        MvcResult mvcResult1 = mvc.perform(MockMvcRequestBuilders.post(uri1).accept(MediaType.APPLICATION_JSON).content(requestJson)).andReturn();

//        String mvcResult1 = mockMvc.perform( post("/softs").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
//                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        int status1 = mvcResult1.getResponse().getStatus();
        String content1 = mvcResult1.getResponse().getContentAsString();

        System.out.println("content1:"+content1);

        Assert.assertTrue("错误，正确的返回值为200", status1 == 200);
        Assert.assertFalse("错误，正确的返回值为200", status1 != 200);

        String expectedResult1 = "{\"errCode\":200,\"errMsg\":\"OK\",\"data\":{\"f_user_id\":";
        Assert.assertThat(content1, containsString(expectedResult1));

        String regexStr1 = "\"f_user_id\":(.*?),";// 不包含特定字符串的表达式

        Pattern pattern1 = Pattern.compile(regexStr1);
        Matcher macher1 = pattern1.matcher(content1);

        String f_user_id;
        while(macher1.find()){
            System.out.println("macher1："+macher1.group(0).trim());
            f_user_id = macher1.group(1).trim();
            System.out.println("f_user_id："+ f_user_id);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("startTime:" + startTime);
        System.out.println("countTime:" + String.valueOf(endTime-startTime));

//        System.out.println(StringMatchRule(content, regexStr));
    }

    public static boolean StringMatchRule(String souce, String regex) {
        boolean result = false;
        if (regex != null && souce != null) {
            result = Pattern.matches(regex, souce);
        }
        return result;
    }


}