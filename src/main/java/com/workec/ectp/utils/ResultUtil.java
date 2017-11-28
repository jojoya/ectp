package com.workec.ectp.utils;

import com.workec.ectp.entity.Result;
import com.workec.ectp.enums.BaseResultEnum;

public class ResultUtil {

    public static Result result = new Result();

    public synchronized static Result success(Object data){
        result.setCode(BaseResultEnum.SUCCESS.getCode());
        result.setMsg(BaseResultEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public synchronized static Result success(){
        result.setCode(BaseResultEnum.SUCCESS.getCode());
        result.setMsg(BaseResultEnum.SUCCESS.getMessage());
        result.setData(null);
        return result;
    }

    public synchronized static Result error(Integer code, String msg , Object data){
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public synchronized static Result error(Integer code, String msg){
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}
