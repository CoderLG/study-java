package com.ceph.objectGateway.test;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;
import java.net.URL;
import java.util.List;

public class TestCeph {
    private static AmazonS3 amazonS3 = null;
    static final String hosts = "10.116.3.2:7480";
    static final String accessKey = "A7V3Z6265NUWS8KHBI0S";
    static final String secretKey = "N8Q1D0aUaajzyNzKHjL3FZO0q4b4dZBsKc1EO6UC";
    static final String bucketName = "mon2-bucket-test2";   //桶名称,不能包含大写字符，详情请看规范
    public static void main(String[] args) {
        testClient(hosts,accessKey,secretKey);
        testCreateBucket( bucketName);
        testListBucket();
        multipartUploadUsingHighLevelAPI();
    }

    //测试连接
    public static void testClient(final String hosts,final String accessKey,final String secretKey){
        AWSCredentialsProvider credentialsProvider = new AWSCredentialsProvider() {
            public AWSCredentials getCredentials() {
                return new BasicAWSCredentials(accessKey, secretKey);
            }

            public void refresh() {
            }
        };
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProtocol(Protocol.HTTP);
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(hosts, null);
        amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider)
                .withEndpointConfiguration(endpointConfiguration)
                .withClientConfiguration(clientConfiguration).build();
        System.out.println("ceph client init success!");
    }

    //测试创建 bucket
    public static void testCreateBucket(String bucketName){
        Bucket bucket1 = amazonS3.createBucket(bucketName);
    }

    //测试遍历当前用户的所有 bucket
    public static void testListBucket(){
        List<Bucket> buckets = amazonS3.listBuckets();
        for (Bucket bucket : buckets) {
            //amazonS3.deleteBucket(bucket.getName());
            System.out.println(bucket.getName() + "\t" + com.amazonaws.util.StringUtils.fromDate(bucket.getCreationDate()));
        }
    }

    //测试上传
    public static void multipartUploadUsingHighLevelAPI() {

        String filePath = "C://Users/linj/Desktop/temp/testcephupload.txt";
        //String bucketName = "my-new-bucket3";
        String keyName = "testcephupload";
        TransferManager tm = TransferManagerBuilder.standard()
                .withMultipartUploadThreshold(5L).withS3Client(amazonS3).build();
        System.out.println("start uploading...");
        long start = System.currentTimeMillis();
        Upload upload =
                tm.upload(
                        bucketName, keyName, new File(filePath));
        System.out.println("asynchronously return ...go on other opration");

        try {
            upload.waitForCompletion();
            System.out.println("Upload complete.");
            System.out.println("文件描述：" + upload.getDescription());
            System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, keyName);
            URL url = amazonS3.generatePresignedUrl(request);
            System.out.println("下载地址：" + url);
        } catch (AmazonClientException e) {
            System.out.println("Unable to upload file, upload was aborted.");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Unable to upload file, upload was aborted.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
