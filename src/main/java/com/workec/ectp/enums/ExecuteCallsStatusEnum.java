package com.workec.ectp.enums;

/**
 * Created by user on 2017/11/17.
 */
public enum ExecuteCallsStatusEnum {
    ASSERT_FAILED(2,"中间变量提取失败"),
    MIDDLE_PARAM_GET_FAILED(1,"中间变量提取失败"),
    SUCCESS(0,"成功"),
    ;

    private Integer code;
    private String message;

    ExecuteCallsStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
