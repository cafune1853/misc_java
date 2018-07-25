package com.doggy.jsonserialize;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

public class Test {
	public static void main(String[] args) throws Exception {
		Person person = new Person("doggy", 23, Person.E.E2);
		/**
		 * Gson序列化最为宽松，不要求类的可访问性以及不要求类提供可访问的构造器（通过调用setAccessible绕过）
		 * 也不需要getter && setter方法
		 */
		Gson gson = new Gson();
		System.out.println(gson.toJson(person));
		System.out.println(gson.fromJson("{\"name\":\"doggy\",\"age\":23,\"e\":\"2\"}", Person.class));
		
		/**
		 * 使用FastJson序列化时，必须有get方法，可以指定SerializerFeature.IgnoreNonFieldGetter，对于那些没有相应域的get方法则不序列化
		 * 使用FastJson进行反序列化时，要求被反序列化的类为public.
		 * 如果存在多个构造器，则调用第一个构造器(构造器可为private)
		 * 然后再调用相应域的setter方法设置相应的域 （所以对于那些不能在构造器中初始化的域，必须提供setter方法）
		 */
		System.out.println(JSON.toJSONString(new Person("doggy", 23, Person.E.E2), SerializerFeature.IgnoreNonFieldGetter));
		System.out.println(JSON.parseObject("{\"age\":23,\"e\":\"E2\",\"name\":\"doggy\"}", Person.class));
		
		/**
		 * 使用Jackson序列化时必须提供getter方法
		 * 反序列化时必须提供一个空构造器（空构造器可以为private，类可以为private）可以没有setter方法
		 */
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.println(objectMapper.writeValueAsString(person));
		System.out.println(objectMapper.readValue("{\"name\":\"doggy\",\"age\":23,\"e\":\"E2\"}", Person.class));
	}
	
	@Getter
	@ToString
	private static class Person {
		private String name;
		private int age;
		private E e;
		
		public Person(String name, int age, E e) {
			this.name = name;
			this.age = age;
			this.e = e;
		}
		
		private enum E {
			//Gson对于枚举可以直接这样用于替代枚举名称
			@SerializedName("1")
			E1(1),
			@SerializedName("2")
			E2(2),
			@SerializedName("3")
			E3(3);
			
			private int type;
			
			E(int type) {
				this.type = type;
			}
		}
	}
}
