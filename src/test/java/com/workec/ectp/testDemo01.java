package com.workec.ectp;

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
            while(matcher.find()){
                ls.add(matcher.group());
            }
            for (String string : ls) {
                System.out.println(string);
            }
    }

}
