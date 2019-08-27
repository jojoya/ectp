package com.workec.ectp.service.impl;

import com.workec.ectp.dao.jpa.MiddleParamDao;
import com.workec.ectp.entity.Do.MiddleParam;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.entity.Vo.GlobalCache;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2018/3/20.
 */
@Service
public class TestService {

    @Autowired
    MiddleParamDao middleParamDao;

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


    public void testRegx1() {
        String paramValue = "\"Expires\": \"Thu, 19 Nov 1981 08:52:00 GMT\",\"Date\": \"Thu, 29 Mar 2018 11:20:45 GMT\",";
        String regexRegular = "\"Thu,(.+?)GMT\"";

        MiddleParam middleParam = middleParamDao.findOne(48);
        String regex = middleParam.getExpression();
        Pattern pattern = Pattern.compile(regex);
        Matcher matchResult = pattern.matcher(paramValue);
        while (matchResult.find()) {
            System.out.println("matchResult.group(0):" + matchResult.group(0));
            System.out.println("matchResult.group(1):" + matchResult.group(1));
        }
    }
}
