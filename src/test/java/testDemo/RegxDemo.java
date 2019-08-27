package testDemo;

import com.workec.ectp.EctpApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class RegxDemo {

    @Test
    public void testRegx(){
        String paramValue = "打飞的xxxxxxxxxxxxxx中间变量-a--a--a-${1::1}xxxxxxxxxxxxxxx打飞的";
        String text2 = "全局变量-bbb-${1::1}";
        String text3 = "公共方法-ccc-${1::1}";
//        String regexRegular =  "^(全局变量|中间变量|公共方法)-(.*?)-\\$\\{(.*?)::(.*?)}$";
        String regexRegular =  "(全局变量|中间变量|公共方法)-(.*?)-\\$\\{(.*?)::(.*?)}";

        Pattern pattern = Pattern.compile(regexRegular);
        Matcher matchResult = pattern.matcher(paramValue);

        StringBuffer sb = new StringBuffer();
        int strStart = 0;
        while (matchResult.find()) {
            System.out.println("matchResult.group(1)"+matchResult.group(1));
            System.out.println("matchResult.group(2)"+matchResult.group(2));
            System.out.println("matchResult.group(3)"+matchResult.group(3));
            System.out.println("matchResult.group(4)"+matchResult.group(4));
            int type = Integer.valueOf(matchResult.group(3));
            int paramId = Integer.valueOf(matchResult.group(4));
            int start = matchResult.start();
            int end = matchResult.end();
            sb.append(paramValue.substring(strStart, start));

            System.out.println("matchResult.group():::::::::::"+matchResult.group());
            if(type==1){    //全局参数
                sb.append("全局全局全局全局");
            }else if(type==2){    //公共方法
                sb.append(matchResult.group());     //未实现，保持原样不做替换
            }else if(type==3){    //中间变量.正则
                sb.append("中间中间中间中间");
            }else if(type==4){    //中间变量. JSON
                sb.append(matchResult.group());     //未实现，保持原样不做替换
            }else {
                sb.append(matchResult.group());     //未能匹配到，保持原样不做替换
            }
            strStart = end;
        }
        if ( strStart < paramValue.length() ) {
            sb.append(paramValue.substring(strStart));
        }

        System.out.println("sb:"+sb);
    }
}
