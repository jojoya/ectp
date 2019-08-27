package com.workec.ectp.test.thread;

import java.util.Map;
import java.util.Random;

/**
 * 定义一个全局共享的ThreadLocal变量，然后启动多个线程向该ThreadLocal变量中存储一个随机值，
 * 接着各个线程调用另外其他多个类的方法，这多个类的方法中读取这个ThreadLocal变量的值，
 * 就可以看到多个类在同一个线程中共享同一份数据。
 */
public class ThreadLocalTest {

    private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();
    private static ThreadLocal<Map<Integer,Object>> global = new ThreadLocal<>();

    public static void main(String[] args) {
        for(int i=0;i<2;i++){
            new Thread(new Runnable(){
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName()+
                           " has put data :" + data);
                    x.set(data);     //存放与当前线程有关的数据
                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    static class A{
        public void get(){
            int data = x.get();
            System.out.println("A from " + Thread.currentThread().getName()
                    + " get data :" + data);
        }
    }

    static class B{
        public void get(){
            int data = x.get();
            System.out.println("B from " + Thread.currentThread().getName()
                    + " get data :" + data);
        }
    }
}
