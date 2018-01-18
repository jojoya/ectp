package com.workec.ectp.entity;

import lombok.Data;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@Data
public class Result<T>{

    /* 错误码 */
    private int code;

    /* 提示信息 */
    private String msg;

    /* 具体内容 */
    private T data;

    public String toString() {

        Map map = new HashMap();
        map.put("code",code);
        map.put("msg",msg);
        map.put("data",(data==""||data==null)?null:data.toString());

        return new JSONObject(map).toString();
    }
}
