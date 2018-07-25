package com.doggy.concurrency;

        import java.util.concurrent.atomic.AtomicReference;

/**
 * @author doggy
 * Created on 2017-09-07.
 */
public class SyncStackOnCAS<E> {
    private final AtomicReference<Node<E>> top;

    public SyncStackOnCAS() {
        top = new AtomicReference<>(null);
    }

    public void push(E e){
        while(true){
            Node<E> currentTop = top.get();
            Node<E> newNode = new Node<>(e, currentTop);
            if(top.compareAndSet(currentTop, newNode)){
                return;
            }
        }
    }

    private static class Node<E>{
        private final E e;
        private final Node<E> next;

        private Node(E e, Node<E> next) {
            this.e = e;
            this.next = next;
        }
    }
}
