package com.workec.ectp.utils;

import com.workec.ectp.entity.ResultWithData;
import com.workec.ectp.enums.BaseResultEnum;

import java.util.List;

public class ResultWithDataListUtil extends ResultUtil{

    public static ResultWithData result = new ResultWithData();

    public static ResultWithData success(List<Object> list){
        result.setCode(BaseResultEnum.SUCCESS.getCode());
        result.setMsg(BaseResultEnum.SUCCESS.getMessage());
        result.setData(list);
        return result;
    }


    public static ResultWithData error(Integer code, String msg ,List<Object> objectList){
        result.setCode(code);
        result.setMsg(msg);
        result.setData(objectList);
        return result;
    }


}
