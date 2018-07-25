package com.doggy.concurrency;

/**
 * Created by huangzhw on 2016/8/30.
 */
public class Frequency {
    public enum TimeUnit{MILLISECOND,SECOND,MINUTE,HOUR,DAY,WEEK}
    //Frequency = maxCount / timeSpan.
    private volatile long recentRecordTime;
    private long timeSpan;
    private volatile int counter;
    private int maxRunner;
    private volatile long slowTime;
    private final Object incLock;
    private final Object refreshLock;
    
    
    private Frequency(long timeSpan,int maxRunner){
        this.maxRunner = maxRunner;
        this.timeSpan = timeSpan;
        counter = 0;
        slowTime = 0;
        recentRecordTime = System.currentTimeMillis();
        incLock = new Object();
        refreshLock = new Object();
    }
    
    public static Frequency getInstance(TimeUnit timeUnit,long timeSpan,int maxRunner){
        switch (timeUnit){
            case MILLISECOND:
                return new Frequency(timeSpan,maxRunner);
            case SECOND:
                return new Frequency(timeSpan*1000,maxRunner);
            case MINUTE:
                return new Frequency(timeSpan*60*1000,maxRunner);
            case HOUR:
                return new Frequency(timeSpan*60*60*1000,maxRunner);
            case DAY:
                return new Frequency(timeSpan*24*60*60*1000,maxRunner);
            case WEEK:
                return new Frequency(timeSpan*7*24*60*60*1000,maxRunner);
            default:
                throw new IllegalArgumentException("Impossible");
        }
    }
    
    public void incAndWait() throws InterruptedException{
        if(slowTime != 0){
            Thread.sleep(slowTime);
            slowTime = 0;
        }
        while(true){
            if(counter < maxRunner){
                synchronized (incLock){
                    if(counter < maxRunner){
                        this.counter++;
                        break;
                    }
                }
            }else{
                Thread.sleep(100);
                refreshTime();
            }
        }
    }
    
    private void refreshTime(){
        long currentTime = System.currentTimeMillis();
        if(currentTime - recentRecordTime > timeSpan){
            synchronized (refreshLock){
                if(currentTime - recentRecordTime > timeSpan){
                    recentRecordTime = System.currentTimeMillis();
                    this.counter = 0;
                }
            }
        }
    }
}
