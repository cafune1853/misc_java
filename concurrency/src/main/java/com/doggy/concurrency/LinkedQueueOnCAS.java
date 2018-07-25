package com.doggy.concurrency;


import java.util.concurrent.atomic.AtomicReference;

public class LinkedQueueOnCAS<E> {
	private final Node<E> dummy = new Node<>(null, null);
	private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
	private AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);
	
	public void put(E e){
		Node<E> node = new Node(e, null);
		while (true){
			Node<E> curTailNode = tail.get();
			//检测到当前的curTailNode.next.get不为空,表示上一个元素的插入并未完成,所以在这里先尝试恢复下状态
			if(curTailNode.next.get() != null){
				tail.compareAndSet(curTailNode, curTailNode.next.get());
			}else{
				if(curTailNode.next.compareAndSet(null, node)){
					tail.compareAndSet(curTailNode, curTailNode.next.get());
					return;
				}
			}
		}
	}
	
	private static class Node<E>{
		private final E e;
		private final AtomicReference<Node<E>> next;
		
		private Node(E e, Node<E> next) {
			this.e = e;
			this.next = new AtomicReference<>(next);
		}
	}
}
