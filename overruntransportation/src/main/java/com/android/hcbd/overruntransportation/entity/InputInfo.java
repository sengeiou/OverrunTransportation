package com.android.hcbd.overruntransportation.entity;

import java.io.Serializable;

/**
 * Created by guocheng on 2017/3/20.
 */

public class InputInfo implements Serializable{
    /*站点信息*/
    private String punish_site_name; //公路名称
    private String punish_site_checkPlace; //检测地点
    private String punish_site_shipDirection; //运输地点
    /*司机信息*/
    private String punish_driver_name; //司机姓名
    private String punish_driver_ids; //身份证号
    private String punish_driver_sex; //姓别
    private String punish_driver_phone; //联系电话
    private String punish_driver_addr; //联系地址
    private String punish_driver_driverAddr; //驾驶证地址
    private String punish_driver_no1; //驾驶证号
    private String punish_driver_certId; //从业资格证号
    private String punish_driver_post; //单位职务
    private String punish_driver_caseRel; //案件关系
    private String punish_driver_job; //职业
    /*货物信息*/
    private String punish_tpsGoods_name; //货主名称
    private String punish_tpsGoods_phone; //货主电话
    private String punish_tpsGoods_goods; //货物名称
    private String punish_tpsGoods_startPlace; //出发地点
    private String punish_tpsGoods_endPlace; //目的地点
    private String punish_isUninstall; //是否不可解体
    private String punish_overrunNo; //超限证号
    /*案件信息*/
    private String punish_tpsCase_endTime1; //立案时间
    private String punish_tpsCase_beginTime3; //勘验时间
    private String punish_tpsCase_endTime3; //至
    private String punish_tpsCase_beginTime4; //询问时间
    private String punish_tpsCase_endTime4; //至
    /*车辆信息*/
    private String punish_car_no1; //车辆号码
    private String punish_car_no2; //挂车号牌
    private String punish_car_no3; //营运证号
    private String punish_car_owner; //车辆所有人
    private String punish_car_addr; //所有人地址
    private String punish_car_phone; //所有人电话
    private String punish_car_lawOper; //法人代表
    private String punish_car_lawName; //法人名称
    private String punish_car_lawPhone; //联系电话
    private String punish_car_lawAddr; //法人地址
    private String punish_car_lawPost; //法人职务
    /*执法信息*/
    private String punish_o1_id; //执法人员1
    private String punish_o2_id; //执法人员2
    private String punish_k1_id; //勘验人员1
    private String punish_k2_id; //勘验人员2
    /*检测信息*/
    private String punish_carType_remark; //车轴类型
    private String punish_weight; //车重
    private String punish_length; //长度
    private String punish_width; //宽度
    private String punish_height; // 高度
    private String punish_source; //案件来源
    private String punish_amt; //处罚金额
    private String punish_isCompany; //处罚对象
    private String punish_dutyTime; //责改日期
    private String punish_state; //是否结案
    private String punish_proof; //证据材料

    public String getPunish_site_name() {
        return punish_site_name;
    }

    public void setPunish_site_name(String punish_site_name) {
        this.punish_site_name = punish_site_name;
    }

    public String getPunish_site_checkPlace() {
        return punish_site_checkPlace;
    }

    public void setPunish_site_checkPlace(String punish_site_checkPlace) {
        this.punish_site_checkPlace = punish_site_checkPlace;
    }

    public String getPunish_site_shipDirection() {
        return punish_site_shipDirection;
    }

    public void setPunish_site_shipDirection(String punish_site_shipDirection) {
        this.punish_site_shipDirection = punish_site_shipDirection;
    }

    public String getPunish_driver_name() {
        return punish_driver_name;
    }

    public void setPunish_driver_name(String punish_driver_name) {
        this.punish_driver_name = punish_driver_name;
    }

    public String getPunish_driver_ids() {
        return punish_driver_ids;
    }

    public void setPunish_driver_ids(String punish_driver_ids) {
        this.punish_driver_ids = punish_driver_ids;
    }

    public String getPunish_driver_sex() {
        return punish_driver_sex;
    }

    public void setPunish_driver_sex(String punish_driver_sex) {
        this.punish_driver_sex = punish_driver_sex;
    }

    public String getPunish_driver_phone() {
        return punish_driver_phone;
    }

    public void setPunish_driver_phone(String punish_driver_phone) {
        this.punish_driver_phone = punish_driver_phone;
    }

    public String getPunish_driver_addr() {
        return punish_driver_addr;
    }

    public void setPunish_driver_addr(String punish_driver_addr) {
        this.punish_driver_addr = punish_driver_addr;
    }

    public String getPunish_driver_driverAddr() {
        return punish_driver_driverAddr;
    }

    public void setPunish_driver_driverAddr(String punish_driver_driverAddr) {
        this.punish_driver_driverAddr = punish_driver_driverAddr;
    }

    public String getPunish_driver_no1() {
        return punish_driver_no1;
    }

    public void setPunish_driver_no1(String punish_driver_no1) {
        this.punish_driver_no1 = punish_driver_no1;
    }

    public String getPunish_driver_certId() {
        return punish_driver_certId;
    }

    public void setPunish_driver_certId(String punish_driver_certId) {
        this.punish_driver_certId = punish_driver_certId;
    }

    public String getPunish_driver_post() {
        return punish_driver_post;
    }

    public void setPunish_driver_post(String punish_driver_post) {
        this.punish_driver_post = punish_driver_post;
    }

    public String getPunish_driver_caseRel() {
        return punish_driver_caseRel;
    }

    public void setPunish_driver_caseRel(String punish_driver_caseRel) {
        this.punish_driver_caseRel = punish_driver_caseRel;
    }

    public String getPunish_driver_job() {
        return punish_driver_job;
    }

    public void setPunish_driver_job(String punish_driver_job) {
        this.punish_driver_job = punish_driver_job;
    }

    public String getPunish_tpsGoods_name() {
        return punish_tpsGoods_name;
    }

    public void setPunish_tpsGoods_name(String punish_tpsGoods_name) {
        this.punish_tpsGoods_name = punish_tpsGoods_name;
    }

    public String getPunish_tpsGoods_phone() {
        return punish_tpsGoods_phone;
    }

    public void setPunish_tpsGoods_phone(String punish_tpsGoods_phone) {
        this.punish_tpsGoods_phone = punish_tpsGoods_phone;
    }

    public String getPunish_tpsGoods_goods() {
        return punish_tpsGoods_goods;
    }

    public void setPunish_tpsGoods_goods(String punish_tpsGoods_goods) {
        this.punish_tpsGoods_goods = punish_tpsGoods_goods;
    }

    public String getPunish_tpsGoods_startPlace() {
        return punish_tpsGoods_startPlace;
    }

    public void setPunish_tpsGoods_startPlace(String punish_tpsGoods_startPlace) {
        this.punish_tpsGoods_startPlace = punish_tpsGoods_startPlace;
    }

    public String getPunish_tpsGoods_endPlace() {
        return punish_tpsGoods_endPlace;
    }

    public void setPunish_tpsGoods_endPlace(String punish_tpsGoods_endPlace) {
        this.punish_tpsGoods_endPlace = punish_tpsGoods_endPlace;
    }

    public String getPunish_isUninstall() {
        return punish_isUninstall;
    }

    public void setPunish_isUninstall(String punish_isUninstall) {
        this.punish_isUninstall = punish_isUninstall;
    }

    public String getPunish_overrunNo() {
        return punish_overrunNo;
    }

    public void setPunish_overrunNo(String punish_overrunNo) {
        this.punish_overrunNo = punish_overrunNo;
    }

    public String getPunish_tpsCase_endTime1() {
        return punish_tpsCase_endTime1;
    }

    public void setPunish_tpsCase_endTime1(String punish_tpsCase_endTime1) {
        this.punish_tpsCase_endTime1 = punish_tpsCase_endTime1;
    }

    public String getPunish_tpsCase_beginTime3() {
        return punish_tpsCase_beginTime3;
    }

    public void setPunish_tpsCase_beginTime3(String punish_tpsCase_beginTime3) {
        this.punish_tpsCase_beginTime3 = punish_tpsCase_beginTime3;
    }

    public String getPunish_tpsCase_endTime3() {
        return punish_tpsCase_endTime3;
    }

    public void setPunish_tpsCase_endTime3(String punish_tpsCase_endTime3) {
        this.punish_tpsCase_endTime3 = punish_tpsCase_endTime3;
    }

    public String getPunish_tpsCase_beginTime4() {
        return punish_tpsCase_beginTime4;
    }

    public void setPunish_tpsCase_beginTime4(String punish_tpsCase_beginTime4) {
        this.punish_tpsCase_beginTime4 = punish_tpsCase_beginTime4;
    }

    public String getPunish_tpsCase_endTime4() {
        return punish_tpsCase_endTime4;
    }

    public void setPunish_tpsCase_endTime4(String punish_tpsCase_endTime4) {
        this.punish_tpsCase_endTime4 = punish_tpsCase_endTime4;
    }

    public String getPunish_car_no1() {
        return punish_car_no1;
    }

    public void setPunish_car_no1(String punish_car_no1) {
        this.punish_car_no1 = punish_car_no1;
    }

    public String getPunish_car_no2() {
        return punish_car_no2;
    }

    public void setPunish_car_no2(String punish_car_no2) {
        this.punish_car_no2 = punish_car_no2;
    }

    public String getPunish_car_no3() {
        return punish_car_no3;
    }

    public void setPunish_car_no3(String punish_car_no3) {
        this.punish_car_no3 = punish_car_no3;
    }

    public String getPunish_car_owner() {
        return punish_car_owner;
    }

    public void setPunish_car_owner(String punish_car_owner) {
        this.punish_car_owner = punish_car_owner;
    }

    public String getPunish_car_addr() {
        return punish_car_addr;
    }

    public void setPunish_car_addr(String punish_car_addr) {
        this.punish_car_addr = punish_car_addr;
    }

    public String getPunish_car_phone() {
        return punish_car_phone;
    }

    public void setPunish_car_phone(String punish_car_phone) {
        this.punish_car_phone = punish_car_phone;
    }

    public String getPunish_car_lawOper() {
        return punish_car_lawOper;
    }

    public void setPunish_car_lawOper(String punish_car_lawOper) {
        this.punish_car_lawOper = punish_car_lawOper;
    }

    public String getPunish_car_lawName() {
        return punish_car_lawName;
    }

    public void setPunish_car_lawName(String punish_car_lawName) {
        this.punish_car_lawName = punish_car_lawName;
    }

    public String getPunish_car_lawPhone() {
        return punish_car_lawPhone;
    }

    public void setPunish_car_lawPhone(String punish_car_lawPhone) {
        this.punish_car_lawPhone = punish_car_lawPhone;
    }

    public String getPunish_car_lawAddr() {
        return punish_car_lawAddr;
    }

    public void setPunish_car_lawAddr(String punish_car_lawAddr) {
        this.punish_car_lawAddr = punish_car_lawAddr;
    }

    public String getPunish_car_lawPost() {
        return punish_car_lawPost;
    }

    public void setPunish_car_lawPost(String punish_car_lawPost) {
        this.punish_car_lawPost = punish_car_lawPost;
    }

    public String getPunish_o1_id() {
        return punish_o1_id;
    }

    public void setPunish_o1_id(String punish_o1_id) {
        this.punish_o1_id = punish_o1_id;
    }

    public String getPunish_o2_id() {
        return punish_o2_id;
    }

    public void setPunish_o2_id(String punish_o2_id) {
        this.punish_o2_id = punish_o2_id;
    }

    public String getPunish_k1_id() {
        return punish_k1_id;
    }

    public void setPunish_k1_id(String punish_k1_id) {
        this.punish_k1_id = punish_k1_id;
    }

    public String getPunish_k2_id() {
        return punish_k2_id;
    }

    public void setPunish_k2_id(String punish_k2_id) {
        this.punish_k2_id = punish_k2_id;
    }

    public String getPunish_carType_remark() {
        return punish_carType_remark;
    }

    public void setPunish_carType_remark(String punish_carType_remark) {
        this.punish_carType_remark = punish_carType_remark;
    }

    public String getPunish_weight() {
        return punish_weight;
    }

    public void setPunish_weight(String punish_weight) {
        this.punish_weight = punish_weight;
    }

    public String getPunish_length() {
        return punish_length;
    }

    public void setPunish_length(String punish_length) {
        this.punish_length = punish_length;
    }

    public String getPunish_width() {
        return punish_width;
    }

    public void setPunish_width(String punish_width) {
        this.punish_width = punish_width;
    }

    public String getPunish_height() {
        return punish_height;
    }

    public void setPunish_height(String punish_height) {
        this.punish_height = punish_height;
    }

    public String getPunish_source() {
        return punish_source;
    }

    public void setPunish_source(String punish_source) {
        this.punish_source = punish_source;
    }

    public String getPunish_amt() {
        return punish_amt;
    }

    public void setPunish_amt(String punish_amt) {
        this.punish_amt = punish_amt;
    }

    public String getPunish_isCompany() {
        return punish_isCompany;
    }

    public void setPunish_isCompany(String punish_isCompany) {
        this.punish_isCompany = punish_isCompany;
    }

    public String getPunish_dutyTime() {
        return punish_dutyTime;
    }

    public void setPunish_dutyTime(String punish_dutyTime) {
        this.punish_dutyTime = punish_dutyTime;
    }

    public String getPunish_state() {
        return punish_state;
    }

    public void setPunish_state(String punish_state) {
        this.punish_state = punish_state;
    }

    public String getPunish_proof() {
        return punish_proof;
    }

    public void setPunish_proof(String punish_proof) {
        this.punish_proof = punish_proof;
    }
}
