package com.doggy.okhttp;

import java.io.IOException;

import okhttp3.*;

/**
 * Created by huangzhw on 2017/4/7.
 */

public class AsyncTest {
	public static void main(String[] args) throws Exception{
		OkHttpClient client = new OkHttpClient.Builder().build();
		Request request = new Request.Builder().url("https://www.baidu.com").build();
		System.out.println(request.headers());
		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				
			}
			
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				System.out.println(response.code());
				System.out.println(response.body().charStream());
				System.out.println(response.headers());
			}
		});
	}
}
