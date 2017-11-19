package com.workec.ectp.exception;

/**
 * Created by user on 2017/11/17.
 */
public class ModuleFreedomException extends RuntimeException {
    private Integer code;

    public ModuleFreedomException(int code, String msg){
        super(msg);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
