package com.workec.ectp.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created by user on 2018/2/9.
 */
public class ToolsUtil {

    /*
    * 把JSONObject转换成Map
    * */
    public static Map<String, Object> transferMap(JSONObject data) throws Exception{
        if ( data == null ) {
            return Collections.emptyMap();
        }
        Map<String, Object> objMap = new HashMap<>();
        Iterator<String> it = data.keys();
        while ( it.hasNext() ) {
            String key = it.next();
            //拿出JSONObject
            Object child = data.get(key);
            if( child instanceof JSONObject ) {
                //JSONObject转Map后，添加到Map
                objMap.put(key, transferMap((JSONObject)child));
            }else if( child instanceof JSONArray) {
                //JSONArray转List后，添加到Map
                objMap.put(key, transferList((JSONArray)child));
            }else{
                //直接添加到Map
                objMap.put(key, child);
            }
        }
        return objMap;
    }


    /*
    * 把JSONArray转换成List
    * */
    public static List transferList(JSONArray data) throws Exception{
        if ( data == null ) {
            return Collections.emptyList();
        }

        List list = new ArrayList<>();
        for (int i =0;i<data.length();i++) {

            //拿出对象
            Object child = data.get(i);
            if( child instanceof JSONObject ) {
                //JSONObject转Map后，添加到List
                list.add(transferMap((JSONObject)child));
            }else if( child instanceof JSONArray) {
                //JSONArray转List后，添加到List
                list.add(transferList((JSONArray)child));
            }else{
                //直接添加到List
                list.add(child);
            }
        }

        return list;
    }

}
