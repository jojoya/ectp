package com.workec.ectp.http.testController;

import com.workec.ectp.entity.HttpRequestInfo;
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
        StringEntity entity= new StringEntity(jsonParam.toString(),"utf-8");
        String body = entity.toString();

//        String body = httpRequestInfo.getBody().toString();
        System.out.println("body" + body);

        HttpResult httpResult = httpAPIService.doPostJson(url,body);

        return /*"doGet:"+strGet + */httpResult.getCode()+"/n"+httpResult.getBody();
    }

}
