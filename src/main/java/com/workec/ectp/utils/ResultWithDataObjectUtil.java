package com.workec.ectp.utils;

import com.workec.ectp.entity.ResultWithData;
import com.workec.ectp.enums.BaseResultEnum;

public class ResultWithDataObjectUtil extends ResultUtil{

    public static ResultWithData result = new ResultWithData();

    public static ResultWithData success(Object object){
        result.setCode(BaseResultEnum.SUCCESS.getCode());
        result.setMsg(BaseResultEnum.SUCCESS.getMessage());
        result.setData(object);
        return result;
    }

    public static ResultWithData error(Integer code, String msg ,Object object){
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
}
