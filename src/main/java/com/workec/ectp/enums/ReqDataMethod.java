package com.workec.ectp.enums;

/**
 * Created by user on 2018/3/12.
 */
public enum ReqDataMethod {
    POST_JSON(3),
    POST_FORM(2),
    REQUEST_GET(1);

    private Integer code;

    ReqDataMethod(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
