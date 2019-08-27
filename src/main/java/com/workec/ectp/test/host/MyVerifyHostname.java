package com.workec.ectp.test.host;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by CaiXiaoLing on 2018/4/24.
 */
public class MyVerifyHostname implements HostnameVerifier {

    @Override
    public boolean verify(String arg0, SSLSession arg1) {
//        if (arg0.equals("192.168.2.177") || arg0.equals("cyber-space2015.imwork.net"))
        if (arg0.equals("10.0.201.197") || arg0.equals("account.workec.com"))
            return true;
        else
            return false;
    }

}