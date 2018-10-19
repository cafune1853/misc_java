package com.doggy.sort;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author doggy1853
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
