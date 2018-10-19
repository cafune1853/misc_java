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
        while (i != j){
            if (array[i] <= pivotValue){
                i++;
                if(i == j){
                    break;
                }
            }
            if (array[j] >= pivotValue){
                j--;
                if(i == j){
                    break;
                }
            }
            if(array[i] > pivotValue && array[j] < pivotValue){
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }
        // set pivotValue to correct index and get that index
        if(i > pivotIndex){
            if(array[i] > pivotValue){
                int tmp = array[i - 1];
                array[i - 1] = pivotValue;
                array[pivotIndex] = tmp;
                return i - 1;
            }else{
                int tmp = array[i];
                array[i] = pivotValue;
                array[pivotIndex] = tmp;
                return i;
            }
        }
        if(i < pivotIndex){
            if(array[i] > pivotValue){
                int tmp = array[i];
                array[i] = pivotValue;
                array[pivotIndex] = tmp;
                return i;
            }else{
                int tmp = array[i + 1];
                array[i + 1] = tmp;
                array[pivotIndex] = tmp;
                return i + 1;
            }
        }
        return i;
    }

    private static int pickPivot(int start, int end){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return random.nextInt(start, end);
    }
}
