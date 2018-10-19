package com.doggy.sort;

/**
 * @author doggy1853
 *
 * 算法思路：
 * 主要思想在于分解与合并，拿到一个无序数组时，如果数组大小<=1,则无需处理，否则先将其从中间拆分成两个子数组，然后对每个子数组进行归并排序（分解），
 * 然后对两个有序的子数组，从头开始比较两个数组，进行排序。（合并）
 *
 * 归并排序的时间复杂度是稳定的，和实际的数据输入没有任何关系（都需要分解-合并）。时间表达式：T(n) = 2T(n/2) + n
 * 算法最差时间复杂度：O(n*lg(n))
 * 算法平均时间复杂度：O(n*lg(n))
 * 算法最佳时间复杂度：O(n*lg(n))
 *
 * 空间复杂度：O(n) 在合并阶段，会需要大小为n的额外空间用于数组合并,由于分解行为在合并之前，所以内存空间已释放，所以占用的最大空间为O(n)
 *
 * 稳定性：稳定
 */
public class MergeSortStrategy implements SortStrategy{
    @Override
    public void sort(int[] array) {
        mergeSort(array, 0, array.length - 1);
    }

    private void mergeSort(int[] array, int p, int q){
        if(q > p){
            int r = (p + q) / 2;
            mergeSort(array, p, r);
            mergeSort(array, r + 1, q);
            merge(array, p, q, r);
        }
    }

    private void merge(int[] array, int p, int q, int r){
        int[] preArray = new int[r - p + 1];
        int[] afterArray = new int[q - r];
        for(int i = p;i <= r;++i){
            preArray[i - p] = array[i];
        }
        for(int i = r + 1;i <= q;++i){
            afterArray[i - r - 1] = array[i];
        }
        int pre = 0;
        int after = 0;
        for (int i = p;i <= q;++i){
            if(pre >= preArray.length){
                array[i] = afterArray[after];
                ++after;
                continue;
            }
            if(after >= afterArray.length){
                array[i] = preArray[pre];
                ++pre;
                continue;
            }
            if(preArray[pre] <= afterArray[after]){
                array[i] = preArray[pre];
                pre++;
            }else{
                array[i] = afterArray[after];
                after++;
            }
        }
    }
}
