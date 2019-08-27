package com.workec.ectp.test.host;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.springframework.http.HttpMethod;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by CaiXiaoLing on 2018/4/21.
 */
public class HostMapDemo {

    /**
     * @Description 创建一个不进行正式验证的请求客户端对象 不用导入SSL证书
     * @return HttpClient
     */
    public static CloseableHttpClient wrapClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(ssf).build();
            return httpclient;
        } catch (Exception e) {
            return HttpClients.createDefault();
        }
    }


    public static void test1(String url,String ip){
        CloseableHttpClient httpClient = wrapClient();
        HttpGet httpGet = null;

        String result = "";

        try {
            // 依次是目标请求地址，端口号,协议类型
            HttpHost target = new HttpHost("account.workec.com", 80, "http");
            // 依次是代理地址，代理端口号，协议类型
//            HttpHost proxy = new HttpHost(ip, 80, "http");
            HttpHost proxy = new HttpHost("10.0.201.185", 443, "https");
            RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy)
                    .setSocketTimeout(4000).setConnectTimeout(4000).build();

            System.out.println("getHostAddress:" + requestConfig.getProxy().getHostName());
            System.out.println("getSchemeName:" + requestConfig.getProxy().getSchemeName());
            System.out.println("getPort:" + requestConfig.getProxy().getPort());


            //设置参数
        /*String ps = "";
        for (String pKey : params.keySet()) {
            if (!"".equals(ps)) {
                ps = ps + "&";
            }
            // 处理特殊字符，%替换成%25，空格替换为%20，#替换为%23
            String pValue = params.get(pKey).replace("%", "%25")
                    .replace(" ", "%20").replace("#", "%23");
            ps += pKey + "=" + pValue;
        }
        if (!"".equals(ps)) {
            url = url + "?" + ps;
        }*/



            httpGet = new HttpGet(url);
            httpGet.setConfig(requestConfig);
            CloseableHttpResponse response = httpClient.execute(target,httpGet);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity, "utf-8");
                // 打印响应内容
                System.out.println("StatusCode:" + response.getStatusLine().getStatusCode());
                System.out.println("DisplayCountry:" + response.getLocale().getDisplayCountry());
            }

            Header[] headers = response.getAllHeaders();
            for (Header header:headers) {
                System.out.println(header.getName()+":"+header.getValue());
            }

            System.out.println("response:\n" + result);

        }catch (ClientProtocolException e) {
            result = "";
        } catch (IOException e) {
            result = "";
        } catch (Exception e) {
            result = "";
        } finally {
            try {
                if (httpGet != null) {
                    httpGet.releaseConnection();
                }
                if (httpClient != null) {
                    // 释放资源
                    httpClient.close();
                }
            } catch (IOException e) {
                result = "";
            }
        }

    }


    public static void main(String args[]) throws Exception {

//        test1("https://account.workec.com/register");
        test1("/register","10.0.201.197");
//        test1("/register","10.0.201.185");

/*
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        // 依次是目标请求地址，端口号,协议类型
        HttpHost target = new HttpHost("account.workec.com", 443,"https");
        // 依次是代理地址，代理端口号，协议类型
        HttpHost proxy = new HttpHost("10.0.201.197", 443, "https");
        RequestConfig config = RequestConfig.custom().setProxy(proxy).build();


        // 请求地址
//        HttpPost httpPost = new HttpPost("http://account.workec.com/register");
        HttpGet httpGet = new HttpGet("https://account.workec.com/register");
        httpGet.setConfig(config);
        // 创建参数队列
//        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        // 参数名为pid，值是2
//        formparams.add(new BasicNameValuePair("pid", "2"));

//        UrlEncodedFormEntity entity;
        try {
//            entity = new UrlEncodedFormEntity(formparams, "UTF-8");
//            httpPost.setEntity(entity);
//            CloseableHttpResponse response = closeableHttpClient.execute(target, httpGet);
            CloseableHttpResponse response = closeableHttpClient.execute(target, httpGet);
            // getEntity()
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                System.out.println("response:"
                        + EntityUtils.toString(httpEntity, "UTF-8"));
            }
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

}
