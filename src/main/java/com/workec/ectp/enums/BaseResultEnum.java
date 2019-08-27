package com.workec.ectp.enums;

/**
 * Created by user on 2017/11/17.
 */
public enum BaseResultEnum {
    UNKNOW_ERROR(-1,"未知错误！"),

    PARAMETER_IS_NULL(400,"参数不能为空"),
    DATA_MISSING(40001,"数据不完整"),
    DATA_EXIST(40002,"数据已存在,不允许重复"),
    DATA_NOT_EXIST(40003,"数据不存在"),
    PARAMETER_INVALID(40004,"参数验证不通过"),
    PARAMETER_TYPE_MISMACH(40005,"参数类型不匹配"),
    INVALID_DATA_ACCESS(40006,"无效的数据访问，API使用异常"),

    REQUEST_METHOD_NOT_SUPPORT(40901,"请求方法不支持"),
    REQUEST_CONTENT_TYPE_NOT_SUPPORT(40902,"Content-Type格式不支持"),
    SUCCESS(100,"成功"),

    USER_ERROR(10100,"账号密码不正确！"),
    USER_DUPLICATION(10101,"存在重复的用户！"),

    REGX_SYNTAX_ERROR(10200,"正则表达式错误"),
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
