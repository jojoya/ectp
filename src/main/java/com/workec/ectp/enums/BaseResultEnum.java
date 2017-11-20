package com.workec.ectp.enums;

/**
 * Created by user on 2017/11/17.
 */
public enum BaseResultEnum {
    UNKNOW_ERROR(-1,"未知错误！"),
    PARAMETER_IS_NULL(400,"参数不能为空"),
    PARAMETER_TYPE_MISMACH(401,"参数格式不正确"),
    PARAMETER_INVALID(404,"参数验证不通过"),
    DATA_NOT_EXIST(402,"操作的数据不存在"),

    SUCCESS(100,"成功"),
    ;

    private Integer code;
    private String message;

    BaseResultEnum(Integer code, String message) {
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
