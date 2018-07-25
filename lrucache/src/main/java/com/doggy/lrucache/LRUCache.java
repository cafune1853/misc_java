package com.doggy.lrucache;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * Created by huangzhw on 2016/8/18.
 */
public class LRUCache<K, V> {
	private Map<K, Entry<V>> maps = new HashMap<>();
	private Entry<V> head;
	private Entry<V> tail;
	private final int cap;
    private int cur;

	public LRUCache(int cap) {
		this.cap = cap;
	}

	@Data
	private static class Entry<V> {
		private V value;
		private Entry previous;
		private Entry next;
	}

	public void put(K k, V v) {
        while(cur >= cap){
            removeLast();
            cur--;
        }
        Entry<V> entry = maps.get(k);
        if(entry != null){
            moveToHead(entry);
            entry.value = v;
        }else{
            Entry<V> e = new Entry<>();
            e.setValue(v);
            if(head != null){
                head.previous = e;
            }
            e.next = head;
            head = e;
            e.previous = null;
            if(tail == null){
                tail = e;
            }
            maps.put(k,e);
            cur++;
        }
	}
	
	public V get(K k){
	    Entry<V> v = maps.get(k);
        if(v != null){
            moveToHead(v);
        }
        return v == null?null:v.getValue();
    }
	
	public void removeLast(){
	    if(tail != null){
            System.out.println("Remove value:"+tail.getValue());
            tail = tail.previous;
            if(tail != null){
                tail.next = null;
            }
        }
    }

	public void moveToHead(Entry<V> entry) {
		if (entry == head){
            return;
        }
        //entry.previous != null
        if (entry == tail){
            tail = entry.previous;
        }
		entry.previous.next = entry.next;
		if (entry.next != null) {
			entry.next.previous = entry.previous;
		}
		entry.previous = null;
		entry.next = head;
		if (head != null) {
			head.previous = entry;
		}
		head = entry;
	}
	
	public void prtLRU(){
	    Entry c = head;
        while(c != null){
            System.out.println(c.getValue());
            c = c.next;
        }
    }
    
    public static void main(String[] args) {
        LRUCache<String,String> cache = new LRUCache<>(5);
        cache.put("s","s");
        cache.put("k","k");
        cache.put("o","o");
        cache.put("j","j");
        cache.put("k","k");
        cache.put("o","o");
        cache.put("h","h");
        cache.put("g","g");
        cache.put("d","d");
    }
}
