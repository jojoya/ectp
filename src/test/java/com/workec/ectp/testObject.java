package com.workec.ectp;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 2018/3/12.
 */
public class testObject {

    @Test
    public void testStringToObject(){
        String str = "{\"name\":\"jojoya\",\"password\":123}";
        Object obj = str;
        System.out.println("str>Obj:"+obj);

        System.out.println("Obj>str:"+obj.toString());

    }

    @Test
    public void testMapToObject(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","jojoya");
        map.put("password","123");
        Object obj = map;
        System.out.println("map>Obj:"+obj);

        map = (Map<String,Object>)obj;
        System.out.println("Obj>map:"+map);

    }
}
