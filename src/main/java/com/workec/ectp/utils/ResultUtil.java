package com.workec.ectp.utils;

import com.workec.ectp.entity.Result;
import com.workec.ectp.enums.BaseResultEnum;

public class ResultUtil {

    public static Result result = new Result();

    public static Result success(){
        result.setCode(BaseResultEnum.SUCCESS.getCode());
        result.setMsg(BaseResultEnum.SUCCESS.getMessage());
        return result;
    }

    public static Result error(Integer code, String msg){
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
