package com.workec.ectp.test.host;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by CaiXiaoLing on 2018/4/24.
 */
public class ClientExecuteProxyDemo {

    public static void main(String[] args)throws Exception {
//        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpClient httpclient = HostMapDemo.wrapClient();
        try {
            HttpHost target = new HttpHost("account.workec.com", 80, "http");
            HttpHost proxy = new HttpHost("10.0.201.197", 443, "https");

            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
//            HttpGet request = new HttpGet("http://account.workec.com/register");
            HttpGet request = new HttpGet("/register");
            request.setConfig(config);

            System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);

            CloseableHttpResponse response = httpclient.execute(target, request);


            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }
}
