package com.jiguang.driver.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigInteger;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "driver_map")
public class DriverMap {

        @Id
        private ObjectId id; //主键id


        @Indexed
        @Field(value = "drive_id")
        private BigInteger driverId; //名称

        @Field(value = "gmt_create")
        @TableField(fill = FieldFill.INSERT)
        private Date gmtCreate;

        @Field(value = "user_nickname")
        private String  userNickname;

        @Field(value = "mobile_phone")
        private  String mobilePhone;



        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
        private GeoJsonPoint location; //x:经度 y:纬度





}
