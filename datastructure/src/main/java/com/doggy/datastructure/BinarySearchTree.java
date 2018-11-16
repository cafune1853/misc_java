package com.doggy.datastructure;

import java.util.ArrayList;
import java.util.List;

/**
 * @author doggy1853
 */
public class BinarySearchTree<K extends Comparable<? super K>, V> {
    public static void main(String[] args) {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.insert(1, "1v");
        bst.insert(7, "7v");
        bst.insert(9, "9v");
        bst.insert(2, "2v");
        System.out.println(bst.ascListKeys());
        System.out.println(bst.get(8) == null);
        System.out.println(bst.get(7).equals("7v"));
    }

    private Entry<K, V> root = null;

    public void insert(K key, V value){
        Entry<K,V> parent = doFindParentNode(key);
        if(parent == null){
            root = new Entry<>(key, value, null, null, null);
            return;
        }
        if(parent.key.compareTo(key) > 0){
            parent.left = new Entry<>(key,value, parent, null, null);
        }else{
            parent.right = new Entry<>(key, value, parent, null, null);
        }
    }

    public void delete(K key){
        Entry<K, V> entry = doFindEntry(key);
        if(entry == null){
            return;
        }
        if(entry.parent == null){
            if(entry.left == null && entry.right == null){
                root = null;
            }
            if(entry.left == null && entry.right != null){
                root = root.right;
                root.parent = null;
            }
            if(entry.left != null && entry.right == null){
                root = root.left;
                root.parent = null;
            }
            if(entry.left != null && entry.right != null){

            }
        }
    }

    private Entry<K,V> doFindParentNode(K key){
        Entry<K, V> parent = null;
        Entry<K, V> current = root;
        while (current != null){
            int result = current.key.compareTo(key);
            if(result == 0){
                throw new IllegalArgumentException("Not permit to have same element.");
            }else if(result < 0){
                parent = current;
                current = current.right;
            }else{
                parent = current;
                current = current.left;
            }
        }
        return parent;
    }

    public V get(K key){
        Entry<K, V> entry = doFindEntry(key);
        return entry == null ? null : entry.value;
    }

    private Entry<K, V> doFindEntry(K key){
        Entry<K, V> current = root;
        while (current != null){
            int result = current.key.compareTo(key);
            if(result == 0){
                return current;
            }else if(result < 0){
                current = current.right;
            }else{
                current = current.left;
            }
        }
        return null;
    }

    public List<K> ascListKeys(){
        List<K> result = new ArrayList<>();
        doMiddleAddNodeToList(result, root);
        return result;
    }

    private void doMiddleAddNodeToList(List<K> list, Entry<K, V> root){
        if(root != null){
            if(root.left != null){
                doMiddleAddNodeToList(list, root.left);
            }
            list.add(root.key);
            if(root.right != null){
                doMiddleAddNodeToList(list, root.right);
            }
        }
    }

    private static class Entry<K extends Comparable<? super K>, V>{
        private K key;
        private V value;
        private Entry<K, V> parent;
        private Entry<K, V> left;
        private Entry<K, V> right;

        public Entry(K key, V value, Entry<K, V> parent, Entry<K, V> left, Entry<K, V> right) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }
}
