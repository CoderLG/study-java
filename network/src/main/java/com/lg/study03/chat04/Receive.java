package com.lg.study03.chat04;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 使用多线程封装:接收端
*  1、接收消息
 * 2、释放资源
 * 3、重写run
 * @author 裴新 QQ:3401997271
 *
 */
public class Receive implements Runnable {
	private DataInputStream dis ;
	private Socket client;
	private boolean isRunning;
	public Receive(Socket client) {
		this.client = client;
		this.isRunning = true;
		try {
			dis =new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			System.out.println("====2=====");
			release();
		}
	}
	//接收消息
	private String receive() {
		String msg ="";
		try {
			msg =dis.readUTF();
		} catch (IOException e) {
			System.out.println("====4====");
			release();
		}
		return msg;
	}
	
	@Override
	public void run() {		
		while(isRunning) {
			String msg =receive();
			if(!msg.equals("")) {
				System.out.println(msg);
			}
		}
	}
	//释放资源
	private void release() {
		this.isRunning = false;
		SxtUtils.close(dis,client);
	}

}
