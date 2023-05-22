package com.jiguang.supplement.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: 23091
 * @Date: 2022/8/23 22:38
 * @Description:
 */
public interface FileService {

    /**
     * 文件上传至阿里云
     * @param file
     * @return
     */
    String upload(MultipartFile file);
}
