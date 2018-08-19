package com.doggy.misctest;

public class StackOverflow {
    public static void main(String[] args) throws Exception {
        System.out.println(ss());
    }

    public static int ss(){
        int a = 0;
        try{
            a = 1;
            // 将操作数1 置于返回区
            return a;
        }finally{
            a = 2;
            // 将操作数2 置于返回区
        }
    }
}
