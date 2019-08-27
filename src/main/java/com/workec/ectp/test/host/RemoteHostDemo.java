package com.workec.ectp.test.host;

import org.apache.http.HttpHost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;

import java.net.*;

/**
 * httpClient 4.3
 */
public class RemoteHostDemo {


    public static  void main(String [] args) throws Exception{
//        HttpClientContext clientContext = HttpClientContext.create();
//        PlainConnectionSocketFactory sf = PlainConnectionSocketFactory.getSocketFactory();
//        Socket socket = sf.createSocket(clientContext);

//        Socket socket = PlainConnectionSocketFactory.getSocketFactory().createSocket(HttpClientContext.create());

//        int timeout = 1000; //ms
//        HttpHost target = new HttpHost("www.workec.com");
        InetSocketAddress remoteAddress = new InetSocketAddress(InetAddress.getByName("www.workec.com"),443);
        //connectSocket源码中，实际没有用到target参数
//        sf.connectSocket(timeout, socket, target, remoteAddress, null, clientContext);


        System.out.println("getAddress:"+remoteAddress.getAddress());
        System.out.println("getAddress.getCanonicalHostName:"+remoteAddress.getAddress().getCanonicalHostName());
        System.out.println("getAddress.getHostAddress:"+remoteAddress.getAddress().getHostAddress());//获取remote Host
        System.out.println("getPort:"+remoteAddress.getPort());//获取remote Port

    }
}
