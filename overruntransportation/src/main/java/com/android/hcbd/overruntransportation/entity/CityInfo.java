package com.android.hcbd.overruntransportation.entity;

/**
 * Created by guocheng on 2017/3/27.
 */

public class CityInfo {
    private String cityName;
    private String addr;
    private double latitude; //纬度
    private double lontitude;//经度

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLontitude() {
        return lontitude;
    }

    public void setLontitude(double lontitude) {
        this.lontitude = lontitude;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
