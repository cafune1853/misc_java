package com.doggy.javabase.lock;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by huangzhw on 2016/8/31.
 */
public class ReadWriterLockTest {
    public static void main(String[] args) {
        final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        for(int i = 0;i < 100;++i){
            new Thread(() -> {
                int randomInt = ThreadLocalRandom.current().nextInt(2);
                if(randomInt == 0){
                    try {
                        readWriteLock.readLock().lock();
                        System.out.println("read");
                    }finally {
                        readWriteLock.readLock().unlock();
                    }
                }else{
                    try {
                        readWriteLock.writeLock().lock();
                        System.out.println("write");
                    }finally {
                        readWriteLock.writeLock().unlock();
                    }
                }
            }).start();
        }
    }
}
