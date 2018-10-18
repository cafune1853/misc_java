package com.doggy.sort;

/**
 * @author doggy1853
 *
 * 算法思路：
 * 1. 进行n - 1轮循环
 * 2. 每轮循环将当前元素往前插入
 * （可以通过当前元素，将数组分为两部分，前部分可以理解为有序的手牌，后半部分为无序的牌堆，然后从牌堆选牌插入即可）
 *
 * 算法最差时间复杂度：O(n^2)  数组逆序情况
 * 算法平均时间复杂度：O(n^2) 通过逆序数分布估计，一次元素后移插入，则数组逆序数减一.
 * 算法最佳时间复杂度：O(n) 数组有序情况
 *
 * 空间复杂度：O(1) 原地排序
 *
 * 稳定性：稳定
 */
public class InsertSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] array) {
        for(int i = 1; i < array.length; ++i){
            // 当前要插入的元素
            int value = array[i];
            int j = i - 1;
            // 从后往前插入
            while (j >= 0){
                if(array[j] > value){
                    array[j + 1] = array[j];
                    --j;
                }else{
                    break;
                }
            }
            array[j+1] = value;
        }
    }
}
