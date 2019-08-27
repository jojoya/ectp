package com.workec.ectp.test.thread;

import java.util.Random;

/**
 * 使用ThreadLocal实现在线程范围内共享变量
 */
public class ThreadLocalTest2 {
//    private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();
    //private static ThreadLocal<MyThreadScopeData> myThreadScopeData = new ThreadLocal<MyThreadScopeData>();
    public static void main(String[] args) {
        for(int i=0;i<2;i++){
            new Thread(new Runnable(){
                @Override
                public void run() {
                    int data = new Random().nextInt();
//                    System.out.println(Thread.currentThread().getName()
//                            + " has put data :" + data);
//                    x.set(data);                //存放与当前线程有关的数据
/*                  MyThreadScopeData myData = new MyThreadScopeData();
                    myData.setName("name" + data);
                    myData.setAge(data);
                    myThreadScopeData.set(myData);*/
                    MyThreadScopeData.getThreadInstance().setName("name" + data);
                    MyThreadScopeData.getThreadInstance().setAge(data);

                    MyResultData.getThreadInstance().setName(data + "name");
                    MyResultData.getThreadInstance().setAge(data);
                    for (int i = 0; i < 5; i++) {
                        new A().get();
                        new B().get();
                    }

                    System.out.println(">>>>>>>>>"+MyThreadScopeData.getThreadInstance().toString()+"\n");

                }
            }).start();
        }
    }

    static class A{
        public void get(){
//            int data = x.get();
//            System.out.println("A from " + Thread.currentThread().getName() + " get data :" + data);
            MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
            System.out.println(System.currentTimeMillis()+":A from " + Thread.currentThread().getName()
                    + " getMyData: " + myData.getName() + "," +
                    myData.getAge());

            MyResultData myResultData = MyResultData.getThreadInstance();
            System.out.println(System.currentTimeMillis()+":A from " + Thread.currentThread().getName()
                    + " getMyData: " + myResultData.getName() + "," +
                    myResultData.getAge());
        }
    }

    static class B{
        public void get(){
//            int data = x.get();
//            System.out.println("B from " + Thread.currentThread().getName() + " get data :" + data);
            MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
            System.out.println(System.currentTimeMillis()+":B from " + Thread.currentThread().getName()
                    + " getMyData: " + myData.getName() + "," +
                    myData.getAge());

            MyResultData myResultData = MyResultData.getThreadInstance();
            System.out.println(System.currentTimeMillis()+":A from " + Thread.currentThread().getName()
                    + " getMyData: " + myResultData.getName() + "," +
                    myResultData.getAge());
        }
    }
}

class MyThreadScopeData{
    private MyThreadScopeData(){}               //构造方法私有化
    public static /*synchronized*/ MyThreadScopeData getThreadInstance(){
        MyThreadScopeData instance = map.get();     //通过map来判断有没有其他线程生成实例对象，如果没有就创建，所以不需要加入synchronized
        if(instance == null){
            instance = new MyThreadScopeData();
            map.set(instance);
        }
        return instance;
    }
    //private static MyThreadScopeData instance = null;//new MyThreadScopeData();
    private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<MyThreadScopeData>();//把ThreadLocal封装在一个类的内部

    private String name;
    private int age;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "MyThreadScopeData{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}


class MyResultData{
    private MyResultData(){}               //构造方法私有化
    public static /*synchronized*/ MyResultData getThreadInstance(){
        MyResultData instance = map.get();     //通过map来判断有没有其他线程生成实例对象，如果没有就创建，所以不需要加入synchronized
        if(instance == null){
            instance = new MyResultData();
            map.set(instance);
        }
        return instance;
    }
    //private static MyThreadScopeData instance = null;//new MyThreadScopeData();
    private static ThreadLocal<MyResultData> map = new ThreadLocal<MyResultData>();//把ThreadLocal封装在一个类的内部

    private String name;
    private int age;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}