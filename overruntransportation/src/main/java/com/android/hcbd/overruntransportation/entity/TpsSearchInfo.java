package com.android.hcbd.overruntransportation.entity;

import java.io.Serializable;

/**
 * Created by guocheng on 2017/4/17.
 */

public class TpsSearchInfo implements Serializable{
    private String code;
    private String goodsName;
    private String checkState;
    private String checkStateName;
    private String beginTime;
    private String endTime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCheckState() {
        return checkState;
    }

    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    public String getCheckStateName() {
        return checkStateName;
    }

    public void setCheckStateName(String checkStateName) {
        this.checkStateName = checkStateName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
