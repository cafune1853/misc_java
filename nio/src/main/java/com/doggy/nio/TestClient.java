package com.doggy.nio;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.concurrent.LinkedBlockingQueue;

public class TestClient {
	private static LinkedBlockingQueue<Long> timeDelay = new LinkedBlockingQueue<Long>();
	private static ArrayList<Long> timeSort = new ArrayList<Long>();
	private static final int MAX_RECORD_CON = 100000;
	private static final int PORT = 9999;
	private static final String IP = "127.0.0.1";
	private static int count = 0;
	private static Calendar cs;
	private static boolean flag = true;
	public static void main(String[] args){
		Thread record = new Thread(() -> {
			cs = Calendar.getInstance();
			while(true){
				try {
					Long temp = timeDelay.take();
					timeSort.add(temp);
					if(timeSort.size() >= MAX_RECORD_CON){
						Calendar ce = Calendar.getInstance();
						Collections.sort(timeSort);
						File f = new File("Record.txt");
						PrintWriter pw = new PrintWriter(f);
						pw.println("并发数目:"+timeSort.size());
						pw.println("并发时间:"+(ce.getTimeInMillis() - cs.getTimeInMillis())+"ms");
						pw.println("最大时延:"+Collections.max(timeSort)+"ms");
						for(int i = 0;i < timeSort.size();++i){
							pw.println(timeSort.get(i));
						}
						pw.close();
						System.exit(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		record.start();
		while(flag){
			Thread t = new Thread(() ->{
					try {
						SocketChannel sc = SocketChannel.open();
						sc.connect(new InetSocketAddress(IP, 9999));
						ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
						Calendar calStart = Calendar.getInstance();
						sc.write(bb);
						bb.clear();
						sc.read(bb);
						bb.flip();
						sc.write(bb);
						bb.clear();
						int back = sc.read(bb);
						printMessage(back);
						Calendar calEnd = Calendar.getInstance();
						long delay = calEnd.getTimeInMillis() - calStart.getTimeInMillis();
						timeDelay.add(delay);
						sc.close();
					} catch (IOException e) {
						if(flag){
							flag = false;
							dealException();
							e.printStackTrace();
							System.exit(0);
						}
					}
				});
			t.start();
		}
	}
	private static synchronized void printMessage(int back){
		count++;
		System.out.println("BACK_NUM:"+count+"&&&RETURN_SIZE:"+back);
	}
	private static synchronized void dealException(){
		Calendar ce = Calendar.getInstance();
		Collections.sort(timeSort);
		System.out.println("并发数目:"+timeSort.size());
		System.out.println("并发时间:"+(ce.getTimeInMillis() - cs.getTimeInMillis())+"ms");
		System.out.println("最大时延:"+Collections.max(timeSort)+"ms");
		for(int i = 0;i < timeSort.size();++i){
			System.out.println(timeSort.get(i));
		}
	}
}
