package com.workec.ectp;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by user on 2017/12/18.
 */
public class testDemo02_httpClient {

        public static void main( String args[] ) throws IOException {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://localhost:8080/pro/module/findByProjectId/1");
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                int l;
                byte[] tmp = new byte[2048];
                while ((l = instream.read(tmp)) != -1) {
                }
            }
    }

}
