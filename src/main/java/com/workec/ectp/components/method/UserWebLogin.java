package com.workec.ectp.components.method;

import com.alibaba.druid.support.json.JSONUtils;
import com.workec.ectp.components.CaseExecuteComponent;
import com.workec.ectp.components.DataCacheComponent;
import com.workec.ectp.components.HttpAPIComponent;
import com.workec.ectp.configuration.Configuration;
import com.workec.ectp.dao.jpa.GlobalParamsDao;
import com.workec.ectp.entity.Bo.CallInterfaceResult;
import com.workec.ectp.entity.Bo.HttpResult;
import com.workec.ectp.entity.Bo.InterfaceInitDataBackEnd;
import com.workec.ectp.entity.Do.GlobalParams;
import com.workec.ectp.entity.Do.MiddleParam;
import com.workec.ectp.enums.ReqDataMethod;
import com.workec.ectp.utils.ToolsUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by caixiaoling on 2018/4/9.
 */

@Component
public class UserWebLogin {

    @Autowired
    private DataCacheComponent dataCacheComponent;

    @Autowired
    private HttpAPIComponent httpAPIComponent;

    @Autowired
    private GlobalParamsDao globalParamsDao;



    /** 1 获取ec的cookie、token    */
    private Map getCookieAndToken(Integer appEnvId){
        Map resultMap = new HashMap();

        //初始化数据
        String domain = "www.workec.com";
        if(Configuration.getEnvChange()==true) {
            String ip = dataCacheComponent.getIpByDomainAndEnvId(appEnvId, domain);
            domain = ip == null ? domain : ip;
        }
        String url = "https://" + domain + "/default/statistics";

        //调用接口
        HttpResult httpResult = httpAPIComponent.doGet(url,null,null);

        //提取数据
        if(httpResult!=null && httpResult.getStatusCode()==200){
            String textToSearch = ToolsUtil.mapToHeader(httpResult.getHeaders()).toString();

            //获取ec_csrf_token
            String ecCsrfToken = null;
            String tokenRegx = "ec_csrf_token=(.+?);";
            Matcher tokenMatcher = Pattern.compile(tokenRegx).matcher(textToSearch);
            if(tokenMatcher.find()) {
                ecCsrfToken =  tokenMatcher.group(1);
            }
            if(ecCsrfToken!=null) {
                //提取成功，更新到缓存中去
                resultMap.put("ec_csrf_token",ecCsrfToken);
            }else {
            }

            //获取cookie
            String cookie = null;
            String cookieRegx = "Set-Cookie:(.+?),";
            Matcher cookieMatcher = Pattern.compile(cookieRegx).matcher(textToSearch);
            if(cookieMatcher.find()) {
                cookie =  cookieMatcher.group(1);
            }
            if(cookie!=null) {
                //提取成功，更新到缓存中去
                resultMap.put("cookie",cookie);
            }else {
            }
        }

        return resultMap;
    }

    /** 2 账号权限js加密    */
    private String accountInfoEncryption(Integer userId,Integer appEnvId){
        String info = null;
        String url = "http://10.0.201.197:3000/api/tester/encrypt";

        int accountId = globalParamsDao.findByParamName("account").getId();
        int passwordId = globalParamsDao.findByParamName("password").getId();
        String account = dataCacheComponent.getParamValueByUserIdAndEnvIdAndParamId(userId,appEnvId,accountId);
        String password = dataCacheComponent.getParamValueByUserIdAndEnvIdAndParamId(userId,appEnvId,passwordId);
        Map map = new HashMap();
        map.put("account",account);
        map.put("password",password);
        map.put("step",1);
        Map<String,Object> bodyMap = new HashMap();
        bodyMap.put("str",new JSONObject(map).toString());

        //调用接口
        HttpResult httpResult = httpAPIComponent.doPostForm(url,null,null,bodyMap);

        //提取数据
        if(httpResult!=null && httpResult.getStatusCode()==200) {
            //获取ec_csrf_token
            Map bodyResultMap = (Map)httpResult.getBody();
            Map infoMap = (Map)bodyResultMap.get("info");
            info = JSONUtils.toJSONString(infoMap);
        }
        return info;
    }


    /** 3 登录信息加密    */
    private String loginInfoEncryption(){
        String info = null;
        String url = "http://10.0.201.197:3000/api/tester/encrypt";

        Map map = new HashMap();
        map.put("type","corp");
        map.put("password","https://corp.workec.com");
        map.put("step",2);
        Map<String,Object> bodyMap = new HashMap();
        bodyMap.put("str",new JSONObject(map).toString());

        //调用接口
        HttpResult httpResult = httpAPIComponent.doPostForm(url,null,null,bodyMap);

        //提取数据
        if(httpResult!=null && httpResult.getStatusCode()==200) {
            //获取ec_csrf_token
            Map bodyResultMap = (Map)httpResult.getBody();
            Map infoMap = (Map)bodyResultMap.get("info");
            info = JSONUtils.toJSONString(infoMap);
        }
        return info;
    }


    /** 4 登录    */
    private void userLogin(Integer appEnvId,String info,Map middleParams){
        //初始化数据
        String domain = "www.workec.com";
        if(Configuration.getEnvChange()==true) {
            String ip = dataCacheComponent.getIpByDomainAndEnvId(appEnvId, domain);
            domain = ip == null ? domain : ip;
        }
        String url = "https://" + domain + "/login/checklogin";

        Map headerMap = new HashMap();
        headerMap.put("Cookie",middleParams.get("cookie"));
        headerMap.put("ec_csrf_token",middleParams.get("ec_csrf_token"));

        Map bodyMap = new HashMap();
        bodyMap.put("info",info);
        bodyMap.put("ec_csrf_token",middleParams.get("ec_csrf_token"));

        //调用接口
        httpAPIComponent.doPostForm(url,null,headerMap,bodyMap);

    }



    /** 5 二次密码登录   */
    private void userLoginFor2Pwd(Integer userId,Integer appEnvId,Map middleParams) {
        //初始化数据
        String domain = "www.workec.com";
        if (Configuration.getEnvChange() == true) {
            String ip = dataCacheComponent.getIpByDomainAndEnvId(appEnvId, domain);
            domain = ip == null ? domain : ip;
        }
        String url = "https://" + domain + "/visitapi/security/managelogin";

        int password2Id = globalParamsDao.findByParamName("password2").getId();
        String password2 = dataCacheComponent.getParamValueByUserIdAndEnvIdAndParamId(userId, appEnvId, password2Id);

        Map headerMap = new HashMap();
        headerMap.put("cookie", middleParams.get("cookie"));
        headerMap.put("ec_csrf_token", middleParams.get("ec_csrf_token"));

        Map bodyMap = new HashMap();
        bodyMap.put("password", password2);
        bodyMap.put("ec_csrf_token", middleParams.get("ec_csrf_token"));

        //调用接口
        httpAPIComponent.doPostForm(url, null, headerMap, bodyMap);
    }




    /**
     1 获取ec的cookie、token
     2 账号权限js加密
     3 登录获取账号权限
     4 登录信息加密
     5 登录
     6 二次密码登录
     */
    public Map webLogin(Integer userId,Integer appEnvId){

        Map middleParams = new HashMap();

        //1 获取ec的cookie、token
        Map map = getCookieAndToken(appEnvId);
        middleParams.putAll(map);

        //2 账号权限js加密
        String info1 = accountInfoEncryption(userId,appEnvId);
        middleParams.put("info1",info1);

        //3 登录获取账号权限
        userLogin(appEnvId,middleParams.get("info1").toString(),middleParams);

        //4 登录信息加密
        String info2 = loginInfoEncryption();
        middleParams.put("info2",info2);

        //5 登录
        userLogin(appEnvId,middleParams.get("info2").toString(),middleParams);

        //6 二次密码登录
        userLoginFor2Pwd(userId,1,middleParams);


        Map resultMap = new HashMap();
        resultMap.put("cookie",middleParams.get("cookie"));
        resultMap.put("ec_csrf_token",middleParams.get("ec_csrf_token"));
        return resultMap;
    }



}
