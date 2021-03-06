package com.workec.ectp;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2017/12/18.
 */
public class testDemo01 {

        public static void main( String args[] ){

        // 按指定模式在字符串查找
//        String line = "This order ${was} placed for QT3000! OK?";

//        String pattern = "(\\D*)(\\d+)(.*)";
//
//        // 创建 Pattern 对象
//        Pattern r = Pattern.compile(pattern);
//
//        // 现在创建 matcher 对象
//        Matcher m = r.matcher(line);
//        if (m.find( )) {
//            System.out.println("Found value: " + m.group(0) );
//            System.out.println("Found value: " + m.group(1) );
//            System.out.println("Found value: " + m.group(2) );
//            System.out.println("Found value: " + m.group(3) );
//        } else {
//            System.out.println("NO MATCH");
//        }

            String test = "{databaseIp}:{databasePort}{instanceName};database";
            List<String> ls=new ArrayList<>();
            Pattern pattern = Pattern.compile("(?<=\\{)(.+?)(?=\\})");
            Matcher matcher = pattern.matcher(test);
            int i = 1;
            while(matcher.find()){
                ls.add(matcher.group());
                if(i++==1) break;
            }
            for (String string : ls) {
                System.out.println(string);
            }
    }


    @Test
    public void test1(){

        //1、设置匹配规则
        String regexRegular =  "\\$\\{(.*?)::(.*?)}";

        //2、指定匹配内容
        String db_value = "AAA_${1::111}_BBB_${3::333}_CCC_${4::444}_DDD_${1::111}_EEE";

        //3、匹配
        Pattern pattern = Pattern.compile(regexRegular);
        Matcher matchResult = pattern.matcher(db_value);
        int i =1;
        Integer num = null;
        String paramValue=null;
        while(matchResult.find()){
            matchResult.group();
            if(num==i++){
                paramValue = matchResult.group(0);
                break;
            }
        }
        System.out.println("paramValue:"+paramValue);
    }

    @Test
    public void test2(){
        Integer num = null;
        int a = num!=null?num:0;
        System.out.println(a);
    }
}
