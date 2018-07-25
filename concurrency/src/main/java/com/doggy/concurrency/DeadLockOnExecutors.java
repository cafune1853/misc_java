package com.doggy.concurrency;

import java.util.concurrent.*;

/**
 * @author huangzhw
 * 由于线程池的内部机制（coreSize = 2）所导致的死锁问题
 */
public class DeadLockOnExecutors {
	public static void main(String[] args) {
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		Runnable runnable = () -> {
			System.out.println("in");
			try {
				cyclicBarrier.await();
			} catch (InterruptedException ite) {
				Thread.currentThread().interrupt();
			} catch (BrokenBarrierException bbe) {
				bbe.printStackTrace();
			}
			System.out.println("out");
		};
		executor.execute(runnable);
		executor.execute(runnable);
		executor.execute(runnable);
	}
}
