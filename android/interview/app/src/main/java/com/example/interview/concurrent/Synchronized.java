package com.example.interview.concurrent;

public class Synchronized {
    public void method1(){
        synchronized (this) {
            System.out.println("method1 start");
        }
    }
    public synchronized void method2() {
        System.out.println("method2 start");
    }
    public synchronized static void method3() {
        System.out.println("method3 start");
    }
}
