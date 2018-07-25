package com.doggy.lrucache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheByLinkedHashMap<K, V> {
    private final Map<K, V> cache;

    public LRUCacheByLinkedHashMap(int size){
        // 这里使用访问更新的模式，每次put/get时，会将整个Entry提到双链表的表头。
        // 默认的模式为put更新，即只有put操作会将元素放到表头。
        cache = new LinkedHashMap<K, V>(32, 0.75f,true){
            // 该方法会在put方法调用时去判断是否需要删除最老的元素，返回true则删除
            @Override
            protected boolean removeEldestEntry(Map.Entry eldest) {
                return size() > size;
            }
        };
    }

    public V put(K k, V v){
        return cache.put(k, v);
    }

    public V get(K k){
        return cache.get(k);
    }

}
