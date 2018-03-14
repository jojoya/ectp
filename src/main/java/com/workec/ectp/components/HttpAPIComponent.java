package com.workec.ectp.components;

import com.workec.ectp.entity.Bo.HttpResult;
import com.workec.ectp.utils.HttpResultUtil;
import com.workec.ectp.utils.ToolsUtil;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class HttpAPIComponent {

    @Autowired
    private CloseableHttpClient httpClient = HttpClientBuilder.create().build();

    @Autowired
    private RequestConfig config;


    /*
    * 组装返回信息:HTML、Json格式
    * */
    private static HttpResult getResultResponse(CloseableHttpResponse response){

        try {
            //获取响应状态
            Integer statusCode = response.getStatusLine().getStatusCode();
            String reasonPhrase = response.getStatusLine().getReasonPhrase();

            //获取响应headers
            Map headerObj = new HashMap();
            Header headers[] = response.getAllHeaders();
            int i = 0;
            while (i < headers.length) {
                headerObj.put(headers[i].getName(),headers[i].getValue());
                i++;
            }

            // 获取响应body
            String contentMimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
            if (statusCode == 200) {

                if(contentMimeType.equals(ContentType.TEXT_HTML.getMimeType())){         //根据返回数据类型转换：Content-Type:text/html
                    String body  = EntityUtils.toString(response.getEntity(), "UTF-8");
                    HttpResult httpResult = HttpResultUtil.setHttpResult(statusCode,reasonPhrase,headerObj, body);
                    return httpResult;
                }else if(contentMimeType.equals(ContentType.APPLICATION_JSON.getMimeType())){         //根据返回数据类型转换：Content-Type:application/json
                    JSONObject body = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                    Map<String, Object> objMap = ToolsUtil.transferMap(body);
                    HttpResult httpResult = HttpResultUtil.setHttpResult(statusCode,reasonPhrase,headerObj, objMap);
                    return httpResult;
                }else {
                    return null;
                }

            } else if (statusCode == 400) {
                // 预留...
                System.out.println("-- 400 --");
            }

            //返回结果
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /*
    * 组装path：把url与参数拼接起来
    * */
    private static String pathBuilder(String url,Map<String, Object> path){
        String pathResult = null;
        try {
            URI uri = new URI(url);
            URIBuilder uriBuilder = new URIBuilder(uri);
            if (path != null && path.size()>0) {
                // 遍历map,拼接url参数
                for (Map.Entry<String, Object> entry : path.entrySet()) {
                    uriBuilder.setParameter(entry.getKey(), entry.getValue()!=null?entry.getValue().toString():"");
                }
            }
            pathResult = uriBuilder.build().toString();

        }catch (URISyntaxException e){
            e.printStackTrace();
        }

        return pathResult;
    }

    /*
    * 组装header信息
    * */
    private static void headerBuilder(Object object,Map<String, Object> headers){
        if (headers != null && object instanceof HttpGet) {
            HttpGet httpGet = (HttpGet)object;
            // 遍历map,拼接请求headers
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), entry.getValue().toString());
            }
        }else if(headers != null && object instanceof HttpPost){
            HttpPost httpPost = (HttpPost)object;
            // 遍历map,拼接请求headers
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue()!=null?entry.getValue().toString():"");
            }
        }
    }


    /*
    * 组装body：form格式
    * */
    private static void bodyFormBuilder(HttpPost httpPost, Map<String, Object> bodys){
        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (bodys != null && bodys.size()>0) {
            List<NameValuePair> list = new ArrayList();
            for (Map.Entry<String, Object> entry : bodys.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()!=null?entry.getValue().toString():""));
            }

            try {
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");    // 构造form表单对象
                httpPost.setEntity(urlEncodedFormEntity);   // 把表单放到post里
            }catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }
        }
    }


    /*
    * 组装body：Json格式
    * */
    private static void bodyJsonBuilder(HttpPost httpPost, String body){
        // 判断jsonStr是否为空，不为空则封装成Json参数
        if (body != null) {
            // 构造Json对象
            StringEntity entity = new StringEntity(body,"utf-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");

            // 把Json放到post里
            httpPost.setEntity(entity);
        }
    }


    /**
     * 带参数的get请求，如果状态码为200，则返回body，如果不为200，则返回null
     *
     * @param url
     * @param paths
     * @param headers
     * @return
     * @throws Exception
     */
    public HttpResult doGet(String url, Map<String, Object> paths,  Map<String, Object> headers){

        if(url==null){
            return HttpResultUtil.setHttpResult(999,"url不能为空");
        }

        //组装url
        String urlPath = pathBuilder(url,paths);

        // 声明 http get 请求
        HttpGet httpGet = new HttpGet(urlPath);

        // 装载配置信息
        httpGet.setConfig(config);

        //组装header
        headerBuilder(httpGet,headers);

        // 发起请求
        HttpResult result = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            result =  getResultResponse(response);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回结果
        return result;

    }

    /**
     * 带From参数的post请求
     *
     * @param url
     * @param paths
     * @param headers
     * @param bodys
     * @return
     * @throws Exception
     */
    public HttpResult doPostForm(String url, Map<String, Object> paths,  Map<String, Object> headers, Map<String, Object> bodys){

        if(url==null){
            return HttpResultUtil.setHttpResult(999,"url不能为空");
        }

        //组装url
        String urlPath = pathBuilder(url,paths);
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(urlPath);
        // 加入配置信息
        httpPost.setConfig(config);

        //组装header
        headerBuilder(httpPost,headers);

        //组装body：form
        bodyFormBuilder(httpPost, bodys);

        // 发起请求
        HttpResult result = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            result = getResultResponse(response);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回结果
        return result;

    }

    /**
     * 带Json参数的post请求
     *
     * @param url
     * @param paths
     * @param headers
     * @param body
     * @return HttpResult
     * @throws Exception
     */
    public HttpResult doPostJson(String url, Map<String, Object> paths,  Map<String, Object> headers, String body){

        if(url==null){
            return HttpResultUtil.setHttpResult(999,"url不能为空");
        }

        //组装url
        String urlPath = pathBuilder(url,paths);
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(urlPath);
        // 加入配置信息
        httpPost.setConfig(config);

        //组装header
        headerBuilder(httpPost,headers);

        //组装body：Json格式
        bodyJsonBuilder(httpPost,body);

        // 发起请求
        HttpResult result = null;
        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            result = getResultResponse(response);
            response.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
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
        Map<String, Object> mapUrl =new HashMap<>();
        URIBuilder uriBuilder = new URIBuilder(url);
        if (mapUrl != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : mapUrl.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
            url = uriBuilder.build().toString();
        }

        System.out.println("url:"+url);
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 设置headers
        httpPost.setHeader("Authorization","");
        httpPost.setHeader("CORP-ID","4855250");

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
        return null;
//        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
//                response.getEntity(), "UTF-8"));

    }

    public HttpResult doPost1(String str) throws Exception{

        //初始化url
        String url = "https://open.workec.com/user/findUserInfoById";
        //设置URL
        Map<String, Object> mapUrl =new HashMap<>();
        URIBuilder uriBuilder = new URIBuilder(url);
        if (mapUrl != null) {
            // 遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : mapUrl.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
            url = uriBuilder.build().toString();
        }

        System.out.println("url:"+url);
        // 声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        // 加入配置信息
        httpPost.setConfig(config);

        // 设置headers
        JSONObject js = new JSONObject(str);
        String accessToken = js.get("accessToken").toString();
        String responseBody = js.get("responseBody").toString();
        System.out.println("accessToken1:"+accessToken);
        System.out.println("responseBody1:"+responseBody);

        //获取带表达式的存储值
        String db_value = "AAA_${Rsp:1:regex@\"accessToken\":\"(.*?)\",\"expiresIn\"}_XXX";
        String matcher_value = null;

        //识别动态参数的提取方式：正则
        String checkRegular_regex =  "\\$\\{Rsp:(.*?):regex@(.*?)}";
        System.out.println("checkRegular_regex:"+checkRegular_regex);

        Pattern pattern = Pattern.compile(checkRegular_regex);
        Matcher macherRegular = pattern.matcher(db_value);

        while(macherRegular.find()){
            String macher_regular = macherRegular.group(0).trim();
            System.out.println("macher_regular："+macher_regular);

            //提取正则表达式
            int startLocation = macher_regular.indexOf("@")+1;  //从@标记符号后开始截取
            int endLocation = macher_regular.length()-1;    //去掉结尾的}

            System.out.println("startLocation:"+startLocation+",endLocation:"+endLocation);
            String regex = macher_regular.substring(startLocation,endLocation);
            System.out.println("regex:" + regex);

            //正则匹配，提取动态参数的值
            Pattern pattern_get_param = Pattern.compile(regex);
            Matcher matcher_get_param = pattern_get_param.matcher(responseBody);

            while (matcher_get_param.find()){
                String matcher_result = matcher_get_param.group(0).trim();
                System.out.println("matcher_result:" + matcher_result);
                matcher_value = matcher_get_param.group(1).trim();
                System.out.println("matcher_value:" + matcher_value);
                //把参数值中的表达式，替换为真实值
                db_value = db_value.replace(macher_regular,matcher_value);
                System.out.println("db_value:"+db_value);
            }
        }


        httpPost.setHeader("Authorization",matcher_value);
        httpPost.setHeader("CORP-ID","4855250");

        JSONObject jsonObject_header = new JSONObject();
        Header[] requestHeaders= httpPost.getAllHeaders();
        for (int i = 0; i < requestHeaders.length; i++) {
            String key = requestHeaders[i].getName();
            String value = requestHeaders[i].getValue();
            jsonObject_header.put(key,value);
        }
        System.out.println("headers:" + jsonObject_header.toString());

        //设置body
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("account","14422222222");
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
        return null;
//        return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
//                response.getEntity(), "UTF-8"));

    }

/*    public static void main(String[] args) throws Exception {
        HttpAPIService service = new HttpAPIService();
        String result = service.doPost().toString();
        System.out.println(result);
    }*/
}
