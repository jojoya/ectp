package com.workec.ectp.utils;

import com.workec.ectp.entity.Result;
import com.workec.ectp.enums.BaseResultEnum;

public class ResultUtil {

    public static Result result = new Result();

    public static Result success(Object object){

        result.setCode(BaseResultEnum.SUCCESS.getCode());
        result.setMsg(BaseResultEnum.SUCCESS.getMessage());
        result.setData(object);

        return result;
    }

    public static Result success(){
        return success("[]");
    }

    public static Result error(Integer code, String msg){
        result.setCode(code);
        result.setMsg(msg);
        result.setData("[]");
        return result;
    }
    public static Result error(Integer code, String msg ,Object object){
        result.setCode(code);
        result.setMsg(msg);
        if(object==null) {
            result.setData("[]");
        }else {
            result.setData(object);
        }
        return result;

    }
}
