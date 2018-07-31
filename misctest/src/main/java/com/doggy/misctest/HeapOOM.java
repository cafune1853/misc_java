package com.doggy.misctest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author doggy1853
 * VM args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/d/heap-dump/java.dump
 */
public class HeapOOM {
    static class OOMObject {
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            List<OOMObject> list = new ArrayList<>();
            while (true) {
                list.add(new OOMObject());
            }
        });

        Thread t2 = new Thread(() -> {
            List<OOMObject> list = new ArrayList<>();
            while (true) {
                list.add(new OOMObject());
            }
        });

        t1.start();
        t2.start();
    }
}
