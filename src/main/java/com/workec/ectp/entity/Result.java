package com.workec.ectp.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@Data public class Result<T>{

    /* 错误码 */
    private int code;

    /* 提示信息 */
    private String msg;

    /* 具体内容 */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public String toString() {
        return "{" +
                "\"code\":" + code +
                ", \"msg\":\"" + msg + '\"' +
                ", \"data\":" + data.toString() +
                '}';
    }
}
