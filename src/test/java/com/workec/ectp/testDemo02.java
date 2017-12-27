package com.workec.ectp;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 2017/12/18.
 */
public class testDemo02 {

        public static void main( String args[] ){

            String test = "${Rsp:1:regex@\"accessToken\":\"(.*?)\",\"expiresIn\"}";
            List<String> ls=new ArrayList<>();
            Pattern pattern = Pattern.compile("\\$\\{Rsp:(.*?):regex@(.*?)\\}");
            Matcher matcher = pattern.matcher(test);
            while(matcher.find()){
                ls.add(matcher.group());
            }
            for (String string : ls) {
                System.out.println(string);
            }
    }

}
