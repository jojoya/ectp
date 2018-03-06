package com.workec.ectp.components;

import com.workec.ectp.entity.Bo.HttpResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2018/2/9.
 */
public class HttpAPIComponentTest {

    @Autowired
    HttpAPIComponent httpAPIComponent = new HttpAPIComponent();

    @Test
    public void testDoGet() throws Exception {

        String url = "http://www.baidu.com";

        Map<String,Object>  paths = new HashMap();
        paths.put("account","jojoya");
        paths.put("password","123abc");

        Map<String,Object>  headers = new HashMap();
        headers.put("hd1","this is header1");
        headers.put("hd2","this is header2");

        HttpResult httpResult = httpAPIComponent.doGet(url,paths,headers);
        System.out.println("httpResult:"+httpResult);
    }
}