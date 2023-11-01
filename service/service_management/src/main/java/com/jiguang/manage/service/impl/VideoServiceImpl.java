
package com.jiguang.manage.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.StringUtils;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.UpdateVideoInfoRequest;
import com.aliyuncs.vod.model.v20170321.UpdateVideoInfoResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jiguang.commonutils.GuliException;
import com.jiguang.manage.entity.Video;
import com.jiguang.manage.mapper.VideoMapper;
import com.jiguang.manage.service.VideoService;
import com.jiguang.manage.utils.ConstantPropertiesUtil;
import com.jiguang.manage.utils.ConstantVodUtils;
import com.jiguang.manage.utils.InitVodCilent;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-03-25
 */

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {




    //上传头像到oss
    @Override
    public String upload(MultipartFile file) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtil.END_POIND;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try {
            // 创建OSS实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();

            //1 在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            // yuy76t5rew01.jpg
            fileName = uuid+fileName;

            //2 把文件按照日期进行分类
            //获取当前日期
            //   2019/11/12
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            //  2019/11/12/ewtqr313401.jpg
            fileName = datePath+"/"+fileName;

            //调用oss方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
            //第三个参数  上传文件输入流
            ossClient.putObject(bucketName,fileName , inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //  https://edu-guli-1010.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            return url;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public String uploadVideoAly(MultipartFile file
            ,String title,String tags,String description) {

        DefaultAcsClient client =
                InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

            String fileName = file.getOriginalFilename();
            //title：上传之后显示名称


            //inputStream：上传文件输入流
            InputStream inputStream = null;

        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }


        UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);


            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);


            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();

            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }


            UpdateVideoInfoRequest request1 = new UpdateVideoInfoRequest();

            request1.setVideoId(videoId);
            request1.setTitle(title);
            request1.setDescription(description);
            request1.setTags(tags);


        try {
            UpdateVideoInfoResponse response1 = client.getAcsResponse(request1);
        } catch (ClientException e) {
            e.printStackTrace();
        }


        return videoId;



            }






 @Override
    public void removeMoreAlyVideo(List videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //videoIdList值转换成 1,2,3
            String videoIds = StringUtils.join(String.valueOf(videoIdList.toArray()), ",");

            //向request设置视频id
            request.setVideoIds(videoIds);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }




    }








}
