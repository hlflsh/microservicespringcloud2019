package com.atguigu.test;

import java.util.concurrent.atomic.AtomicInteger;

//但是这个论据并不能得出"基于volatile变量的运算在并发下是安全的"这个结论，因为核心点在于java里的运算（比如自增）并不是原子性的。
public class AtomicIntegerTest {
	
    private static final int THREADS_CONUT = 20;
    //volatile关键字很重要的两个特性:
    //1、保证变量在线程间可见，对volatile变量所有的写操作都能立即反应到其他线程中，换句话说，volatile变量在各个线程中是一致的（得益于java内存模型—"先行发生原则"）；
    //2、禁止指令的重排序优化；
    //private static volatile int count = 0;
    private static AtomicInteger count = new AtomicInteger(0);
    
    //每次都输出20000，程序输出了正确的结果，这都归功于AtomicInteger.incrementAndGet()方法的原子性。
    public static void increase() {
    	count.incrementAndGet();
    }
 
    public static void main(String[] args) {
        Thread[] threads = new Thread[THREADS_CONUT];
        for (int i = 0; i < THREADS_CONUT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }
 
        while (Thread.activeCount() > 1) {
            Thread.yield();
        }
        System.out.println(count);
    }
}
