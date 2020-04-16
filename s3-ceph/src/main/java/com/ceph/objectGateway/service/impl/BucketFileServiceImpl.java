package com.ceph.objectGateway.service.impl;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.ceph.common.HaoCangServerResponse;
import com.ceph.objectGateway.service.BucketFileService;
import com.ceph.objectGateway.util.AwsClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class BucketFileServiceImpl implements BucketFileService {


    /**
     * 获取bucket内的所有对象
     * @param bucketName
     * @return
     */
    public HaoCangServerResponse bucketFileList(String bucketName){
        if(StringUtils.isNotBlank(bucketName)){
            AmazonS3 s3 = AwsClientUtil.s3Client();
            if(s3 !=null) {
                ObjectListing objects = s3.listObjects(bucketName);

                return HaoCangServerResponse.createSuccess(objects);
            }
            return HaoCangServerResponse.createError("ceph连接异常");
        }
        return HaoCangServerResponse.createError("bucket不能为空");
    }

    /**
     * 文件上传
     * @param multipartFile
     * @param bucketName
     * @return
     */
    public HaoCangServerResponse fileUpload(MultipartFile multipartFile, String bucketName){
        SimpleDateFormat sdf  = new SimpleDateFormat("yyy/MM/dd/hh:mm:ss");
        if(!multipartFile.isEmpty() && StringUtils.isNotBlank(bucketName)){
            String name = multipartFile.getOriginalFilename();
            long size = multipartFile.getSize();
            AmazonS3 s3 = AwsClientUtil.s3Client();
            if(s3 != null){
                //key 使用uuid随机生成
                String key = name +""+sdf.format(new Date());
                ObjectMetadata metadata = new ObjectMetadata();
                // 必须设置ContentLength
                metadata.setContentLength(size);
                try {
                    s3.putObject(bucketName,key,multipartFile.getInputStream(),metadata);//metadata可以为空
                    GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key);
                    URL url = s3.generatePresignedUrl(request);
                    Map<String, Object> result = new HashMap<>();
                    result.put("name",name);
                    result.put("size",size);
                    result.put("url",url);
                    return HaoCangServerResponse.createSuccess("上传成功",result);
                } catch (IOException e) {
                    return HaoCangServerResponse.createError("文件:"+name+"上传异常");
                }
            }
            return HaoCangServerResponse.createError("ceph连接异常");
        }
        return HaoCangServerResponse.createError("参数异常");
    }

    /**
     * 文件下载
     * @param bucketName
     * @param key
     * @return
     */
    public HaoCangServerResponse fileDownLoad(String bucketName,String key){
        if(StringUtils.isNotBlank(bucketName) && StringUtils.isNotBlank(key)){
            AmazonS3 s3 = AwsClientUtil.s3Client();
            if(s3 != null){
                GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, key);
                URL url = s3.generatePresignedUrl(request);
                Map<String,Object> result = new HashMap<>();
                result.put("url",url);
                return HaoCangServerResponse.createSuccess(result);
            }
            return HaoCangServerResponse.createError("ceph连接异常");
        }
        return HaoCangServerResponse.createError();
    }

}
