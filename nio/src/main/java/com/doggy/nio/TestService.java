package com.doggy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

enum Constant{
	CHANNEL_NUM(100),PORT(9999);
	private int size;
	Constant(int size){
		this.size = size;
	}
	public int getSize(){
		return size;
	}
}

public class TestService {
	private static ServerSocketChannel scc;
	private static Selector current;
	private static int count = 0;
	public static void main(String[] args){
		try{
				scc = ServerSocketChannel.open();
				scc.socket().bind(new InetSocketAddress(Constant.PORT.getSize()));
				while(true){
					SocketChannel socketChannel = scc.accept();
					socketChannel.configureBlocking(false);
					if(count == 0){
							current = Selector.open();
							socketChannel.register(current, SelectionKey.OP_READ);
							Thread t = new Thread(new SelectorRunnable(current));
							t.start();
					}else{
						socketChannel.register(current, SelectionKey.OP_READ);
					}
					count = (count + 1)%Constant.CHANNEL_NUM.getSize();
				}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}

class SelectorRunnable implements Runnable{
	private Selector selector;
	private int leftChannel = Constant.CHANNEL_NUM.getSize();
	SelectorRunnable(final Selector selector){
		this.selector = selector;
	}
	@Override
	public void run() {
		System.out.println("Thread Created");
			try {
		            while(true){
					int num = selector.selectNow();
					if(num == 0){
						if(leftChannel == 0)
							break;
						 continue;
					}
					Set<SelectionKey> keys = selector.selectedKeys();
					Iterator<SelectionKey> ite = keys.iterator();
					while(ite.hasNext()){
						SelectionKey sk = ite.next();
						ite.remove();
						//处理读事件
						if(sk.isReadable()){
							ByteBuffer bb = ByteBuffer.allocate(16);
							SocketChannel sc = (SocketChannel) sk.channel();
							int x = sc.read(bb);
							if(x ==  -1){
								leftChannel--;
								sc.close();
								continue;
							}
							bb.flip();
							//可能阻塞，需要转发等
							//TODO
							sc.write(bb);
							bb.clear();
						}
					}
				}
		     selector.close();
			System.out.println("Thread break");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}
