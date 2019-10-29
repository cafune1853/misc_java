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
            // 初始堆化,这里采用从第一个父节点开始，往下
            for (int i = maxHeapSize/2 - 1; i >= 0 ; i--) {
                sinkDown(maxHeapSize, i);
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
                    sinkDown(currentHeapSize, 0);
                }
            }
        }

        private void sinkDown(int currentHeapSize, int startIndex) {
            int leftChildIndex = startIndex * 2 + 1;
            int rightChildIndex = startIndex * 2 + 2;
            while (leftChildIndex <= currentHeapSize - 1){
                int nextStartIndex = -1;
                if(array[leftChildIndex] > array[startIndex]){
                    swap(array, leftChildIndex, startIndex);
                    nextStartIndex = leftChildIndex;
                }
                if(rightChildIndex <= currentHeapSize - 1 && array[rightChildIndex] > array[startIndex]) {
                    swap(array, rightChildIndex, startIndex);
                    nextStartIndex = rightChildIndex;
                }
                if(nextStartIndex == -1){
                    break;
                }
                startIndex = nextStartIndex;
                leftChildIndex = startIndex * 2 + 1;
                rightChildIndex = startIndex * 2 + 2;
            }
        }

        private void swap(int array[], int i, int j){
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
