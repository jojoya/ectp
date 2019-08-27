package com.workec.ectp.service.impl;

import com.workec.ectp.components.DataCacheComponent;
import com.workec.ectp.configuration.Configuration;
import com.workec.ectp.entity.Bo.KeyValuePair;
import com.workec.ectp.entity.Bo.HttpDebugInformation;
import com.workec.ectp.entity.Bo.HttpResult;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.components.HttpAPIComponent;
import com.workec.ectp.service.HttpService;
import com.workec.ectp.utils.ResultUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class HttpServiceImpl implements HttpService {

    @Autowired
    HttpAPIComponent httpAPIComponent;

    @Autowired
    DataCacheComponent dataCacheComponent;


    private String urlMapping(String url,Integer envId){
        String regex = "^(http://|https://)(.*?)/";

        //取出域名
        String domain = null;
        Matcher matchResult = Pattern.compile(regex).matcher(url);
        if(matchResult.find()) {
            domain = matchResult.group(2);
        }

        //替换域名
        String ip = dataCacheComponent.getIpByDomainAndEnvId(envId,domain);
        //域名、ip都不为空就做域名映射
        if(domain!=null&&ip!=null){
            url=url.replaceFirst(domain,ip);
        }
        return url;
    }

    @Override
    public Result<HttpResult> doDebug(HttpDebugInformation httpDebugInformation, BindingResult bindingResult){

        //检验字段值
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        Integer method = httpDebugInformation.getMethod();
        String url = httpDebugInformation.getUrl();
        Integer envId = httpDebugInformation.getApplicationEnvironmentId();
        //url映射处理
        if(Configuration.getEnvChange()==true){
            System.out.println("++++++++++++++++++++++++before:"+url);
            url = urlMapping(url,envId);
            System.out.println("++++++++++++++++++++++++after:"+url);
        }else {
            System.out.println("-----------------------------定义调试：不做url转换--------------------------");
        }

        List<KeyValuePair> pathList = httpDebugInformation.getPaths();
        Map<String, Object> pathMap = keyPairAssemble(pathList) ;

        List<KeyValuePair> headerList = httpDebugInformation.getHeaders();
        Map<String, Object> headerMap = keyPairAssemble(headerList) ;

        HttpResult httpResult = null;

        if(method==1) {
            //get请求
            httpResult = httpAPIComponent.doGet(url, pathMap, headerMap);
            return ResultUtil.success(httpResult);
        }else if(method==2){
            //postForm请求
            try {
                Map<String, Object> bodyMap = new HashMap<>();
                String bodyStr = httpDebugInformation.getBodys().toString();
                JSONArray bodyArray = new JSONArray(bodyStr);
                if(bodyArray.length()>0){
                    for(int i=0;i<bodyArray.length();i++) {
                        JSONObject object = new JSONObject(bodyArray.get(i).toString());
                        bodyMap.put(object.get("paramName").toString(),object.get("value"));
                    }
                }

                httpResult = httpAPIComponent.doPostForm(url, pathMap, headerMap, bodyMap);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return ResultUtil.success(httpResult);
        }else if(method==3){
            //postJson请求
            String bodyStr = null;
            List<Map<String,String>> list = (List)httpDebugInformation.getBodys();
            if(list!=null && list.size()>0) {
                Map<String, String> map = list.get(0);
                bodyStr = map.get("value");
            }

            httpResult = httpAPIComponent.doPostJson(url, pathMap, headerMap, bodyStr);
            return ResultUtil.success(httpResult);
        }else{
            return null;
        }
    }


    private static Map<String, Object> keyPairAssemble(List<KeyValuePair> list){
        Map<String, Object> map = new HashMap();
        if(list!=null&&list.size()>0) {
            for (KeyValuePair kv : list) {
                map.put(kv.getParamName(), kv.getValue());
            }
        }
        return map;
    }
}
