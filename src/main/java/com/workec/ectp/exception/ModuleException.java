package com.workec.ectp.exception;


import com.workec.ectp.enums.BaseResultEnum;

/**
 * Created by user on 2017/11/17.
 */
public class ModuleException extends RuntimeException {
    private Integer code;

    public ModuleException(BaseResultEnum baseResultEnum){
        super(baseResultEnum.getMessage());
        this.code = baseResultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
