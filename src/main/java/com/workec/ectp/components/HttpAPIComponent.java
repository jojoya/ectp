package com.workec.ectp.components;

import com.workec.ectp.entity.Bo.HttpResult;
import com.workec.ectp.utils.HttpResultUtil;
import com.workec.ectp.utils.ToolsUtil;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


@Component
public class HttpAPIComponent {

    @Autowired
//    private CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
//    private CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;



    /**
     *
     * 组装返回信息:HTML、Json格式
     *
     */
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
                Object key =headers[i].getName();
                Object value = headers[i].getValue();
                if(!headerObj.containsKey(key)){
                    headerObj.put(key, value);
                }else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(headerObj.get(key));
                    sb.append("; ");
                    sb.append(value);
                    headerObj.put(key, sb);
                }
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
//                    HttpResult httpResult = HttpResultUtil.setHttpResult(statusCode,reasonPhrase,headerObj, body);
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




    /**
     *
     * 组装path：把url与参数拼接起来
     *
     */
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





    /**
     *
     *组装header信息
     *
     */
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



    /**
     *
     * 组装body：form格式
     *
     * */
    private static void bodyFormBuilder(HttpRequestBase httpRequest, Map<String, Object> bodys){
//    private static void bodyFormBuilder(HttpPost httpPost, Map<String, Object> bodys){
        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        /*if (bodys != null && bodys.size()>0) {
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
        }*/

        if (bodys != null && bodys.size()>0) {
            StringBuilder sb = new StringBuilder();

            for (Map.Entry<String, Object> entry : bodys.entrySet()) {
                sb.append("&");
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue()!=null?entry.getValue().toString():"");
            }

            StringEntity entity = new StringEntity(sb.toString().substring(1),"UTF-8");        // 构造form表单对象
            System.out.println("+++++++++++++++++++formParams:"+sb.toString().substring(1));


//            entity.setContentEncoding("UTF-8");
//            entity.setContentType("application/x-www-form-urlencoded");


//            httpPost.setEntity(entity);   // 把表单放到post里
            if(httpRequest instanceof HttpPost) {
                HttpPost httpPost = (HttpPost)httpRequest;
                httpPost.setEntity(entity);   // 把表单放到post里
            }
        }
    }


    /**
     *
     *组装body：Json格式
     *
     * */
    private static void bodyJsonBuilder(HttpRequestBase httpRequest, String body){
//    private static void bodyJsonBuilder(HttpPost httpPost, String body){
        // 判断jsonStr是否为空，不为空则封装成Json参数
        if (body != null) {
            // 构造Json对象
            StringEntity entity = new StringEntity(body,"UTF-8");
//            entity.setContentEncoding("UTF-8");
//            entity.setContentType("application/json");

            // 把Json放到post里
//            httpPost.setEntity(entity);
            if(httpRequest instanceof HttpPost) {
                HttpPost httpPost = (HttpPost)httpRequest;
                httpPost.setEntity(entity);   // 把表单放到post里
            }
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
//        HttpGet httpGet = new HttpGet(urlPath);

        // 装载配置信息
//        httpGet.setConfig(config);

        //组装header
//        headerBuilder(httpGet,headers);

        // 发起请求
     /* HttpResult result = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = httpClient.execute(httpGet);
            result =  getResultResponse(response);
            response.close();
            httpClient.close();
        }catch (IOException e) {
            e.printStackTrace();
        }*/

        HttpRequestBase httpRequest = new HttpGet(urlPath);
        httpRequest.setConfig(config);
        headerBuilder(httpRequest,headers);
        //返回结果
        return getRequestResult(httpRequest);

    }

    private  HttpResult getRequestResult(HttpRequestBase httpRequest){
        HttpResult result = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = httpClient.execute(httpRequest);
            result =  getResultResponse(response);
            response.close();
            httpClient.close();
        }catch (IOException e) {
            e.printStackTrace();
        }

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
 /*       HttpPost httpPost = new HttpPost(urlPath);

        // 加入配置信息
        httpPost.setConfig(config);

        //组装header
        headerBuilder(httpPost,headers);

        //组装body：form
        bodyFormBuilder(httpPost, bodys);

        if(!httpPost.containsHeader("Content-Type")) {
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
        }

        // 发起请求
        HttpResult result = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = httpClient.execute(httpPost);
            result = getResultResponse(response);
            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        HttpRequestBase httpRequest = new HttpPost(urlPath);
        httpRequest.setConfig(config);
        headerBuilder(httpRequest,headers);
        bodyFormBuilder(httpRequest, bodys);
        if(!httpRequest.containsHeader("Content-Type")) {
            httpRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
        }

        //返回结果
        return getRequestResult(httpRequest);

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
    /*    HttpPost httpPost = new HttpPost(urlPath);
        // 加入配置信息
        httpPost.setConfig(config);

        //组装header
        headerBuilder(httpPost,headers);

        //组装body：Json格式
        bodyJsonBuilder(httpPost,body);
        if(!httpPost.containsHeader("Content-Type")) {
            httpPost.addHeader("Content-Type", "application/json");
        }

        // 发起请求
        HttpResult result = null;
        try {
            httpClient = HttpClientBuilder.create().build();
            CloseableHttpResponse response = httpClient.execute(httpPost);
            result = getResultResponse(response);
            response.close();
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        HttpRequestBase httpRequest = new HttpPost(urlPath);
        httpRequest.setConfig(config);
        headerBuilder(httpRequest,headers);
        bodyJsonBuilder(httpRequest, body);
        if(!httpRequest.containsHeader("Content-Type")) {
            httpRequest.addHeader("Content-Type", "application/json");
        }

        //返回结果
        return getRequestResult(httpRequest);
    }


}
