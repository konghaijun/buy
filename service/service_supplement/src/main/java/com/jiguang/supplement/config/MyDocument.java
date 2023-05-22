package com.jiguang.supplement.config;

import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
public class MyDocument {
    private GeoJsonPoint location;
    public MyDocument(double longitude, double latitude) {
        this.location = new GeoJsonPoint(longitude, latitude);
    }
    public GeoJsonPoint getLocation() {
        return location;
    }
    public void setLocation(GeoJsonPoint location) {
        this.location = location;
    }
}
