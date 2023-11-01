package com.jiguang.supplement.controller;



import com.jiguang.supplement.config.MyDocument;
import com.jiguang.supplement.entity.SupplementMap;
import com.jiguang.supplement.service.FileService;
import com.jiguang.commonutils.R;

import com.mongodb.client.result.UpdateResult;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@CrossOrigin //跨域

@RequestMapping("/map")

public class MapController {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired(required=false)
    private FileService fileService;


    @PostMapping("add")
    public R add(

            SupplementMap supplementMap,
            @RequestParam("longitude") double longitude,
            @RequestParam("latitude") double latitude,
            @RequestParam("file") MultipartFile picture)  {

       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=df.format(new Date());
        MyDocument myDocument=new MyDocument(longitude,latitude);
        supplementMap.setLocation(myDocument.getLocation());
      //  SupplementMap supplementMap=new SupplementMap();
        String uploadUrl = fileService.upload(picture);
       /* supplementMap.setAddress(address);
        supplementMap.setHours(hours);
        supplementMap.setLocation((GeoJsonPoint) location);
        supplementMap.setName(name);

        supplementMap.setMobilePhone(mobilePhone);*/
        supplementMap.setPicture(uploadUrl);
        supplementMap.setStatus(1);

        supplementMap.setGmtCreate(date);
      mongoTemplate.insert(supplementMap);
        return R.ok();
    }


     @PostMapping("updata")
    public  R update(@RequestBody SupplementMap supplementMap){
         Query query = new Query(Criteria.where("supplement_id").is(supplementMap.getId()));
         Update update = new Update();

         //当前营业状态
         update.set("status",supplementMap.getStatus());
         update.set("name",supplementMap.getName());
         update.set("mobile_phone",supplementMap.getMobilePhone());
         update.set("location",supplementMap.getLocation());
         update.set("hours",supplementMap.getHours());
         update.set("address",supplementMap.getAddress());

         UpdateResult updateResult = mongoTemplate.upsert(query, update,
                 SupplementMap.class);


   if(updateResult.getModifiedCount()>0) {
       return R.ok().message("修改成功");
   } else {
       return R.error().message("修改失败");
   }
     }



     //查询站点数量
    @GetMapping("select")
    public  R select(
    ){
       Query query=new Query();
       Long list = mongoTemplate.count(query,SupplementMap.class);
        return R.ok().data("number",list);
    }


    @GetMapping("selectAll")
    public R selectAll(){
Query query=new Query();
        List<SupplementMap> list = mongoTemplate.find(query,SupplementMap.class);

        return R.ok().data("list",list);
    }





//查询所有场站信息
    @GetMapping("getAll/{pages}")
    public R getAll(@PathVariable Integer pages){

        Query query = new Query();

//sort排序
//query.with(Sort.by(Sort.Order.desc("salary")));
//skip limit 分页 skip用于指定跳过记录数，limit则用于限定返回结果数量。
        query.with(Sort.by(Sort.Order.desc("gmt_create")))
                .skip(10*(pages-1)) //指定跳过记录数
                .limit(10); //每页显示记录数


        List<SupplementMap> list = mongoTemplate.find(query,SupplementMap.class);
        return R.ok().data("list",list);
    }




//查询附件20公里以内的收获站
    @GetMapping("select/{longitude}/{latitude}")
    public R findLocationsNearby(@PathVariable  double longitude, @PathVariable double latitude) {
        int maxDistance =20;
        Point point = new Point(longitude, latitude);
        NearQuery nearQuery = NearQuery.near(point).maxDistance(maxDistance, Metrics.KILOMETERS);
        GeoResults<SupplementMap> geoResults = mongoTemplate.geoNear(nearQuery, SupplementMap.class);

        List<SupplementMap> locations = new ArrayList<>();

        for (GeoResult<SupplementMap> geoResult : geoResults) {
            SupplementMap location = geoResult.getContent();
            locations.add(location);
        }
        return R.ok().data("map",locations);
    }







    //场站搜索根据名字
    @GetMapping("selectName")
    public R selectName(@RequestParam("name")String name){

        Query query = new Query();

        Criteria criteria = Criteria.where("name").regex(name);
        query.addCriteria(criteria);

        List<SupplementMap> list = mongoTemplate.find(query,SupplementMap.class);


        return R.ok().data("list",list);
    }






}
