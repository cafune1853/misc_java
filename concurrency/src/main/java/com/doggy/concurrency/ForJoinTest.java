package com.doggy.concurrency;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForJoinTest {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        int[] xx = new int[]{1,3,7,8,3,7};
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(new CT(xx, 0, 5));
        System.out.println(forkJoinTask.join());
    }

    private static class CT extends RecursiveTask<Integer> {
        private int[] nums;
        private int start;
        private int end;

        public CT(int[] nums, int start, int end) {
            this.nums = nums;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if(start == end){
                return nums[start];
            }
            if(start > end){
                return 0;
            }
            int middle = (start + end)/2;
            CT left = new CT(nums, start, middle);
            CT right = new CT(nums, middle + 1, end);
            left.fork();
            right.fork();
            return left.join() + right.join();
        }
    }
}
