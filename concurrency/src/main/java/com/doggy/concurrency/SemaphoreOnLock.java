package com.doggy.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用锁 + 等待队列实现信号量
 */
public class SemaphoreOnLock {
	private final Lock lock;
	private final Condition condition;
	private int resources;
	
	public SemaphoreOnLock(int resources) {
		this.resources = resources;
		this.lock = new ReentrantLock();
		this.condition = this.lock.newCondition();
	}
	
	public void acquire() throws InterruptedException{
		lock.lock();
		try {
			while(resources - 1 < 0){
				condition.await();
			}
			--resources;
		}finally {
			lock.unlock();
		}
	}
	
	public void release(){
		lock.lock();
		try{
			++resources;
			//由于只能激活一个等待线程而且每个等待线程行为一致，所以使用signal来提高性能
			condition.signal();
		}finally {
			lock.unlock();
		}
	}
}
