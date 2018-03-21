package com.workec.ectp.service.impl;

import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.entity.Vo.GlobalCache;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by user on 2018/3/20.
 */
@Service
public class TestService {

    public Result testEnv(Map map){
        Integer envId = Integer.valueOf(map.get("envId").toString());
        String domain = String.valueOf(map.get("domain"));
        Map<Integer,Object> envMap = GlobalCache.getInstance().getAppEnvMap();
        Map<String,String> mapDomain = (Map<String,String>)envMap.get(envId);
        String ip = mapDomain.get(domain);
        return ResultUtil.success(ip);
    }


    public Result testGlobal(Map map){
        Integer userId = Integer.valueOf(map.get("userId").toString());
        Integer envId = Integer.valueOf(map.get("envId").toString());
        String key = String.valueOf(map.get("paramName"));
        Map<Integer,Object> userMap = GlobalCache.getInstance().getUserGlobalParamMap();
        Map<Integer,Object> envMap = (Map<Integer,Object>)userMap.get(userId);
        Map<String,String> keyValue = (Map<String,String>)envMap.get(envId);
        String value = keyValue.get(key);
        return ResultUtil.success(key+"::"+value);
    }
}
