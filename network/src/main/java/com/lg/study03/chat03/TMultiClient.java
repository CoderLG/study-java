package com.lg.study03.chat03;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 在线聊天室: 客户端
 * 目标: 封装使用多线程实现多个客户可以正常收发多条消息 
 * @author 裴新 QQ:3401997271
 *
 */
public class TMultiClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("-----Client-----");
		//1、建立连接: 使用Socket创建客户端 +服务的地址和端口
		Socket client =new Socket("localhost",8888);
		//2、客户端发送消息
		new Thread(new Send(client)).start();  
		new Thread(new Receive(client)).start();
	}
}
