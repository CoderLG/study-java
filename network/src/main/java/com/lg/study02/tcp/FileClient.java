package com.lg.study02.tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 上传文件
 * 创建客户端
 * 1、建立连接: 使用Socket创建客户端 +服务的地址和端口
 * 2、操作: 输入输出流操作
 * 3、释放资源 
 * @author 裴新 QQ:3401997271
 *
 */
public class FileClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		System.out.println("-----Client-----");
		//1、建立连接: 使用Socket创建客户端 +服务的地址和端口
		Socket client =new Socket("localhost",8888);
		//2、操作: 拷贝 上传
		InputStream is =new BufferedInputStream(new FileInputStream("src/logo.png"));
		OutputStream os =new BufferedOutputStream(client.getOutputStream());
		byte[] flush =new byte[1024];
		int len = -1;
		while((len=is.read(flush))!=-1) {
            os.write(flush,0,len);
        }
		os.flush();
		//3、释放资源 
		os.close();
		is.close();
		client.close();
	}

}
