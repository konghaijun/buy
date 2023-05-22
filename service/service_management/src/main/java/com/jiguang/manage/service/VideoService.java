package com.jiguang.manage.service;

import com.jiguang.manage.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface VideoService extends IService<Video> {



    String upload(MultipartFile file);


    //上传视频到阿里云
    String uploadVideoAly(MultipartFile file
            ,String title,String tags,String description);

    void removeMoreAlyVideo(List videoIdList);



}
