package com.doggy.byteserialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.ToString;

public class Main {
	
	public static void main(String[] args) throws Exception {
		Person person = new Person(18, "doggy");
		protoStuff(person);
	}
	
	/**
	 * proto stuff protobuf的java类库
	 * 被序列化的类不必实现Serializable接口
	 */
	private static void protoStuff(Person person) {
		Schema<Person> schema = RuntimeSchema.getSchema(Person.class);
		byte[] bs = ProtostuffIOUtil.toByteArray(person, schema, LinkedBuffer.allocate(2048));
		Person p = schema.newMessage();
		ProtobufIOUtil.mergeFrom(bs, p, schema);
		System.out.println(p);
	}
	
	/**
	 * hessian序列化 是dubbo的默认序列化方式
	 * 需要被序列化的类实现Serializable接口
	 */
	private static void hessian2(Person person) throws IOException {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			Hessian2Output ho = new Hessian2Output(baos);
			ho.writeObject(person);
			ho.flush();
			byte[] tmps = baos.toByteArray();
			System.out.println(tmps.length);
			try (ByteArrayInputStream bais = new ByteArrayInputStream(tmps)) {
				Hessian2Input hi = new Hessian2Input(bais);
				Person personX = (Person) hi.readObject();
				System.out.println(personX);
			}
		}
	}
	
	@ToString
	private static final class Person {
		private static final long serialVersionUID = 1L;
		private int age;
		private String name;
		
		private Person(int age, String name) {
			this.age = age;
			this.name = name;
		}
	}
}
