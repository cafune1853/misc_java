package com.doggy.jvm.invokedynamic;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {
    // super class Here
    public static class Human{
        public static String getSimpleClassName(){
            return "Human";
        }

        public String speak(Object word){
            return "Human speak:" + word;
        }
    }

    // subclass here
    public static class Man extends Human{
        @Override
        public String speak(Object word){
            return "Man speak:" + word;
        }
    }


    public static void main(String[] args) throws Throwable{
        testInvokeStatic();
        testInvokeVirtual();
    }

    public static void testInvokeStatic() throws Throwable{
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(String.class);
        MethodHandle mh = lookup.findStatic(Man.class, "getSimpleClassName", mt);
        System.out.println(mh.invoke());
    }

    public static void testInvokeVirtual() throws Throwable{
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(String.class, Object.class);
        MethodHandle mh = lookup.findVirtual(Human.class, "speak", mt).bindTo(new Man());
        System.out.println(mh.invoke("haha"));
    }
}
