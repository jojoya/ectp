package com.workec.ectp.test.thread;

/**
 * Created by user on 2018/3/21.
 */
public class MultiThreadShareData {//多线程卖票，一个加，一个减

    //private static ShareData1 data1 = new ShareData1();

    public static void main(String[] args) {

        //
        final ShareData1 data1 = new ShareData1();
        data1.setJ(1000);
        new Thread(new Runnable(){
            @Override
            public void run() {
                data1.decrement();

            }
        }).start();
        new Thread(new Runnable(){
            @Override
            public void run() {
                data1.increment();

            }
        }).start();


        ShareData1 data2 = new ShareData1();
        data2.setJ(-10);
        for (int i = 0; i <5 ; i++) {
            new Thread(new MyRunnable1(data2)).start();
            new Thread(new MyRunnable2(data2)).start();
        }

    }

}

class MyRunnable1 implements Runnable{      //线程1
    private ShareData1 data1;
    public MyRunnable1(ShareData1 data1){
        this.data1 = data1;
    }
    public void run() {
        data1.decrement();

    }
}

class MyRunnable2 implements Runnable{      //线程2
    private ShareData1 data1;
    public MyRunnable2(ShareData1 data1){
        this.data1 = data1;
    }
    public void run() {
        data1.increment();
    }
}

class ShareData1 /*implements Runnable*/{   //共享对象
/*      private int count = 100;
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(true){
                count--;
            }
        }*/

    private int j;
    public synchronized void increment(){
        j++;
        System.out.println("j = "+j);
    }

    public synchronized void decrement(){
        j--;
        System.out.println("j = "+j);

    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }
}
