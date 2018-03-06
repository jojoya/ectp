package com.workec.ectp.utils;

import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;

public class ResultUtil {

    public static Result success(Object data){
        Result result = new Result();
        result.setCode(BaseResultEnum.SUCCESS.getCode());
        result.setMsg(BaseResultEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static Result success(){
        Result result = new Result();
        result.setCode(BaseResultEnum.SUCCESS.getCode());
        result.setMsg(BaseResultEnum.SUCCESS.getMessage());
        result.setData(null);
        return result;
    }

    public static Result error(Integer code, String msg , Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result error(Integer code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }
}
