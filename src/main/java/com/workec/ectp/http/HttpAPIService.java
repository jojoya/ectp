package com.workec.ectp.http;

import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class HttpAPIService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;


    /**
     * 不带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url) throws Exception {
        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(url);

        // 装载配置信息
        httpGet.setConfig(config);

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);

        // 判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            // 返回响应体的内容
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return null;
    }

    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, Object> map) throws Exception {
        URIBuilder uriBuilder = new URIBuilder(url);

        if (map != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        // 调用不带参数的get请求
        return this.doGet(uriBuilder.build().toString());

    }

    /**
     * 带From参数的post请求
     *
     * @param url
     * @param map
     * @return
     * @throws Exception
     */
    public HttpResult doPostFrom(String url, Map<String, Object> map) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (map != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    /**
     * 带Json参数的post请求
     *
     * @param url
     * @param jsonStr
     * @return
     * @throws Exception
     */
    public HttpResult doPostJson(String url, String jsonStr) throws Exception {
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 判断jsonStr是否为空，不为空则封装成Json参数
        if (jsonStr != null) {
            // 构造Json对象
            StringEntity entity = new StringEntity(jsonStr,"utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");

            // 把Json放到post里
            httpPost.setEntity(entity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));
    }

    /**
     * 不带参数post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url) throws Exception {
        return this.doPostFrom(url, null);
    }

    /**
     * 场景post请求及参数调用
     *
     * @param
     * @return
     * @throws Exception
     */
    public HttpResult doPost() throws Exception{
        // String method = "POST";

        //初始化url
        String url = "https://open.workec.com/auth/accesstoken";
        //设置URL
        /*Map<String, Object> mapUrl =new HashMap<>();
        URIBuilder uriBuilder = new URIBuilder(url);
        if (mapUrl != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : mapUrl.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
            url = uriBuilder.build().toString();
        }*/

        System.out.println("url:"+url);
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 设置headers
        /*httpPost.setHeader("Authorization","");
        httpPost.setHeader("CORP-ID","4855250");*/

        //设置body
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appId","200307256498061312");
        jsonObject.put("appSecret","m1lSYLQOcKh08KxmjaN");
        String body = jsonObject.toString();
        System.out.println("body:"+body);

        // 判断body是否为空，不为空则封装成Json参数
        if (body != null) {
            // 构造Json对象
            StringEntity entity = new StringEntity(body,"utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");

            // 把Json放到post里
            httpPost.setEntity(entity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);
        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                response.getEntity(), "UTF-8"));

    }

/*    public static void main(String[] args) throws Exception {
        HttpAPIService service = new HttpAPIService();
        String result = service.doPost().toString();
        System.out.println(result);
    }*/
}
