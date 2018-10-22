package com.doggy.sort;

import java.util.Arrays;

/**
 * @author doggy1853
 *
 * 算法思路：
 * 根据数据的可能范围，划分出m个桶，每个桶之间存在着天然的大小关系，遍历一遍数据，将数据分发到不同的桶中
 * 对每个桶使用其他排序，然后按桶顺序输出元素即可。
 *
 * 稳定性： 稳定
 */
public class BucketSortStrategy implements SortStrategy {
    private SortStrategy oneBucketSortStrategy;
    private int maxData;
    private int dataRange;

    public BucketSortStrategy(SortStrategy oneBucketSortStrategy, int maxData, int dataRange) {
        this.oneBucketSortStrategy = oneBucketSortStrategy;
        this.maxData = maxData;
        this.dataRange = dataRange;
    }

    @Override
    public void sort(int[] array) {
        int[][] buckets = new int[maxData/dataRange + 1][];
        for (int e : array) {
            int index = e / dataRange;
            if(buckets[index] == null){
                buckets[index] = new int[]{e};
            }else{
                int[] na = Arrays.copyOf(buckets[index], buckets[index].length + 1);
                na[na.length - 1] = e;
                buckets[index] = na;
            }
        }
        for (int[] bucket : buckets) {
            oneBucketSortStrategy.sort(bucket);
        }
        int idx = 0;
        for (int[] bucket : buckets) {
            for (int i : bucket) {
                array[idx] = bucket[i];
            }
        }
    }
}
