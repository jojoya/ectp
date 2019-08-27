package com.workec.ectp.test.host;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class UploadTest {
    private String host;
    private String cookie;
    private String path;
    private int port;
    private SSLContext sslContext;
    private X509TrustManager tm;
    private HttpClient httpClient;

    public UploadTest(String host, String cookie, String path, int port) throws Exception {
        this.host = host;
        this.cookie = cookie;
        this.path = path;
        this.port = port;
        sslContext = SSLContext.getInstance("TLS");
        tm = new MyX509TrustManager();
        sslContext.init(null, new TrustManager[] { tm },
                new java.security.SecureRandom());
        httpClient = HttpClients.custom()
                .setSSLHostnameVerifier(new MyVerifyHostname())
                .setSslcontext(sslContext).build();
    }

    public String getRequest() throws URISyntaxException, IOException {

        URI uri = new URIBuilder().setScheme("https").setHost(host)
                .setPath(path).setPort(port)
                .build();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity httpEntity = response.getEntity();
        httpEntity = new BufferedHttpEntity(httpEntity);
        String resultString = EntityUtils.toString(httpEntity);
        return resultString;
    }



    public static void main(String[] args) {
                try {
            UploadTest test = new UploadTest(
                    "10.0.201.197",
                    "XXXXX",
                    "/register", 443);  //https://account.workec.com
            String resultString = test.getRequest();
            System.out.println(resultString);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*try {
            UploadTest test = new UploadTest(
                    "192.168.2.177",
                    "XXXXX",
                    "XXXXX", 443);
            String resultString = test.uploadFile(
                    "/Users/justyoung/Desktop/upload/test.java", "mytest1");
            System.out.println(resultString);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /*public String uploadFile(String localPath, String filename)
            throws URISyntaxException, ClientProtocolException, IOException {

        URI uri = new URIBuilder().setScheme("https").setHost(host)
                .setPath(path).setParameter("filename", filename).setPort(port)
                .build();
        HttpPost httpPost = new HttpPost(uri);
        FileEntity fileEntity = new FileEntity(new File(localPath));
        fileEntity.setChunked(true);
        httpPost.setEntity(fileEntity);
        httpPost.addHeader("Cookie", cookie);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        httpEntity = new BufferedHttpEntity(httpEntity);
        String resultString = EntityUtils.toString(httpEntity);
        return resultString;
    }*/
}
