package com.workec.ectp.utils;

import com.workec.ectp.entity.Bo.HttpResult;

import java.util.Map;

public class HttpResultUtil {

    public static HttpResult setHttpResult(Integer statusCode, String reasonPhrase, Map headers, Object body){
        HttpResult httpResult = new HttpResult();
        httpResult.setStatusCode(statusCode);
        httpResult.setReasonPhrase(reasonPhrase);
        httpResult.setHeaders(headers);
        httpResult.setBody(body);
        return httpResult;
    }

    public static HttpResult setHttpResult(Integer statusCode, String reasonPhrase){
        HttpResult httpResult = new HttpResult();
        httpResult.setStatusCode(statusCode);
        httpResult.setReasonPhrase(reasonPhrase);
        return httpResult;
    }

}
