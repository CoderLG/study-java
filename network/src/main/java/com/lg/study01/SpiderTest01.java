package com.lg.study01;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * 网络爬虫的原理
 * 
 * @author 裴新 QQ:3401997271
 *
 */
public class SpiderTest01 {

	public static void main(String[] args) throws Exception {
		//获取URL
		URL url =new URL("https://www.jd.com");
		//URL url =new URL("https://www.dianping.com");
		//下载资源
		InputStream is = url.openStream();
		BufferedReader br =new BufferedReader(new InputStreamReader(is,"UTF-8"));
		String msg =null;
		while(null!=(msg=br.readLine())) {
			System.out.println(msg);
		}
		br.close();
		//分析
		//处理。。。。
	}

}
