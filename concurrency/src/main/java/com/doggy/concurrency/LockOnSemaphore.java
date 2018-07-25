package com.doggy.concurrency;

import java.util.concurrent.Semaphore;

public class LockOnSemaphore {
	private final Semaphore semaphore;
	
	public LockOnSemaphore() {
		this.semaphore = new Semaphore(1);
	}
	
	public void lock(){
		try {
			this.semaphore.acquire();
		}catch (InterruptedException ie){
			Thread.currentThread().interrupt();
		}
	}
	
	public void unlock(){
		this.semaphore.release();
	}
}
