package com.workec.ectp.components.method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserWebLoginTest {

    @Autowired
    UserWebLogin UserWebLogin;

    @Test
    public void testGetAccountAuthority(){
        Map map = UserWebLogin.webLogin(7,1);
        System.out.println("map:"+map);
        System.out.println("cookie:"+map.get("cookie"));
        System.out.println("ec_csrf_token:"+map.get("ec_csrf_token"));
    }

}