package com.doggy.misctest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author doggy1853
 * VM args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {
	static class OOMObject{}
	
	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<>();
		while (true){
			list.add(new OOMObject());
		}
	}
}
