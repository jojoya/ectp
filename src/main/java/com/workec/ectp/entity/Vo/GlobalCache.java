package com.workec.ectp.entity.Vo;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存全局变量信息，包含用户全局变量、应用环境域名IP配置
 */
public class GlobalCache {

    @Setter
    @Getter
    private Map<Integer,Object> userGlobalParamMap = new HashMap();    //用户的全局变量信息

    @Setter
    @Getter
    private Map<Integer,Object> appEnvMap = new HashMap();     //应用环境的域名配置

    // 创建唯一的实例
    private static final GlobalCache ONLY = new GlobalCache();


    // 屏蔽外部的new
    private GlobalCache(){

    }


    // 创建一个全局的访问点
    public static GlobalCache getInstance(){
        return ONLY;
    }

    public void pf(){
        System.out.println("userGlobalParamMap:"+this.userGlobalParamMap);
        System.out.println("appEnvMap:"+this.appEnvMap);
    }


}
