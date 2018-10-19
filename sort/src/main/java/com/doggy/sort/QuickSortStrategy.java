package com.doggy.sort;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author doggy1853
 *
 * 算法思路：
 * 与归并排序类似，也是通过分治思想来处理数据的，通过在要排序的数组内随机选择一个元素pivot,然后分别从第一个元素和最后一个元素往中间遍历
 * 然后通过交换元素，将大于pivotValue的元素放到右边，将小于pivotValue的元素放到左边，这样就将原数组分为两个子数组，与pivot元素的正确位置。
 * 最后再分别排序两个子数组即可。
 *
 * 快速排序的算法复杂度与选择的pivot元素有很大的关系，如果选择的pivot能比较平均地将数组划分成两个数组，
 * 则性能较好，否则性能较差（极端情况下就是每轮都只能分出一个子数组）
 *
 * 算法最差时间复杂度：O(n^2)
 * 算法平均时间复杂度：O(n*lg(n))
 * 算法最佳时间复杂度：O(n*lg(n))
 *
 * 空间复杂度：O(1) 原地排序
 *
 * 稳定性： 不稳定
 */
public class QuickSortStrategy implements SortStrategy {
    @Override
    public void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    public void quickSort(int[] array, int p, int q){
        if(q > p){
            int r = partition(array, p, q);
            quickSort(array, p, r - 1);
            quickSort(array, r + 1, q);
        }
    }

    private int partition(int[] array, int p, int q) {
        int pivotIndex = pickPivot(p, q);
        int pivotValue = array[pivotIndex];
        int i = p;
        int j = q;
        // 从头到尾遍历并交换
        while (i != j){
            if (array[i] <= pivotValue){
                i++;
                continue;
            }
            if (array[j] >= pivotValue){
                j--;
                continue;
            }
            if(array[i] > pivotValue && array[j] < pivotValue){
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }
        // set pivotValue to correct index and get that index
        int correctIndex;
        if(i > pivotIndex){
            if(array[i] > pivotValue){
                correctIndex = i -1;
            }else{
                correctIndex = i;
            }
        }else if(i < pivotIndex){
            if (array[i] > pivotValue){
                correctIndex = i;
            }else{
                correctIndex = i + 1;
            }
        }else{
            correctIndex = i;
        }
        int tmp = array[correctIndex];
        array[correctIndex] = pivotValue;
        array[pivotIndex] = tmp;
        return correctIndex;
    }

    private static int pickPivot(int start, int end){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(start, end);
    }
}
