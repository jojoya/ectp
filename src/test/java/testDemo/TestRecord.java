package testDemo;
import java.text.SimpleDateFormat;
import java.util.*;

public class TestRecord {

    /*	时间偏移	*/
//    private static final Calendar now = Calendar.getInstance();
    Calendar now = Calendar.getInstance();
//    private static final int hour = now.get(Calendar.HOUR_OF_DAY);
    int hour = now.get(Calendar.HOUR_OF_DAY);
    private static final int DEVIATION_DATE_LEFT = -30;		//要求≤0
    private static final int DEVIATION_DATE_RIGHT = 10;		//left<0,right=0 不包含今日数据
//    private static final int DEVIATION_HOUR_LEFT = -hour;		//默认，无需调整
    int DEVIATION_HOUR_LEFT = -hour;		//默认，无需调整
//    private static final int DEVIATION_HOUR_RIGHT = 23-hour;	//默认，无需调整
    int DEVIATION_HOUR_RIGHT = 23-hour;	//默认，无需调整
    private static final int INDEX_USER_LEFT = 1;		//包含左右边界
    private static final int INDEX_USER_RIGHT = 2;	//包含左右边界


    /*	电话type	*/
    private static final Integer[] allCallTypes = new Integer[]{4,5,7,8,9,10,11,12,13,14,15,16,17,18,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,48,49};
    private static final Integer[] callOutTypes = new Integer[]{5,7,9,11,12,14,17,18,23,25,27,28,29,32,34,36,38,40,41,43,49};
    //    private static final Integer[] query = new Integer[]{7};
    private static final Integer[] f_call_type_zuoji = new Integer[]{4,5,10,11,13,14,15,16,17,18,22,23,24,25,26,27,35,36,37,38,39,40,42,43,48,49};
    private static final Integer[] f_call_type_yunhu = new Integer[]{12,28,33,34};
    private static final Integer[] f_call_type_zongji = new Integer[]{8,9};
    private static final Integer[] f_call_type_shouji = new Integer[]{7,41,44};
    private static final Integer[] f_call_type_yunzongji = new Integer[]{29,30};
    private static final Integer[] f_call_type_API = new Integer[]{31,32};

    /*	用户Users	*/
//    private static final Integer[] users = new Integer[]{1939153,1939158,1939175,2052324,2067016,2067020,2067024,2067028,2067036,2067040,2067048,2067060,2175699,2175703};
    private static final Integer[] users = new Integer[]{4855249, 5058271};
    private static final Integer corpId = 4855250;


    /**
     * 随机获取用户
     */
    private static int randomUser(){
        int len = INDEX_USER_RIGHT - INDEX_USER_LEFT + 1;
        int index=(int)(Math.random()*len) + INDEX_USER_LEFT -1;
//        System.out.println("随机获取用户" + users[index]);
        return users[index];
//        return ${userId};
    }


    /**
     * 获取随机整数
     */
    private static int myRandomInt(int minInt,int maxInt){
        if(maxInt>minInt) {
            Random random = new Random();
            int result = random.nextInt(maxInt - minInt) + minInt;
            return result;
        }else if(maxInt==minInt){
            return maxInt;
        }else {
            return 0;
        }
    }

    /**
     * 返回手机号码
     */
    private static String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
    private static String getTel() {
//        int index=myRandomInt(0,telFirst.length-1);
//        String first=telFirst[index];
//        String second=String.valueOf(myRandomInt(1,888)+10000).substring(1);
//        String third=String.valueOf(myRandomInt(1,9100)+10000).substring(1);
        String first="136";
        String second="000000";
        String third=String.valueOf(myRandomInt(1,50)+100).substring(1);
//        System.out.println(first+second+third);
        return first+second+third;
    }

    private static String getCrmId() {
        int temp = myRandomInt(1,50);
        if(temp>30){
            temp = 0;
        }
        String crmId=String.valueOf(temp);
//        System.out.println(crmId);
        return crmId;
    }

    /**
     * 检查数组是否包含某个值的方法
     */
    public static boolean isIncluded(Integer[] arr,int element){
        return Arrays.asList(arr).contains(element);
    }



    public void printSql(){
        /*	wasteId	**/
        long time = System.currentTimeMillis();
        int max=1000,min=1;
        int ran2 = (int) (Math.random()*(max-min)+min);
        String wasteId = String.valueOf(time)+ String.valueOf(ran2);
//        System.out.println("time:" + time);
//        System.out.println("ran2:" + ran2);

    /*	callType	**/
        Random random = new Random();
        int i = random.nextInt(allCallTypes.length);
        int callType = allCallTypes[i];

    /*	in_out_type	**/
        int in_out_type;
        if(isIncluded(callOutTypes,callType)){
            in_out_type = 2;
        }else{
            in_out_type = 1;
        }

    /*	f_call_type	**/
        int f_call_type = 0;
        if(isIncluded(f_call_type_zuoji,callType)){
            f_call_type = 1;
        }else if(isIncluded(f_call_type_yunhu,callType)){
            f_call_type = 2;
        }else if(isIncluded(f_call_type_zongji,callType)){
            f_call_type = 3;
        }else if(isIncluded(f_call_type_shouji,callType)){
            f_call_type = 4;
        }else if(isIncluded(f_call_type_yunzongji,callType)){
            f_call_type = 5;
        }else if(isIncluded(f_call_type_API,callType)){
            f_call_type = 6;
        }

    /*	timeInterval	**/
        int timeInterval = myRandomInt(-30,250);
        if(timeInterval<0){
            timeInterval = 0;
        }
        //log.info("timeInterval:" + timeInterval);

    /*	startTime/endTime	**/
        now.add(Calendar.DATE, myRandomInt(DEVIATION_DATE_LEFT,DEVIATION_DATE_RIGHT));
        now.add(Calendar.HOUR, myRandomInt(DEVIATION_HOUR_LEFT,DEVIATION_HOUR_RIGHT));
        long endTimeSt = now.getTime().getTime();

//        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(endTimeSt)));

        now.add(Calendar.SECOND,-timeInterval);
        long startTimeSt = now.getTime().getTime();

        Date endDate = new Date(endTimeSt);
        Date startDate = new Date(startTimeSt);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = simpleDateFormat.format(endDate);
        String startTime = simpleDateFormat.format(startDate);

    /*	tel	**/
        String tel = getTel();


        //String info ="('${corpId}',"+wasteId+",0,"+callType+", NULL,'"+tel+"','"+startTime+"','"+endTime+"','"+timeInterval+"', 0,'755',2,NULL,'"+startTime+"','"+randomUser()+"',0,'',NULL,NULL,0,0,0,"+
        String info ="(" + corpId + ","+wasteId+",0,"+callType+", NULL,'"+tel+"','"+startTime+"','"+endTime+"','"+timeInterval+"', 0,'755',2,NULL,'"+startTime+"','"+randomUser()+"',0,'',NULL,NULL,"+getCrmId()+",0,0,"+in_out_type+","+ f_call_type +",'123');";

        String data = "INSERT INTO `t_tel_record`  VALUES " + info;
        String data_31 = "INSERT INTO `t_tel_record_31`  VALUES " + info;

        System.out.println(data);
        System.out.println(data_31);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new TestRecord().printSql();
        }
    }


}
