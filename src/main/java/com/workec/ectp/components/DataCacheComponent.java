package com.workec.ectp.components;

import com.workec.ectp.dao.jdbc.IAppEnvDataDao;
import com.workec.ectp.dao.jdbc.IGlobalParamsDataDao;
import com.workec.ectp.dao.jpa.ApplicationEnvironmentDao;
import com.workec.ectp.dao.jpa.DataEnvironmentDao;
import com.workec.ectp.dao.jpa.UserDao;
import com.workec.ectp.entity.Bo.AppEnvDetailInfo;
import com.workec.ectp.entity.Bo.GlobalParamsDataInfo;
import com.workec.ectp.entity.Do.ApplicationEnvironment;
import com.workec.ectp.entity.Do.DataEnvironment;
import com.workec.ectp.entity.Do.User;
import com.workec.ectp.entity.Vo.GlobalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Component
public class DataCacheComponent {

    @Autowired
    UserDao userDao;

    @Autowired
    DataEnvironmentDao dataEnvironmentDao;

    @Autowired
    ApplicationEnvironmentDao applicationEnvironmentDao;

    @Autowired
    IGlobalParamsDataDao iGlobalParamsDataDao;

    @Autowired
    IAppEnvDataDao iAppEnvDataDao;

    private GlobalCache globalCache = GlobalCache.getInstance();



    /**
     * 初始化所有用户的全局变量信息到内存中
     * */
    public void initGlobalParamInfo(){
        //清空数据
        globalCache.setUserGlobalParamMap(new HashMap<>());

        //缓存数据
        Map<Integer,Object> userMap = globalCache.getUserGlobalParamMap();
        List<User> userList = userDao.findAll();
        for (User user:userList) {
            int userId = user.getId();
            Map<Integer,Object> userGlobalParams = getGlobalParamInfoByUserId(user.getId());
            if(userGlobalParams!=null&&userGlobalParams.size()>0) {
                userMap.put(userId, userGlobalParams);
            }
        }

    }



    /**
     * 根据用户Id和数据环境Id更新内存中的全局变量信息
     * */
    public void updateGlobalParamInfoByUserIdAndEnvId(Integer userId, Integer dbEnvId){
        Map<Integer,Object> mapUser = globalCache.getUserGlobalParamMap();
        Map<Integer,Object> dbEnvMap = (Map<Integer,Object>) mapUser.get(userId);
        Map<String,Object> keyValueMap = getGlobalParamInfoByUserIdAndEnvId(userId,dbEnvId);
        dbEnvMap.put(dbEnvId,keyValueMap);
        mapUser.put(userId,dbEnvMap);
    }



    /**
     * 根据用户Id查询全局变量信息
     * */
    public Map<Integer,Object> getGlobalParamInfoByUserId(Integer userId){
        Map<Integer,Object> envMap = new HashMap();

        List<DataEnvironment> dataEnvironments = dataEnvironmentDao.findAll();
        for (DataEnvironment envs:dataEnvironments) {
            envMap.put(envs.getId(),getGlobalParamInfoByUserIdAndEnvId(userId,envs.getId()));
        }

        return envMap;
    }



    /**
     * 根据用户Id和数据环境Id查询全局变量信息
     * */
    public Map<String,Object> getGlobalParamInfoByUserIdAndEnvId(Integer userId, Integer dbEnvId){
        Map<String,Object> keyValueMap = new HashMap();
        List<GlobalParamsDataInfo> list = iGlobalParamsDataDao.findByUserAndEnv(userId,dbEnvId);
        for (GlobalParamsDataInfo info:list) {
            keyValueMap.put(info.getParamName(),info.getParamValue());
        }

        return keyValueMap;
    }



    /**
     * 初始化应用环境信息到内存中
     * */
    public void initAppEnvInfo(){
        //清空数据
        globalCache.setAppEnvMap(new HashMap<>());

        //缓存数据
        Map<Integer,Object> appEnvMap = globalCache.getAppEnvMap();
        List<ApplicationEnvironment> appList = applicationEnvironmentDao.findAll();
        for (ApplicationEnvironment appEnv:appList) {
            int appEnvId = appEnv.getId();
            appEnvMap.put(appEnvId,getAppEnvInfoByUserId(appEnvId));
        }

    }



    /**
     * 根据应用环境Id更新内存中的应用环境信息
     * */
    public void updateAppEnvInfo(Integer appEnvId){
        Map<Integer,Object> map = globalCache.getUserGlobalParamMap();
        map.put(appEnvId,getAppEnvInfoByUserId(appEnvId));

    }



    /**
     * 根据应用环境Id查询域名、IP配置信息
     * */
    public Map<String,String> getAppEnvInfoByUserId(Integer appEnvId){
        Map<String,String> domainIpMap = new HashMap();
        List<AppEnvDetailInfo> appEnvDetailInfos = iAppEnvDataDao.findByEnvId(appEnvId);
        for (AppEnvDetailInfo appEnv:appEnvDetailInfos) {
            String domainName = appEnv.getDomainName();
            String ip = appEnv.getIp();
            domainIpMap.put(domainName,ip);
        }

        return domainIpMap;
    }


}
