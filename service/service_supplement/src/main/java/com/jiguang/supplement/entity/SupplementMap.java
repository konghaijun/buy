package com.jiguang.supplement.entity;

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


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "supplement_map")
public class SupplementMap {

        @Id
        @Field("_id")
        private ObjectId id; //主键id

        @Indexed
        @Field(value = "address")
        private String address;

        @Field(value = "gmtCreate")
        @TableField(fill = FieldFill.INSERT)
        private String gmtCreate;

        @Field(value = "nnames")
        private String  name;

        @Field(value = "hours")
        private  String hours;

      @Field(value = "mobilePhone")
      private  String mobilePhone;

        @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
        private GeoJsonPoint location; //x:经度 y:纬度

        @Field(value = "picture")
        private  String picture;

        @Field(value = "status")
        private Integer status;

         @Field(value = "Dcode")
    private Integer Dcode;



        @Field(value = "supplementNumber")
        private Integer supplementNumber;



}
