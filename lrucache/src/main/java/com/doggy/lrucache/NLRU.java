package com.doggy.lrucache;

/**
 * Created by huangzhw on 2016/9/7.
 */

import java.util.HashMap;
import java.util.Map;

public class NLRU<K,V>{
    private Node<K,V> head = null;
    private Node<K,V> tail = null;
    private int cap = Integer.MAX_VALUE;
    private int cur = 0;
    private Map<K,Node<K,V>> locateMap = new HashMap<>();
    
    public NLRU(int cap){
        this.cap = cap;
    }
    
    private static class Node<K,V>{
        private K key;
        private V value;
        private Node pre;
        private Node next;
    }
    //void put(K k,V v),If full remove if.
    
    public boolean put(K k,V v){
        if(k == null)
            throw new RuntimeException("sd");
        Node node = locateMap.get(k);
        if(node == null){
            if(full()){
                removeOne();
            }
            cur++;
            node = new Node();
            node.key = k;
            node.value = v;
            if(head == null){
                head = node;
                tail = node;
            }else{
                node.next = head;
                head.pre = node;
                head = node;
                head.pre = null;
            }
            locateMap.put(k,node);
        }else{
            node.value = v;
            moveNodeToHead(node);
        }
        return true;
    }
    
    public V get(K k){
        Node<K,V> node = locateMap.get(k);
        if(node == null)
            return null;
        else{
            moveNodeToHead(node);
            return node.value;
        }
    }
    
    private void moveNodeToHead(Node node){
        if(node == null) throw new IllegalArgumentException("Not null");
        if(node == head){
            return ;
        }else if(node == tail){
            tail = node.pre;
            tail.next = null;
            node.next = head;
            head.pre = node;
            head = node;
            head.pre = null;
        }else{
            Node p = node.pre;
            Node n = node.next;
            p.next = n;
            n.pre = p;
            node.next = head;
            head.pre = node;
            head = node;
            head.pre = null;
        }
    }
    private boolean full(){
        return cur >= cap;
    }
    private void removeOne(){
        cur--;
        if(tail == head){
            head = null;
            tail = null;
        }else{
            K key = tail.key;
            locateMap.remove(key);
            tail = tail.pre;
            tail.next = null;
        }
    }
    public static void main(String[] args) {
        NLRU<String,String> cache = new NLRU<>(5);
        cache.put("s","s");
        cache.put("k","k");
        cache.put("o","o");
        cache.put("j","j");
        System.out.println(cache);
        cache.put("k","k");
        System.out.println(cache);
        cache.put("o","o");
        System.out.println(cache);
        cache.put("h","h");
        System.out.println(cache);
        cache.put("g","g");
        System.out.println(cache);
        cache.put("d","d");
        System.out.println(cache);
    }
    
    @Override
    public String toString() {
        String s = "";
        Node cur = head;
        while(cur!= null){
            s += cur.value+" -> ";
            cur = cur.next;
        }
        return s;
    }
}
