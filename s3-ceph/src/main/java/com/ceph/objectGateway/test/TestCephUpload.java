package com.ceph.objectGateway.test;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.io.File;
import java.net.URL;

public class TestCephUpload {
    //密钥
    static String hosts = "10.116.3.2:7480";
    static String accessKey = "A7V3Z6265NUWS8KHBI0S";
    static String secretKey = "N8Q1D0aUaajzyNzKHjL3FZO0q4b4dZBsKc1EO6UC";
    static String bucketName = "mon2-bucket-test";   //桶名称
    static String key = "testcephupload";   //object名称
    static String filepath = "C://Users/linj/Desktop/temp/testcephupload.txt"; //上传文件路径


    public static void main(String[] args) throws Exception {
        //初始化
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);

        //连接服务器
        AmazonS3 conn = new AmazonS3Client(credentials,clientConfig);

        conn.setEndpoint(hosts);  //测试接口ip

        conn.getBucketLocation( bucketName);

       /* conn.putObject(bucketName, key, new File(filepath));

        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key);
        URL url = conn.generatePresignedUrl(request);
        System.out.println("上传成功，文件下载网络地址：" + url);*/
    }
}
