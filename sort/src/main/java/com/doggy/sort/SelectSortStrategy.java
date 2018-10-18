package com.doggy.sort;

/**
 * @author doggy1853
 *
 * 算法思路：
 * 1. 进行n轮循环
 * 2. 每轮循环从剩余数组中选出最小的元素，并与未排序的第一个元素交换
 *
 * 算法最差时间复杂度：O(n^2)
 * 算法平均时间复杂度：O(n^2)
 * 算法最佳时间复杂度：O(n^2)
 *
 * 空间复杂度：O(1) 原地排序
 *
 * 稳定性： 不稳定
 */
public class SelectSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] array) {
        for(int i = 0; i < array.length;++i){
            int minIndex = i;
            int minValue = array[i];
            int j = i + 1;
            while (j < array.length){
                if(array[j] < minValue){
                    minIndex = j;
                    minValue = array[j];
                }
                ++j;
            }
            array[minIndex] = array[i];
            array[i] = minValue;
        }
    }
}
