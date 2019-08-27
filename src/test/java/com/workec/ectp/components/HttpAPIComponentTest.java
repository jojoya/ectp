package com.workec.ectp.components;

import com.workec.ectp.entity.Bo.HttpResult;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2018/2/9.
 */
public class HttpAPIComponentTest {

    @Autowired
    HttpAPIComponent httpAPIComponent = new HttpAPIComponent();

    @Test
    public void testDoGet() throws Exception {

        String url = "http://www.baidu.com";

        Map<String,Object>  paths = new HashMap();
        paths.put("account","jojoya");
        paths.put("password","123abc");

        Map<String,Object>  headers = new HashMap();
        headers.put("hd1","this is header1");
        headers.put("hd2","this is header2");

        HttpResult httpResult = httpAPIComponent.doGet(url,paths,headers);
        System.out.println("httpResult:"+httpResult);
    }


    @Test
    public void regexTestDemo01(){
        String checkRegular_regex =  "\\$\\{Rsp:(.*?):regex@(.*?)}";
        System.out.println("checkRegular_regex:"+checkRegular_regex);

        //1、设置匹配规则
        String regexRegular =  "\\$\\{(.*?)::(.*?)}";
        System.out.println("regexRegular:"+regexRegular);

        //2、指定匹配内容
        String db_value = "AAA_${1::111}_BBB_${3::333}_CCC_${4::444}_DDD_${1::111}_EEE";

        //3、匹配
        Pattern pattern = Pattern.compile(regexRegular);
        Matcher matchResult = pattern.matcher(db_value);

        Pattern.matches(regexRegular,db_value);
        System.out.println("groupCount:"+ matchResult.groupCount());
        StringBuffer sb = new StringBuffer();
        int strStart = 0;
        final String[] CONST_KEY = {"", "全局", "公共", "中间", "正则"};
        Map<Integer, String> idMap = new HashMap<>();
        idMap.put(111, "a");
        idMap.put(333, "b");
        idMap.put(444, "c");
        while (matchResult.find()) {
//            System.out.println("start:"+ matchResult.start());
//            System.out.println("end:"+ matchResult.end());
//
//            System.out.println("group:"+ matchResult.group());
//            System.out.println("group(0):" + matchResult.group(0));
//            System.out.println("group(1):" + matchResult.group(1));
//            System.out.println("group(2):" + matchResult.group(2));
            System.out.println("xxxxxx:"+  matchResult.group(1));

            int type = Integer.valueOf(matchResult.group(1));
            int id = Integer.valueOf(matchResult.group(2));
            int start = matchResult.start();
            int end = matchResult.end();
            sb.append(db_value.substring(strStart, start));
            sb.append(CONST_KEY[type]);
            sb.append(idMap.get(id));
            strStart = end;
//            switch (type){
//
//                case 1: //全局变量,根据全局变量id、当前用户、数据环境获取变量的值
//                    id = id ;
//                    System.out.println(db_value.replace(matchResult.group(),"全局变量"));
//                    System.out.println(db_value);
//                    break;
//
//                case 2: //公共方法
//                    id = id ;
//                    System.out.println(db_value.replace(matchResult.group(),"公共方法"));
//                    break;
//
//                case 3:  //中间变量.正则提取，根据中间变量id，获取变量名以及提取参数的正则表达式，优先利用变量名去缓存中查询对应的值，如果为空，则利用正则表达式去执行结果中提取参数值，并与参数名一起保存到HashMap中去
//                    id = id ;
//                    System.out.println(db_value.replace(matchResult.group(),"中间变量.正则提取"));
//                    break;
//
//                case 4:  //中间变量.JSONPath提取，同正则表达式
//                    id = id ;
//                    System.out.println(db_value.replace(matchResult.group(),"中间变量.JSONPath"));
//                    break;
//
//                default:    //不做任何处理
//                    System.out.println("db_value:"+db_value);
//                    break;
//            }
//            System.out.println();
        }
        if ( strStart < db_value.length() ) {
            sb.append(db_value.substring(strStart));
        }
        System.out.println(sb.toString());
    }



    @Test
    public void testMap(){
        Map map = new HashMap();
        map.put(1111,111);
        map.put(2222,222);
        map.put(3333,333);
        map.put(4444,444);
        map.put(5555,555);
        map.put(6666,666);

        for (int i = 0; i < map.size(); i++) {
            System.out.println(map.get(i));
        }
    }
}