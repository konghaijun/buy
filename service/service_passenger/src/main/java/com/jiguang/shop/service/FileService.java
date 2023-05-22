package com.jiguang.shop.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {

   //上传
    String upload(MultipartFile file);
}
