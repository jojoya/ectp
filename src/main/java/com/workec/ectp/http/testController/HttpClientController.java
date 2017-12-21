package com.workec.ectp.http.testController;

import com.workec.ectp.http.HttpAPIService;
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

    @RequestMapping("httpclient")
    public  String test() throws Exception {
        String str = httpAPIService.doGet("http://www.baidu.com/");
        System.out.println(str);
        return str;
    }

//    public  static void main(String [] args) throws Exception{
//        TestDemo03_httpClient_Service service = new TestDemo03_httpClient_Service();
//        service.test();
//    }

}
