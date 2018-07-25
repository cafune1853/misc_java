package com.doggy.jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by huangzhw on 2016/9/8.
 */
public class HotSwapTest {
    public static void main(String[] args) throws Exception{
        while(true){
            HotSwapClassLoader hotSwapClassLoader = new HotSwapClassLoader();
            //所有在M类中所用到的类，都会通过HotSwapClassLoader进行加载
            //加载时先调用loadClass(在LoadClass中定义，默认支持双亲委派)，使用父加载器向上加载。
            //只有所有父加载器都加载失败时，才调用findClass加载并生成类！！！
            //由于双亲委派的存在，所以对于自加载的类可以通过反射使用，也可以通过接口使用（接口类会由SystemClassLoader加载），在子类中加载接口时，会返回SystemClassLoader加载的对象。
            Class<?> cls = hotSwapClassLoader.findClass("M");
            Object target = cls.newInstance();
            cls.getDeclaredMethod("prt").invoke(target);
            Thread.sleep(5000);
        }
    }
    private static class HotSwapClassLoader extends ClassLoader{
    
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            File f = new File("D:\\M.class");
            long size = f.length();
            byte[] bs = new byte[(int)size];
            try(FileInputStream fins = new FileInputStream(f)){
                fins.read(bs);
            }catch (IOException ie){
                System.out.println(ie);
            }
            return defineClass(name, bs, 0, (int)size);
        }
    }
}
