package com.lg.study01;

import java.net.InetSocketAddress;
/**
 * 端口
 * 1、区分软件
 * 2、2个字节 0-65535  UDP TCP
 * 3、同一个协议端口不能冲突
 * 4、定义端口越大越好
 * InetSocketAddress 
 * 1、构造器
 *   new InetSocketAddress(地址|域名,端口);
 * 2、方法
 *  getAddress​()  
 *  getPort()
 *  getHostName()
 * @author 裴新 QQ:3401997271
 *
 */
public class PortTest {

	public static void main(String[] args) {
		//包含端口
		InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",8080);
		InetSocketAddress socketAddress2 = new InetSocketAddress("localhost",9000);
		System.out.println(socketAddress.getHostName());
		System.out.println(socketAddress.getAddress());
		System.out.println(socketAddress2.getAddress());
		System.out.println(socketAddress2.getPort());

	}

}
