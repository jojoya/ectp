package com.workec.ectp.entity.Bo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

/**
 * Created by user on 2018/3/12.
 */
@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class InterfaceInitDataBackEnd {

    private Integer callInterfaceId;

    private Integer reqMethod;//1Get 2PostForm 3PostJson
    private String url;
    private Map<String,Object> path;
    private Map<String,Object> header;
    private Object body;

    private Integer location;
    private Integer step;
    private String label;


    @Override
    public String toString() {
        return "InterfaceInitDataBackEnd{" +
                "reqMethod=" + reqMethod +
                ", url='" + url + '\'' +
                ", path=" + path +
                ", header=" + header +
                ", body=" + body +
                '}';
    }
}
