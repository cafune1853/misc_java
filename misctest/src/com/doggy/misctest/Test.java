package com.doggy.misctest;

public class Test {
    public static void main(String[] args) throws Exception {
        S s = new S();
        System.out.println(s);
    }
    
    private static class F{
        protected String ff = "f";
    }
    
    private static class S extends F{
        protected String ff = "s";
    }
}
