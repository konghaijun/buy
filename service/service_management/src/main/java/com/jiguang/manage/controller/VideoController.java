package com.jiguang.manage.controller;


import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.*;
import com.jiguang.commonutils.GuliException;
import com.jiguang.commonutils.R;
import com.jiguang.manage.entity.VideoCount;
import com.jiguang.manage.service.FileService;
import com.jiguang.manage.service.VideoCountService;
import com.jiguang.manage.service.VideoService;
import com.jiguang.manage.utils.ConstantVodUtils;
import com.jiguang.manage.utils.InitVodCilent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin //跨域
@RestController
@RequestMapping("/manage/video")
public class VideoController {

    @Autowired
    VideoService videoService;

    @Autowired
    VideoCountService videoCountService;

    //上传视频到阿里云
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(@RequestParam("video") MultipartFile file,
                             @RequestParam("title") String title,
                             @RequestParam("tags") String tags,
                             @RequestParam("description") String description

                             ) {
        //返回上传视频id


       String videoId = videoService.uploadVideoAly(file,title,tags,description);


      return R.ok().data("id",videoId);

    }


    //根据视频id删除阿里云视频
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        }catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"删除视频失败");
        }
    }


    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);

            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            String playAuth = response.getPlayAuth();


            return R.ok().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new GuliException(20001,"获取凭证失败");
        }
    }


    //GetVideoList：查询视频列表，包括视频的基本信息。
    @GetMapping("getPlayAuthPages/{pages}")
    public R getVideoList(@PathVariable Integer pages) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoListRequest request = new GetVideoListRequest();
            //向request设置视频id
            request.setPageSize(10);
            request.setPageNo(pages);

            GetVideoListResponse response3 = client.getAcsResponse(request);
            List<GetVideoListResponse.Video> videoList = response3.getVideoList();


            return R.ok().data("videoList",videoList);
        }catch(Exception e) {
            throw new GuliException(201,"获取视频列表失败");
        }
    }



    @GetMapping("getPlayNumber")
    public R getPlayNumber() {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoListRequest request = new GetVideoListRequest();

            request.setPageNo(1);
            request.setPageSize(1);

            GetVideoListResponse response3 = client.getAcsResponse(request);


            return R.ok().data("number",response3.getTotal());
        }catch(Exception e) {
            throw new GuliException(201,"获取数量失败");
        }
    }




//根据id获取视频地址
    @GetMapping ("getPlayAddress/{id}")
    public R getPlayAddress(@PathVariable String id) {
        DefaultAcsClient client =
                InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
        //创建获取凭证request和response对象
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        //向request设置视频id
        request.setVideoId(id);

        GetPlayInfoResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
     return  R.ok().data("url",response.getPlayInfoList());
    }








    //视频地址
     @GetMapping ("getPlayInfo")
    public R getPlayInfo() {
         DefaultAcsClient client =
                 InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
         //创建获取凭证request和response对象


         //创建获取凭证request和response对象
         GetVideoListRequest request1 = new GetVideoListRequest();
         //向request设置视频id
         //   request1.setPageSize(10);

         GetVideoListResponse response3 = null;
         try {
             response3 = client.getAcsResponse(request1);
         } catch (Exception e) {
             e.printStackTrace();
         }
         List<GetVideoListResponse.Video> videoList = response3.getVideoList();


         List list = new ArrayList();
         for (GetVideoListResponse.Video i : videoList) {
             GetPlayInfoRequest request = new GetPlayInfoRequest();
             //向request设置视频id
             request.setVideoId(i.getVideoId());


             GetPlayInfoResponse response = null;
             try {
                 response = client.getAcsResponse(request);
             } catch (Exception e) {
                 e.printStackTrace();
             }

             list.add(response.getPlayInfoList());
         }
         //输出请求结果}
         //输出请求结果播放地址
         return R.ok().data("list", list);

     }

    //获取昨天视频播放量
    @Scheduled(cron = "0 0 0 * * ?")
    public  void describePlayUserTotal() {

         DefaultAcsClient client =
                 InitVodCilent.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);

         DescribePlayUserTotalRequest request = new DescribePlayUserTotalRequest();
         Calendar cal = Calendar.getInstance();
         cal.add(Calendar.DATE, -1);
         SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
         String yesterdayStart = sdf.format(cal.getTime())+"T00:00:00Z";
         String yesterdayEnd = sdf.format(cal.getTime())+"T23:59:59Z";

          request.setStartTime(yesterdayStart);
          request.setEndTime(yesterdayEnd);

         DescribePlayUserTotalResponse response;

         try {

             response = client.getAcsResponse(request);

             List<DescribePlayUserTotalResponse.UserPlayStatisTotal> userPlayStatisTotals=response.getUserPlayStatisTotals();

             cal = Calendar.getInstance();
             Date date=sdf.parse(cal.toString());

             BigDecimal time=new BigDecimal(0);
             int count=0;
             for(DescribePlayUserTotalResponse.UserPlayStatisTotal i:userPlayStatisTotals){
               time=time.add(new BigDecimal(i.getPlayDuration()));
               count=count+Integer.parseInt(i.getVV().getAndroid());
               count=count+Integer.parseInt(i.getVV().getIOS());
               count=count+Integer.parseInt(i.getVV().getHTML5());
               count=count+Integer.parseInt(i.getVV().getFlash());
             }

             VideoCount v=new VideoCount();
             v.setCount(count);
             v.setTime(time);
             v.setMonth(date);
             videoCountService.save(v);
         } catch (Exception e) {
             e.printStackTrace();
         }
     }



     @Autowired
     FileService fileService;

   @PostMapping("up")
    public R up(@RequestParam("file")MultipartFile file){

    String s=   fileService.upload(file);
        return R.ok().data("s",s);
   }







}

