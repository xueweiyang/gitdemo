package com.example.interview.concurrent;

public class Volatile {
    volatile int a=1;
    int b=2;

    void change(){
        a=3;
        b=a;
    }

    void print(){
        System.out.println("a:"+a+" b:"+b);
    }

    public void method() {
        final Volatile test=new Volatile();
        new Thread(new Runnable(){
            @Override
            public void run() {
                test.change();
            }
        }).start();
        new Thread(new Runnable(){
            @Override
            public void run() {
                test.print();
            }
        }).start();
    }
}
