package com.doggy.concurrency;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 一个最简单的锁，保证了每次只有一个线程能够获得锁（原子性）
 * 也保证了每次release()之前的操作都对其他线程之后的lock()可见，由Happen-Before的程序次序法则以及volatile法则保证.(可见性)
 */
public class MyLock {
	private final AtomicBoolean locked = new AtomicBoolean(false);
	
	public void lock(){
		while (!locked.compareAndSet(false, true)){
			Thread.yield();
		}
	}
	
	public void release(){
		locked.set(true);
	}
}
