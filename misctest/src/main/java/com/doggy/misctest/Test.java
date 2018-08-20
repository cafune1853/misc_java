package com.doggy.misctest;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author doggy1853
 */
public class Test {
	public static void main(String[] args) throws Exception{
		ClassLoader myLoader = new ClassLoader() {
			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException {
				try {
					//String fileName = "/" + name.replace('.', '/') + ".class";
					String fileName = name.substring(name.lastIndexOf('.') + 1) + ".class";
					InputStream is = getClass().getResourceAsStream(fileName);
					if(is == null){
						return super.loadClass(name);
					}
					byte[] b = new byte[is.available()];
					is.read(b);
					return defineClass(name, b, 0, b.length);
				}catch (IOException ioe){
					throw new ClassNotFoundException(name);
				}
			}
		};
		Object testObj = myLoader.loadClass("com.doggy.misctest.TestObj").newInstance();
		System.out.println(testObj.getClass());
		System.out.println(testObj instanceof com.doggy.misctest.TestObj);
	}
}
