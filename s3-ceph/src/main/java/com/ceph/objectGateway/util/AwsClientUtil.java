package com.ceph.objectGateway.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ceph.objectGateway.configure.S3Configure;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Aws工具类
 */

@Component
public class AwsClientUtil {
    @Resource
    private S3Configure s3Configure;

    // 维护一个本类的静态变量
    public static AwsClientUtil awsClientUtil;

    @PostConstruct
    public void init() {
        awsClientUtil = this;
        awsClientUtil.s3Configure = this.s3Configure;
    }
    public static AmazonS3 s3Client() {
        try {
            AWSCredentialsProvider credentialsProvider = new AWSCredentialsProvider() {
                public AWSCredentials getCredentials() {
                    return new BasicAWSCredentials(awsClientUtil.s3Configure.getAccessKey(), awsClientUtil.s3Configure.getSecretKey());
                }

                @Override
                public void refresh() {
                }
            };
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            clientConfiguration.setProtocol(Protocol.HTTP);
            AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(awsClientUtil.s3Configure.getHosts(), null);
            AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withEndpointConfiguration(endpointConfiguration).withClientConfiguration(clientConfiguration).build();
            return amazonS3;
        }catch (Exception e){
            return null;
        }
    }

    public static AmazonS3 s3Client(final String hosts,final String accessKey,final String secretKey) {
        if(StringUtils.isNotBlank(hosts)&&StringUtils.isNotBlank(accessKey)&&StringUtils.isNotBlank(secretKey)){
            AWSCredentialsProvider credentialsProvider = new AWSCredentialsProvider() {
                public AWSCredentials getCredentials() {
                    return new BasicAWSCredentials(accessKey,secretKey);
                }
                @Override
                public void refresh() {
                }
            };
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            clientConfiguration.setProtocol(Protocol.HTTP);
            AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(hosts, null);
            AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withEndpointConfiguration(endpointConfiguration).withClientConfiguration(clientConfiguration).build();
            return amazonS3;
        }
        return null;

    }

}
