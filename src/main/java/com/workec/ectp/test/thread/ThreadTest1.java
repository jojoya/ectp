package com.workec.ectp.test.thread;

//设计 4 个线程,其中两个线程每次对 j 增加 1,另外两个线程对 j 每次减少 1
public class ThreadTest1{

    private int j;
    public static void main(String args[]){
        ThreadTest1 tt=new ThreadTest1();
        Inc inc=tt.new Inc();
        Dec dec=tt.new Dec();
        for(int i=0;i<2;i++){
            Thread t=new Thread(inc);
            t.start();
            t=new Thread(dec);
            t.start();
        }
    }

    private synchronized void inc(){
        j++;
        System.out.println(Thread.currentThread().getName()+"-inc:"+j);
    }

    private synchronized void dec(){
        j--;
        System.out.println(Thread.currentThread().getName()+"-dec:"+j);
    }

    class Inc implements Runnable{      //线程1
        public void run(){
            for(int i=0;i<5;i++){
                inc();
            }
        }
    }

    class Dec implements Runnable{      //线程2
        public void run(){
            for(int i=0;i<5;i++){
                dec();
            }
        }
    }

}