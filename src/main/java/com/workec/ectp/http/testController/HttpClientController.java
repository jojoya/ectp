package com.workec.ectp.http.testController;

import com.workec.ectp.entity.dto.HttpRequestInfo;
import com.workec.ectp.http.HttpAPIService;
import com.workec.ectp.http.HttpResult;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by user on 2017/12/18.
 */
@RestController
public class HttpClientController {

    @Resource
    private HttpAPIService httpAPIService;

    @RequestMapping("httpclientForm")
    public  String test() throws Exception {
//        String strGet = httpAPIService.doGet("http://www.baidu.com/");
//        System.out.println("doGet:"+strGet);
        HttpResult httpResult = httpAPIService.doPost("http://localhost:8080/dev/module/add/");
        String strPost = httpResult.getBody();

        return /*"doGet:"+strGet + */httpResult.getCode()+"/n"+httpResult.getBody();
    }


    @RequestMapping("httpclientJson")
    public  String test(@RequestBody HttpRequestInfo httpRequestInfo) throws Exception {

        //url校验：请求协议为空（ClientProtocolException）、地址找不到（UnknownHostException）
        String url = httpRequestInfo.getUrl();
        JSONObject jsonParam  = httpRequestInfo.getBody();
        String jsonParam_str = jsonParam.toString();
        System.out.println("jsonParam_str:"+jsonParam_str);

        StringEntity entity= new StringEntity(jsonParam_str,"utf-8");
        String body = entity.toString();

//        String body = httpRequestInfo.getBody().toString();
        System.out.println("url:" + url);
        System.out.println("body:" + body);

        HttpResult httpResult = httpAPIService.doPostJson(url,body);

        System.out.println("responseCode:"+httpResult.getCode()+"/n"+"responseBody:"+httpResult.getBody());

        return /*"doGet:"+strGet + */httpResult.getCode()+"/n"+httpResult.getBody();
    }

    @RequestMapping("/doPOST")
    public  String doPOST() throws Exception {

        HttpResult httpResult = httpAPIService.doPost();

        return /* httpResult.getCode()+"\n"+*/httpResult.getBody();
    }

    @RequestMapping("/doPOST1")
    public  String doPOST1(@RequestBody String str) throws Exception {

        HttpResult httpResult = httpAPIService.doPost1(str);

        return /* httpResult.getCode()+"\n"+*/httpResult.getBody();
    }

}
