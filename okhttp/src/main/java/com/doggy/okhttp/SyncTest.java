package com.doggy.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by huangzhw on 2017/4/6.
 */

public class SyncTest {
	public static void main(String[] args) throws Exception{
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().url("http://www.baidu.com").build();
		Response response = client.newCall(request).execute();
		System.out.println(response.isSuccessful());
		System.out.println(response.body().string());
		System.out.println(response.message());
		System.out.println(response.code());
	}
}
