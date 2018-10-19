package com.doggy.sort;

/**
 * @author doggy1853
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
