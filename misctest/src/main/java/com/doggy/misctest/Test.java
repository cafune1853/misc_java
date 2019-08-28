package com.doggy.misctest;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author doggy1853
 */
public class Test {
	public static void main(String[] args) throws Exception{


		InetSocketAddress inetSocketAddress = new InetSocketAddress(18040);
		try (DatagramSocket datagramSocket = new DatagramSocket(inetSocketAddress)) {
			InetSocketAddress sendAddr = new InetSocketAddress("10.22.3.1", 300001);
			DatagramPacket datagramPacket = new DatagramPacket("xdsfrds".getBytes(), "xdsfrds".getBytes().length, sendAddr);
			datagramSocket.send(datagramPacket);
		}
	}
}
