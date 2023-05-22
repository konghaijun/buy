package com.jiguang.supplement.controller;


import com.jiguang.supplement.entity.SupplementMap;
import com.jiguang.supplement.service.FileService;
import com.jiguang.commonutils.R;
import com.mongodb.client.result.UpdateResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Auther: 23091
 * @Date: 2022/8/23 22:51
 * @Description:
 */

@Api(description="阿里云文件管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/oss")
public class FileUploadController {

    @Autowired(required=false)
    private FileService fileService;

    @Autowired
    MongoTemplate mongoTemplate;
    /**
     * 文件上传
     *
     * @param file
     */
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R upload(
             @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file,
          @RequestParam("supplementId") Integer supplementId )

    {

        String uploadUrl = fileService.upload(file);
        Query query = new Query(Criteria.where("supplement_id").is(supplementId));
        Update update = new Update();

        //修改图片
        update.set("picture",uploadUrl);

        UpdateResult updateResult = mongoTemplate.upsert(query, update,
                SupplementMap.class);


        if(updateResult.getModifiedCount()>0) {
            return R.ok().message("修改成功");
        } else {
            return R.error().message("修改失败");
        }

    }
}
