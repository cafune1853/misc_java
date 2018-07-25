package com.doggy.datastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzhw on 2016/8/18.
 */
public class Heap<T extends Comparable<? super T>> {
	public enum HeapType {
		MAX, MIN
	}

	/** 堆的整体大小 **/
	private final int maxSize;
	private List<T> values;
	private int lastElementIndex = -1;
	private HeapType heapType;

	public Heap(int maxSize, HeapType heapType) {
		this.maxSize = maxSize;
		this.heapType = heapType;
        values = new ArrayList<>();
	}
	
	public T popTop(){
	    if(isEmpty()){
	        return null;
        }else{
            swap(0,lastElementIndex);
            T max = values.get(lastElementIndex);
            values.set(lastElementIndex,null);
            sinkDown(0);
            lastElementIndex--;
            return max;
        }
    }
    
    public boolean insertOne(T val){
        if(isFull()){
            return false;
        }
        lastElementIndex++;
        values.add(val);
        flowUp(lastElementIndex);
        return true;
    }
    
    public void flowUp(int index){
        while(!isRoot(index)){
            int parent = getParent(index);
            if(heapType == HeapType.MAX){
                if(values.get(index).compareTo(values.get(parent)) <= 0){
                    break;
                }
            }else{
                if(values.get(index).compareTo(values.get(parent)) >= 0){
                    break;
                }
            }
            swap(parent,index);
            index = parent;
        }
    }

	public void sinkDown(int x) {
		if (heapType == HeapType.MAX) {
            int rightIndex = getRightIndex(x);
            int leftIndex = getLeftIndex(x);
            int hitIndex = -1;
            if(leftIndex != -1){
                hitIndex = leftIndex;
                if(rightIndex != -1 && values.get(leftIndex).compareTo(values.get(rightIndex)) < 0){
                    hitIndex = rightIndex;
                }
                if(values.get(x).compareTo(values.get(hitIndex)) < 0){
                    swap(x,hitIndex);
                }
            }
		}else{
            int rightIndex = getRightIndex(x);
            int leftIndex = getLeftIndex(x);
            int hitIndex = -1;
            if(leftIndex != -1){
                hitIndex = leftIndex;
                if(rightIndex != -1 && values.get(leftIndex).compareTo(values.get(rightIndex)) > 0){
                    hitIndex = rightIndex;
                }
                if(values.get(x).compareTo(values.get(hitIndex)) > 0){
                    swap(x,hitIndex);
                }
            }
        }
	}
	
	private void swap(int x,int y){
	    T tmp = values.get(x);
        values.set(x,values.get(y));
        values.set(y,tmp);
    }

	private int getLeftIndex(int root){
	    int ret = -1;
        if(hasLeftChild(root)){
            ret = root * 2 - 1;
        }
        return ret;
    }
    
    private int getRightIndex(int root){
        int ret = -1;
        if(hasRightChild(root)){
            ret = root * 2;
        }
        return ret;
    }
    
    private int getParent(int c){
        return (c+1)/2-1;
    }

	private boolean isEmpty() {
		return lastElementIndex == -1;
	}

	private boolean isFull() {
		return lastElementIndex == maxSize - 1;
	}

	private boolean hasLeftChild(int root) {
		root++;
		return root * 2 - 1 <= lastElementIndex;
	}

	private boolean hasRightChild(int root) {
		root++;
		return root * 2 <= lastElementIndex;
	}

	private boolean isRoot(int x) {
		return x == 0;
	}
    
    public static void main(String[] args) {
        Heap<String> maxHeap = new Heap<>(5, HeapType.MAX);
        maxHeap.insertOne("a");
        maxHeap.insertOne("b");
        maxHeap.insertOne("c");
        maxHeap.insertOne("d");
        maxHeap.insertOne("e");
        String x;
        while((x = maxHeap.popTop()) != null){
            System.out.println(x);
        }
    }
}
