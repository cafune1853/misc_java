package com.doggy.javabase.synchronizer;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class SimpleFuture<T>{
    private static final class Sync extends AbstractQueuedSynchronizer{
        @Override
        protected int tryAcquireShared(int arg) {
            return this.getState() >= 1 ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            this.setState(1);
            return true;
        }

    }

    private final Sync sync = new Sync();
    private T t;

    public T get(){
        sync.acquireShared(0);
        return t;
    }

    public void set(T t){
        this.t = t;
        sync.releaseShared(0);
    }
}
