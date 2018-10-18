package com.doggy.sort;

/**
 * @author doggy1853
 *
 * 算法思路：
 * 1. 进行n轮循环
 * 2. 冒泡：每轮循环，从第一个元素开始，依次比较当前元素与下一元素的大小，如果当前元素大于下一元素，则交换两个元素，
 *    所有元素遍历结束后，最后一个元素即为最大元素。
 * 3. 如果发现某轮循环没有交换元素，则表示数组已经有序，可以提前终止循环。
 *
 * 算法最差时间复杂度：O(n^2)  数组逆序情况
 * 算法平均时间复杂度：O(n^2) 通过逆序数分布估计，一次冒泡交换，则数组逆序数减一.
 * 算法最佳时间复杂度：O(n) 数组有序情况
 *
 * 空间复杂度：O(1) 原地排序
 *
 * 稳定性： 稳定
 */
public class BubbleSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] array) {
        for(int i = array.length - 1;i > 0; --i){
            boolean isSwap = false;
            for(int j = 0;j < i;++j){
                if(array[j] > array[j + 1]){
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    isSwap = true;
                }
            }
            if(!isSwap){
                break;
            }
        }
    }
}
