package com.lg.study01;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP:定位一个节点:计算机、路由、通讯设备等
 * InetAddress: 多个静态方法
 * 1、getLocalHost:本机
 * 2、getByName:根据域名DNS |  IP地址 -->IP
 * 
 * 两个成员方法
 * 1、getHostAddress: 返回地址
 * 2、getHostName:返回计算机名
 * @author 裴新 QQ:3401997271 
 *
 */
public class IPTest {

	public static void main(String[] args) throws UnknownHostException {
		//使用getLocalHost方法创建InetAddress对象  本机
		InetAddress addr = InetAddress.getLocalHost();
		System.out.println(addr.getHostAddress());  //返回：192.168.1.110
		System.out.println(addr.getHostName());  //输出计算机名
		
		//根据域名得到InetAddress对象
		addr = InetAddress.getByName("www.shsxt.com"); 
		System.out.println(addr.getHostAddress());  //返回 shsxt服务器的ip:
		System.out.println(addr.getHostName());  //输出：www.shsxt.com
		
		//根据ip得到InetAddress对象
		addr = InetAddress.getByName("123.56.138.176"); 
		System.out.println(addr.getHostAddress());  //返回 shsxt的ip:123.56.138.176
		System.out.println(addr.getHostName());  //输出ip而不是域名。如果这个IP地 址不存在或DNS服务器不允许进行IP地址和域名的映射，

				


	}

}
