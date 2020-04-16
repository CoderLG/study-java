package com.ceph.objectGateway.controller.file;

import com.ceph.common.HaoCangServerResponse;
import com.ceph.objectGateway.service.BucketFileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 对象存储相关的文件接口
 */
@Controller
@RequestMapping(value = "/bucketFile")
public class BucketFileController {

    @Resource
    private BucketFileService bucketFileService;

    /**
     * 根据 bucketName 查询所有的文件（不分页，直接查询ceph服务）
     * @param bucketName
     * @return
     */
    @RequestMapping(value = "/bucketFileList",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public HaoCangServerResponse bucketFileList(@RequestParam(value = "bucketName")String bucketName){
       return bucketFileService.bucketFileList(bucketName);
    }
    // TODO: 2018/9/18    根据 bucketName 查询文件 分页建表、考虑数据同步问题


    //文件上传
    @RequestMapping(value = "/fileUpload",method = {RequestMethod.POST})
    @ResponseBody
    public HaoCangServerResponse fileUpload(@RequestParam(value = "file")MultipartFile multipartFile, @RequestParam(value = "bucketName")String bucketName, HttpServletRequest request){
        return bucketFileService.fileUpload(multipartFile,bucketName);
    }

    //文件下载，返回url，让前端自己下载
    @RequestMapping(value = "/fileDownLoad",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public HaoCangServerResponse fileDownLoad(@RequestParam(value = "bucketName")String bucketName,@RequestParam(value = "key")String key){

        return bucketFileService.fileDownLoad(bucketName,key);
    }



    // 文件下载对于服务器内的，开放给外面下载不合理 暂时不需要
    /*@RequestMapping(value = "/fileDownLoad",method = {RequestMethod.POST})
    @ResponseBody
    public HaoCangServerResponse fileDownLoad(@RequestParam(value = "bucketName")String bucketName, HttpServletRequest request){

        return null;
    }*/


}
