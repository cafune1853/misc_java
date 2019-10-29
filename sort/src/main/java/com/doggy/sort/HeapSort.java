package com.doggy.sort;

public class HeapSort implements SortStrategy {
    @Override
    public void sort(int[] array) {
        Heap heap = new Heap(array);
        heap.sort();
    }

    private static class Heap{
        private int[] array;
        private int maxHeapSize;
        private boolean sorted = false;

        public Heap(int[] array) {
            this.array = array;
            this.maxHeapSize = array.length;
            // 初始堆化
            for (int i = maxHeapSize/2 - 1; i >= 0 ; i--) {
                int left = i * 2 + 1;
                if(left <= maxHeapSize - 1 && array[left] > array[i]){
                    swap(array, i, left);
                }
                int right = i * 2 + 2;
                if (right <= maxHeapSize - 1 && array[right] > array[i]){
                    swap(array, i, right);
                }
            }
        }

        // 每次将堆顶元素与最后一个元素交换位置，然后缩小堆，通过下沉操作调整堆，重复此过程即得到升序结果。
        public void sort(){
            if(!sorted){
                for (int currentHeapSize = maxHeapSize; currentHeapSize > 1 ; ) {
                    int temp = array[currentHeapSize - 1];
                    array[currentHeapSize - 1] = array[0];
                    array[0] = temp;
                    currentHeapSize--;
                    int start = 0;
                    while (start * 2 + 1 <= currentHeapSize - 1){
                        int next = -1;
                        if(array[start * 2 + 1] > array[start]){
                            swap(array, start*2+1, start);
                            next = start * 2 + 1;
                        }
                        if(start * 2 + 2 <= currentHeapSize - 1 && array[start * 2 + 2] > array[start]) {
                            swap(array, start * 2 + 2, start);
                            next = start * 2 + 2;
                        }
                        if(next == -1){
                            break;
                        }
                        start = next;
                    }
                }
            }
        }
        private void swap(int array[], int i, int j){
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
