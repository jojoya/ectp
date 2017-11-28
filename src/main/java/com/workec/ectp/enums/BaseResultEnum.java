package com.workec.ectp.enums;

/**
 * Created by user on 2017/11/17.
 */
public enum BaseResultEnum {
    UNKNOW_ERROR(-1,"未知错误！"),
    PARAMETER_IS_NULL(400,"参数不能为空"),
    PARAMETER_TYPE_MISMACH(401,"参数类型不匹配"),
    PARAMETER_INVALID(404,"参数验证不通过"),
    DATA_NOT_EXIST(402,"操作的数据不存在"),
    DATA_EXIST(403,"数据已存在,不允许重复"),
    REQUEST_METHOD_NOT_SUPPORT(405,"请求方法不支持"),
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
