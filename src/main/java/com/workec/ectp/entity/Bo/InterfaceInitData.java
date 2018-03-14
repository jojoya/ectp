package com.workec.ectp.entity.Bo;

import lombok.Data;

import java.util.Map;

/**
 * Created by user on 2018/3/12.
 */
@Data
public class InterfaceInitData {

    private Integer callInterfaceId;

    private Integer reqMethod;//1Get 2PostForm 3PostJson
    private String url;
    private Map<String,Object> path;
    private Map<String,Object> header;
    private Object body;

    @Override
    public String toString() {
        return "InterfaceInitData{" +
                "reqMethod=" + reqMethod +
                ", url='" + url + '\'' +
                ", path=" + path +
                ", header=" + header +
                ", body=" + body +
                '}';
    }
}
