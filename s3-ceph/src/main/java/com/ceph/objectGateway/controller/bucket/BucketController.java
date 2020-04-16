package com.ceph.objectGateway.controller.bucket;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.ceph.common.HaoCangServerResponse;
import com.ceph.objectGateway.service.BucketService;
import com.ceph.objectGateway.util.AwsClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 *  bucket 相关接口
 */
@Controller
@RequestMapping("/bucket")
public class BucketController {


    @Resource
    private BucketService bucketService;

    /**
     * 测试连接ceph 接口
     * @return
     */
    @RequestMapping("/testCeph")
    @ResponseBody
    public String test(){
        AmazonS3 s3 = AwsClientUtil.s3Client();
        StringBuffer sb = new StringBuffer("bucketList:");
        if(s3 !=null){
            List<Bucket> bucketList =  s3.listBuckets();
            for (Bucket bucket : bucketList) {
                sb.append(bucket.getName());
                sb.append(",");
            }
        }
        return sb.toString();
    }
    /**
     * bucket的list查询接口 : 暂时查询ceph服务
     * todo 后面需要考试是否是查询设计表
     */
    @RequestMapping(value = "/bucketList",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public HaoCangServerResponse bucketList(){
        return bucketService.getBucketList();
    }

    /**
     * 创建 bucket
     * @param bucketName  bucketName有验证规则，前端验证
     * @return
     */
    @RequestMapping(value = "/createBucket",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public HaoCangServerResponse createBucket(@RequestParam(value = "bucketName")String bucketName){
        return bucketService.createBucket(bucketName);
    }
}
