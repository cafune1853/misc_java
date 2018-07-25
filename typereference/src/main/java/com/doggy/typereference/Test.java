package com.doggy.typereference;

import java.lang.reflect.ParameterizedType;

public class Test {
    public static void main(String[] args) {
        System.out.println(X1.class.getGenericSuperclass().getClass());
        System.out.println(X2.class.getGenericSuperclass().getClass());
        System.out.println(((ParameterizedType)(X2.class.getGenericSuperclass())).getActualTypeArguments()[0].getClass());
        System.out.println(X3.class.getGenericSuperclass().getClass());
        System.out.println(((ParameterizedType)(X3.class.getGenericSuperclass())).getActualTypeArguments()[0].getClass());
    }

    private static class X1 extends TypeReference{}
    private static class X2 extends TypeReference<String>{}
    private static class X3<T> extends TypeReference<T>{}
}
