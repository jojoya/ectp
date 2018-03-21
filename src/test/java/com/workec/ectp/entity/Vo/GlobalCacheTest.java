package com.workec.ectp.entity.Vo;

import com.fasterxml.jackson.databind.deser.Deserializers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Deserializers.Base.class)
public class GlobalCacheTest {


    @Test
    public void pf() throws Exception {

        GlobalCache globalCache = GlobalCache.getInstance();
        System.out.println("getUserGlobalParamMap:>>>>"+ globalCache.getUserGlobalParamMap());
        System.out.println("getAppEnvMap:>>>>"+ globalCache.getAppEnvMap());
        globalCache.pf();
    }

}