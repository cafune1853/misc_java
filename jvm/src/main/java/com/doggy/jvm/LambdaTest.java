package com.doggy.jvm;

import java.util.function.Function;

/**
 * @author doggy1853
 */
public class LambdaTest {
	// debug for metaFatory
	public static void main(String[] args) {
		Function<String, String> function = x -> x + "x";
		System.out.println(function.apply("xx"));
	}
}
