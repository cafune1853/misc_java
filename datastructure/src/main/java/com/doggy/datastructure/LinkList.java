package com.doggy.datastructure;

/**
 * @author doggy1853
 */
public class LinkList<T>{
    private Node<T> head = null;
    private Node<T> tail = null;

    public void add(Node<T> node){
        if(tail == null){
            head = tail = node;
        }else{
            tail.next = node;
            tail = node;
        }
    }

    public Node<T> removeLast(){
        Node<T> pre = null;
        Node cur = head;
        while (cur != null){
            if(cur == tail){
                if(cur == head){
                    tail = head = null;
                }else {
                    tail = pre;
                    tail.next = null;
                    return cur;
                }
            }else{
                pre = cur;
                cur = cur.next;
            }
        }
        return null;
    }

    public void print(){
        Node cur = head;
        while (cur != null){
            System.out.println(cur.t);
            cur = cur.next;
        }
    }

    public void reverse(){
        Node<T> pre = null;
        Node<T> cur = head;
        Node<T> next = head == null ? null : head.next;
        while (cur != null){
            cur.next = pre;
            pre = cur;
            cur = next;
            if(next != null){
                next = next.next;
            }
        }
        tail = head;
        head = pre;
    }

    public boolean containsCircle(){
        Node<T> runner1 = head;
        Node<T> runner2 = head;
        while (runner2 != null && runner2.next != null && runner2.next.next != null){
            runner1 = runner1.next;
            runner2 = runner2.next.next;
            if(runner1 == runner2) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        LinkList<String> linkList = new LinkList<>();
        Node<String> A = new Node<>("A", null);
        Node<String> B = new Node<>("B", null);
        Node<String> C = new Node<>("C", null);
        Node<String> D = new Node<>("D", null);
        linkList.add(A);
        linkList.add(B);
        linkList.add(C);
        linkList.add(D);

        // reverse
        linkList.print();
        linkList.reverse();
        linkList.print();

        // circle check
        System.out.println(linkList.containsCircle());
        linkList.add(new Node<>("E", B));
        System.out.println(linkList.containsCircle());

        linkList.removeLast();

        linkList.print();

    }

    private static class Node<T>{
        private T t;
        private Node next;

        public Node() {
        }

        public Node(T t, Node next) {
            this.t = t;
            this.next = next;
        }
    }
}
