package com.jiguang.shop.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.jiguang.commonutils.R;
import com.jiguang.shop.entity.UcenterMember;
import com.jiguang.shop.service.FileService;
import com.jiguang.shop.service.UcenterMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



@Api(description="阿里云文件管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/shop/oss")
public class FileUploadController {

    @Autowired(required=false)
    private FileService fileService;

    @Autowired
    private UcenterMemberService ucenterMemberService;


    @ApiOperation(value = "修改头像")
    @PostMapping("upload")
    public R upload(

            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
            @RequestParam("uId") String uid
            ) {

        UpdateWrapper<UcenterMember> wrapper=new UpdateWrapper<>();
        wrapper.eq("id",uid);
        String uploadUrl = fileService.upload(file);
        UcenterMember ucenterMember=new UcenterMember();
        /*ucenterMember.setNickname(nickName);*/
        ucenterMember.setAvatar(uploadUrl);
       boolean f= ucenterMemberService.update(ucenterMember,wrapper);

        //返回r对象
      if(f) {
          return R.ok().message("修改成功");} else{
              return R.error().message("修改失败");
          }
      }

    }

