package com.workec.ectp.components;

import com.workec.ectp.entity.Vo.GlobalCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 程序启动后，把用户全局变量、应用环境配置信息加载到内存中.
 */

@Component
public class RunnerComponent implements ApplicationRunner {

    @Autowired
    private DataCacheComponent dataCacheComponent;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //缓存用户的全局变量信息
        dataCacheComponent.initGlobalParamInfo();

        //缓存应用环境的域名配置
        dataCacheComponent.initAppEnvInfo();

        GlobalCache.getInstance().pf();

        System.out.println("MyApplicationRunner class will be execute when the project was started!");

    }


}

