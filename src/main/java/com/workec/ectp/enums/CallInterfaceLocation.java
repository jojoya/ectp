package com.workec.ectp.enums;

/**
 * Created by user on 2018/3/12.
 */
public enum CallInterfaceLocation {
    PREPOSITION(1), //前置
    TEST(2),
    POSTPOSITION(3); //后置

    private Integer code;

    CallInterfaceLocation(Integer code){
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
