package testDemo;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegxDemo1 {

    public String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }

    public String[] txt2Split(String text, String tag){
        Pattern p=Pattern.compile(tag);
        String[] strs=p.split(text);
        return strs;
    }

    public String testRegx(String str, int member){
        String regexRegular = "paras:(.*?),costTime:";

        Pattern pattern = Pattern.compile(regexRegular);
        Matcher matchResult = pattern.matcher(str);

        String result = "";
        while (matchResult.find()) {
            try {
                result = matchResult.group(member);
            }catch (IndexOutOfBoundsException e){
                result = "";
            }
        }
        System.out.println("result：" + result);
        return result;
    }

    public static void main(String[] args) {
        RegxDemo1 demo = new RegxDemo1();
        int member = 1;

        File file = new File("D:/logDemo.txt");
        String text = demo.txt2String(file);
        System.out.println("text:" + text);

        String tag = "requestTime:";
        String[] items=demo.txt2Split(text,tag);


        for (int i = 0; i < items.length; i++) {
            String item = items[i];
            System.out.println("item:" + item);

            demo.testRegx(item, member);
        }

//        String paramValue = "requestTime:1551801605545,corpId:5169122,paras:{\"crmIds\":\"2396852694,2396898507,2397050497,2397090595,2397090710,2397213779,2397213781,2397239221,2397264617,2397264620\",\"date\":\"2019-02-03;2019-03-05\",\"style\":3,\"type\":3001},costTime:23\n";

    }
}
