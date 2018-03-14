package com.workec.ectp.enums;

/**
 * Created by user on 2018/3/12.
 */
public enum InterfaceParamLocation {
    HEADER(0),
    PATH(1),
    BODY(2);

    private Integer code;

    InterfaceParamLocation(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
