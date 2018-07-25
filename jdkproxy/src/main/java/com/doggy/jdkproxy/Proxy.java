package com.doggy.jdkproxy;

/**
 * Created by huangzhw on 2016/9/8.
 */
public class Proxy {
    public static void main(String[] args) {
        Inter inter = (Inter) java.lang.reflect.Proxy.newProxyInstance(Inter.class.getClassLoader(),new Class[]{Inter.class},(proxy, method, arguments) -> {
            System.out.println("nothing here");
            return null;
        });
        inter.work();
    }
}

interface Inter{
    void work();
}
