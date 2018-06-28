package com.android.hcbd.overruntransportation.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guocheng on 2017/3/20.
 */

public class TpsListInfo implements Serializable{
    private PageInfo pageInfo;
    private List<DataInfo> dataInfoList;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<DataInfo> getDataInfoList() {
        return dataInfoList;
    }

    public void setDataInfoList(List<DataInfo> dataInfoList) {
        this.dataInfoList = dataInfoList;
    }

    public static class PageInfo implements Serializable {
        private int currentPage;
        private int next;
        private int nowPageSize;
        private int pageSize;
        private int previous;
        private int totalSize;
        private Object url;

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getNext() {
            return next;
        }

        public void setNext(int next) {
            this.next = next;
        }

        public int getNowPageSize() {
            return nowPageSize;
        }

        public void setNowPageSize(int nowPageSize) {
            this.nowPageSize = nowPageSize;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPrevious() {
            return previous;
        }

        public void setPrevious(int previous) {
            this.previous = previous;
        }

        public int getTotalSize() {
            return totalSize;
        }

        public void setTotalSize(int totalSize) {
            this.totalSize = totalSize;
        }

        public Object getUrl() {
            return url;
        }

        public void setUrl(Object url) {
            this.url = url;
        }
    }

    public static class DataInfo implements Serializable{

        /**
         * amount : 陆佰陆拾陆元整(￥666.00)
         * amt : 666.0
         * car : {"addr":"666","id":57,"lawAddr":"666","lawName":"666","lawOper":"666","lawPhone":"666","lawPost":"666","no1":"666","no2":"666","no3":"666","owner":"666","phone":"666"}
         * carType : {"axisNum":"6","axisType":"双","code":"004","createTime":"2017-01-06T16:41:47","height":4,"id":4,"length":18.1,"limit":49,"modelContent":["车型信息","/tps/carTypeAction!"],"name":null,"names":"004-null","operNames":null,"orgCode":"027","paramsObj":null,"remark":null,"state":"1","stateContent":"启用","type":"铰接列车","typeImg":"/upload/file/027/2017/1/6/004 16_41_47.png","upload":null,"uploadContentType":null,"uploadFileName":null,"wheelNum":"12","width":2.55}
         * checkDate : 2017-03-24T11:37:14
         * checkOper : null
         * checkState : 1
         * checkStateName : 已复核
         * code : S0089
         * codes : 9
         * company : null
         * createTime : 2017-03-20T17:23:55
         * d2 : 2017年03月20日
         * discuss : null
         * driver : {"addr":"666","age":0,"caseRel":"666","certId":"666","cntDriver":0,"code":null,"createTime":"2017-03-30T08:57:26","driverAddr":"666","id":57,"ids":"666","job":"666","modelContent":null,"name":"王成","names":"null-王成","no1":"666","operNames":null,"orgCode":null,"paramsObj":null,"phone":"666","post":"666","remark":null,"sex":"男","state":null,"stateContent":"历史"}
         * dutyTime : 2017-3-20
         * height : 4.0
         * id : 51
         * invoiceNo : null
         * isCompany : 是
         * isUninstall : 是
         * k1 : {"bankNo":null,"birthday":"","code":"S0001","createTime":"2016-12-14T17:15:56","credit":"G001-湖北省交通运输厅高速公路路政执法总队京珠支队第二大队","email":"yf@126.com","id":2,"mobilephone":"","name":"叶飞","names":"S0001-叶飞","notice":"1","operNames":null,"orgCode":null,"paramsObj":null,"phone":"","pwdTime":null,"remark":null,"sex":"1","state":"1","stateContent":"启用","upload":null,"uploadContentType":null,"uploadFileName":null,"userName":"yf"}
         * k2 : {"bankNo":null,"birthday":"","code":"S0001","createTime":"2016-12-14T17:15:56","credit":"G001-湖北省交通运输厅高速公路路政执法总队京珠支队第二大队","email":"yf@126.com","id":2,"mobilephone":"","name":"叶飞","names":"S0001-叶飞","notice":"1","operNames":null,"orgCode":null,"paramsObj":null,"phone":"","pwdTime":null,"remark":null,"sex":"1","state":"1","stateContent":"启用","upload":null,"uploadContentType":null,"uploadFileName":null,"userName":"yf"}
         * length : 18.1
         * modelContent : ["超限处罚","/tps/punishAction!"]
         * name : null
         * names : S0089-null
         * o1 : {"bankNo":null,"birthday":"","code":"S0001","createTime":"2016-12-14T17:15:56","credit":"G001-湖北省交通运输厅高速公路路政执法总队京珠支队第二大队","email":"yf@126.com","id":2,"mobilephone":"","name":"叶飞","names":"S0001-叶飞","notice":"1","operNames":null,"orgCode":null,"paramsObj":null,"phone":"","pwdTime":null,"remark":null,"sex":"1","state":"1","stateContent":"启用","upload":null,"uploadContentType":null,"uploadFileName":null,"userName":"yf"}
         * o2 : {"bankNo":null,"birthday":"","code":"S0001","createTime":"2016-12-14T17:15:56","credit":"G001-湖北省交通运输厅高速公路路政执法总队京珠支队第二大队","email":"yf@126.com","id":2,"mobilephone":"","name":"叶飞","names":"S0001-叶飞","notice":"1","operNames":null,"orgCode":null,"paramsObj":null,"phone":"","pwdTime":null,"remark":null,"sex":"1","state":"1","stateContent":"启用","upload":null,"uploadContentType":null,"uploadFileName":null,"userName":"yf"}
         * operNames : S0089-测试有权限
         * orgCode : 027
         * overHeight :
         * overLength :
         * overWeight : -46.45
         * overWidth :
         * overrunNo : 666
         * p0 : /
         * p1 : /
         * p10 : /
         * p11 : /
         * p2 : /
         * p3 : /
         * p4 : /
         * p5 : /
         * p6 : /
         * p7 : /
         * p8 : /
         * p9 : /
         * paramsObj : null
         * party : 666
         * proof : 现场照片
         * proofName : 现场照片
         * reason :
         * remark : null
         * site : {"checkPlace":"鄂北收费站02号车道","code":null,"createTime":"2017-03-30T08:57:26","id":57,"modelContent":null,"name":"G20","names":"null-G20","operNames":null,"orgCode":null,"paramsObj":null,"remark":null,"shipDirection":"武汉","state":null,"stateContent":"历史"}
         * source : 群众举报
         * state : 2
         * stateContent : 冻结
         * stateName : 未结案
         * tpsCase : {"beginTime1":null,"beginTime2":null,"beginTime3":"2017-3-20","beginTime4":"2017-3-20","d1":"2017年3月20日","endTime1":"2017-3-20","endTime2":null,"endTime3":"2017-3-20","endTime4":"2017-3-20","id":57,"t1":"","t3":"","t4":"","te3":"","te4":""}
         * tpsGoods : {"code":null,"createTime":"2017-03-30T08:57:26","endPlace":"666","goods":"煤炭","id":57,"modelContent":null,"name":"666","names":"null-666","operNames":null,"orgCode":null,"paramsObj":null,"phone":"666","remark":null,"startPlace":"666","state":null,"stateContent":"历史"}
         * weight : 2.55
         * width : 2.55
         * year : 2017
         */

        private String amount;
        private double amt;
        private CarBean car;
        private CarTypeBean carType;
        private String checkDate;
        private Object checkOper;
        private String checkState;
        private String checkStateName;
        private String code;
        private String codes;
        private Object company;
        private String createTime;
        private String d2;
        private Object discuss;
        private DriverBean driver;
        private String dutyTime;
        private double height;
        private int id;
        private Object invoiceNo;
        private String isCompany;
        private String isUninstall;
        private K1Bean k1;
        private K2Bean k2;
        private double length;
        private Object name;
        private String names;
        private O1Bean o1;
        private O2Bean o2;
        private String operNames;
        private String orgCode;
        private String overHeight;
        private String overLength;
        private String overWeight;
        private String overWidth;
        private String overrunNo;
        private String p0;
        private String p1;
        private String p10;
        private String p11;
        private String p2;
        private String p3;
        private String p4;
        private String p5;
        private String p6;
        private String p7;
        private String p8;
        private String p9;
        private Object paramsObj;
        private String party;
        private String proof;
        private String proofName;
        private String reason;
        private Object remark;
        private SiteBean site;
        private String source;
        private String state;
        private String stateContent;
        private String stateName;
        private TpsCaseBean tpsCase;
        private TpsGoodsBean tpsGoods;
        private double weight;
        private double width;
        private int year;
        private List<String> modelContent;

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public double getAmt() {
            return amt;
        }

        public void setAmt(double amt) {
            this.amt = amt;
        }

        public CarBean getCar() {
            return car;
        }

        public void setCar(CarBean car) {
            this.car = car;
        }

        public CarTypeBean getCarType() {
            return carType;
        }

        public void setCarType(CarTypeBean carType) {
            this.carType = carType;
        }

        public String getCheckDate() {
            return checkDate;
        }

        public void setCheckDate(String checkDate) {
            this.checkDate = checkDate;
        }

        public Object getCheckOper() {
            return checkOper;
        }

        public void setCheckOper(Object checkOper) {
            this.checkOper = checkOper;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodes() {
            return codes;
        }

        public void setCodes(String codes) {
            this.codes = codes;
        }

        public Object getCompany() {
            return company;
        }

        public void setCompany(Object company) {
            this.company = company;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getD2() {
            return d2;
        }

        public void setD2(String d2) {
            this.d2 = d2;
        }

        public Object getDiscuss() {
            return discuss;
        }

        public void setDiscuss(Object discuss) {
            this.discuss = discuss;
        }

        public DriverBean getDriver() {
            return driver;
        }

        public void setDriver(DriverBean driver) {
            this.driver = driver;
        }

        public String getDutyTime() {
            return dutyTime;
        }

        public void setDutyTime(String dutyTime) {
            this.dutyTime = dutyTime;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getInvoiceNo() {
            return invoiceNo;
        }

        public void setInvoiceNo(Object invoiceNo) {
            this.invoiceNo = invoiceNo;
        }

        public String getIsCompany() {
            return isCompany;
        }

        public void setIsCompany(String isCompany) {
            this.isCompany = isCompany;
        }

        public String getIsUninstall() {
            return isUninstall;
        }

        public void setIsUninstall(String isUninstall) {
            this.isUninstall = isUninstall;
        }

        public K1Bean getK1() {
            return k1;
        }

        public void setK1(K1Bean k1) {
            this.k1 = k1;
        }

        public K2Bean getK2() {
            return k2;
        }

        public void setK2(K2Bean k2) {
            this.k2 = k2;
        }

        public double getLength() {
            return length;
        }

        public void setLength(double length) {
            this.length = length;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public String getNames() {
            return names;
        }

        public void setNames(String names) {
            this.names = names;
        }

        public O1Bean getO1() {
            return o1;
        }

        public void setO1(O1Bean o1) {
            this.o1 = o1;
        }

        public O2Bean getO2() {
            return o2;
        }

        public void setO2(O2Bean o2) {
            this.o2 = o2;
        }

        public String getOperNames() {
            return operNames;
        }

        public void setOperNames(String operNames) {
            this.operNames = operNames;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getOverHeight() {
            return overHeight;
        }

        public void setOverHeight(String overHeight) {
            this.overHeight = overHeight;
        }

        public String getOverLength() {
            return overLength;
        }

        public void setOverLength(String overLength) {
            this.overLength = overLength;
        }

        public String getOverWeight() {
            return overWeight;
        }

        public void setOverWeight(String overWeight) {
            this.overWeight = overWeight;
        }

        public String getOverWidth() {
            return overWidth;
        }

        public void setOverWidth(String overWidth) {
            this.overWidth = overWidth;
        }

        public String getOverrunNo() {
            return overrunNo;
        }

        public void setOverrunNo(String overrunNo) {
            this.overrunNo = overrunNo;
        }

        public String getP0() {
            return p0;
        }

        public void setP0(String p0) {
            this.p0 = p0;
        }

        public String getP1() {
            return p1;
        }

        public void setP1(String p1) {
            this.p1 = p1;
        }

        public String getP10() {
            return p10;
        }

        public void setP10(String p10) {
            this.p10 = p10;
        }

        public String getP11() {
            return p11;
        }

        public void setP11(String p11) {
            this.p11 = p11;
        }

        public String getP2() {
            return p2;
        }

        public void setP2(String p2) {
            this.p2 = p2;
        }

        public String getP3() {
            return p3;
        }

        public void setP3(String p3) {
            this.p3 = p3;
        }

        public String getP4() {
            return p4;
        }

        public void setP4(String p4) {
            this.p4 = p4;
        }

        public String getP5() {
            return p5;
        }

        public void setP5(String p5) {
            this.p5 = p5;
        }

        public String getP6() {
            return p6;
        }

        public void setP6(String p6) {
            this.p6 = p6;
        }

        public String getP7() {
            return p7;
        }

        public void setP7(String p7) {
            this.p7 = p7;
        }

        public String getP8() {
            return p8;
        }

        public void setP8(String p8) {
            this.p8 = p8;
        }

        public String getP9() {
            return p9;
        }

        public void setP9(String p9) {
            this.p9 = p9;
        }

        public Object getParamsObj() {
            return paramsObj;
        }

        public void setParamsObj(Object paramsObj) {
            this.paramsObj = paramsObj;
        }

        public String getParty() {
            return party;
        }

        public void setParty(String party) {
            this.party = party;
        }

        public String getProof() {
            return proof;
        }

        public void setProof(String proof) {
            this.proof = proof;
        }

        public String getProofName() {
            return proofName;
        }

        public void setProofName(String proofName) {
            this.proofName = proofName;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public SiteBean getSite() {
            return site;
        }

        public void setSite(SiteBean site) {
            this.site = site;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStateContent() {
            return stateContent;
        }

        public void setStateContent(String stateContent) {
            this.stateContent = stateContent;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public TpsCaseBean getTpsCase() {
            return tpsCase;
        }

        public void setTpsCase(TpsCaseBean tpsCase) {
            this.tpsCase = tpsCase;
        }

        public TpsGoodsBean getTpsGoods() {
            return tpsGoods;
        }

        public void setTpsGoods(TpsGoodsBean tpsGoods) {
            this.tpsGoods = tpsGoods;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public List<String> getModelContent() {
            return modelContent;
        }

        public void setModelContent(List<String> modelContent) {
            this.modelContent = modelContent;
        }

        public static class CarBean implements Serializable {
            /**
             * addr : 666
             * id : 57
             * lawAddr : 666
             * lawName : 666
             * lawOper : 666
             * lawPhone : 666
             * lawPost : 666
             * no1 : 666
             * no2 : 666
             * no3 : 666
             * owner : 666
             * phone : 666
             */

            private String addr;
            private int id;
            private String lawAddr;
            private String lawName;
            private String lawOper;
            private String lawPhone;
            private String lawPost;
            private String no1;
            private String no2;
            private String no3;
            private String owner;
            private String phone;

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLawAddr() {
                return lawAddr;
            }

            public void setLawAddr(String lawAddr) {
                this.lawAddr = lawAddr;
            }

            public String getLawName() {
                return lawName;
            }

            public void setLawName(String lawName) {
                this.lawName = lawName;
            }

            public String getLawOper() {
                return lawOper;
            }

            public void setLawOper(String lawOper) {
                this.lawOper = lawOper;
            }

            public String getLawPhone() {
                return lawPhone;
            }

            public void setLawPhone(String lawPhone) {
                this.lawPhone = lawPhone;
            }

            public String getLawPost() {
                return lawPost;
            }

            public void setLawPost(String lawPost) {
                this.lawPost = lawPost;
            }

            public String getNo1() {
                return no1;
            }

            public void setNo1(String no1) {
                this.no1 = no1;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getNo3() {
                return no3;
            }

            public void setNo3(String no3) {
                this.no3 = no3;
            }

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }

        public static class CarTypeBean implements Serializable {
            /**
             * axisNum : 6
             * axisType : 双
             * code : 004
             * createTime : 2017-01-06T16:41:47
             * height : 4.0
             * id : 4
             * length : 18.1
             * limit : 49.0
             * modelContent : ["车型信息","/tps/carTypeAction!"]
             * name : null
             * names : 004-null
             * operNames : null
             * orgCode : 027
             * paramsObj : null
             * remark : null
             * state : 1
             * stateContent : 启用
             * type : 铰接列车
             * typeImg : /upload/file/027/2017/1/6/004 16_41_47.png
             * upload : null
             * uploadContentType : null
             * uploadFileName : null
             * wheelNum : 12
             * width : 2.55
             */

            private String axisNum;
            private String axisType;
            private String code;
            private String createTime;
            private double height;
            private int id;
            private double length;
            private double limit;
            private Object name;
            private String names;
            private Object operNames;
            private String orgCode;
            private Object paramsObj;
            private Object remark;
            private String state;
            private String stateContent;
            private String type;
            private String typeImg;
            private Object upload;
            private Object uploadContentType;
            private Object uploadFileName;
            private String wheelNum;
            private double width;
            private List<String> modelContent;

            public String getAxisNum() {
                return axisNum;
            }

            public void setAxisNum(String axisNum) {
                this.axisNum = axisNum;
            }

            public String getAxisType() {
                return axisType;
            }

            public void setAxisType(String axisType) {
                this.axisType = axisType;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public double getHeight() {
                return height;
            }

            public void setHeight(double height) {
                this.height = height;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getLength() {
                return length;
            }

            public void setLength(double length) {
                this.length = length;
            }

            public double getLimit() {
                return limit;
            }

            public void setLimit(double limit) {
                this.limit = limit;
            }

            public Object getName() {
                return name;
            }

            public void setName(Object name) {
                this.name = name;
            }

            public String getNames() {
                return names;
            }

            public void setNames(String names) {
                this.names = names;
            }

            public Object getOperNames() {
                return operNames;
            }

            public void setOperNames(Object operNames) {
                this.operNames = operNames;
            }

            public String getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(String orgCode) {
                this.orgCode = orgCode;
            }

            public Object getParamsObj() {
                return paramsObj;
            }

            public void setParamsObj(Object paramsObj) {
                this.paramsObj = paramsObj;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getStateContent() {
                return stateContent;
            }

            public void setStateContent(String stateContent) {
                this.stateContent = stateContent;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTypeImg() {
                return typeImg;
            }

            public void setTypeImg(String typeImg) {
                this.typeImg = typeImg;
            }

            public Object getUpload() {
                return upload;
            }

            public void setUpload(Object upload) {
                this.upload = upload;
            }

            public Object getUploadContentType() {
                return uploadContentType;
            }

            public void setUploadContentType(Object uploadContentType) {
                this.uploadContentType = uploadContentType;
            }

            public Object getUploadFileName() {
                return uploadFileName;
            }

            public void setUploadFileName(Object uploadFileName) {
                this.uploadFileName = uploadFileName;
            }

            public String getWheelNum() {
                return wheelNum;
            }

            public void setWheelNum(String wheelNum) {
                this.wheelNum = wheelNum;
            }

            public double getWidth() {
                return width;
            }

            public void setWidth(double width) {
                this.width = width;
            }

            public List<String> getModelContent() {
                return modelContent;
            }

            public void setModelContent(List<String> modelContent) {
                this.modelContent = modelContent;
            }
        }

        public static class DriverBean implements Serializable {
            /**
             * addr : 666
             * age : 0
             * caseRel : 666
             * certId : 666
             * cntDriver : 0
             * code : null
             * createTime : 2017-03-30T08:57:26
             * driverAddr : 666
             * id : 57
             * ids : 666
             * job : 666
             * modelContent : null
             * name : 王成
             * names : null-王成
             * no1 : 666
             * operNames : null
             * orgCode : null
             * paramsObj : null
             * phone : 666
             * post : 666
             * remark : null
             * sex : 男
             * state : null
             * stateContent : 历史
             */

            private String addr;
            private int age;
            private String caseRel;
            private String certId;
            private int cntDriver;
            private Object code;
            private String createTime;
            private String driverAddr;
            private int id;
            private String ids;
            private String job;
            private Object modelContent;
            private String name;
            private String names;
            private String no1;
            private Object operNames;
            private Object orgCode;
            private Object paramsObj;
            private String phone;
            private String post;
            private Object remark;
            private String sex;
            private Object state;
            private String stateContent;

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public String getCaseRel() {
                return caseRel;
            }

            public void setCaseRel(String caseRel) {
                this.caseRel = caseRel;
            }

            public String getCertId() {
                return certId;
            }

            public void setCertId(String certId) {
                this.certId = certId;
            }

            public int getCntDriver() {
                return cntDriver;
            }

            public void setCntDriver(int cntDriver) {
                this.cntDriver = cntDriver;
            }

            public Object getCode() {
                return code;
            }

            public void setCode(Object code) {
                this.code = code;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDriverAddr() {
                return driverAddr;
            }

            public void setDriverAddr(String driverAddr) {
                this.driverAddr = driverAddr;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getIds() {
                return ids;
            }

            public void setIds(String ids) {
                this.ids = ids;
            }

            public String getJob() {
                return job;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public Object getModelContent() {
                return modelContent;
            }

            public void setModelContent(Object modelContent) {
                this.modelContent = modelContent;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNames() {
                return names;
            }

            public void setNames(String names) {
                this.names = names;
            }

            public String getNo1() {
                return no1;
            }

            public void setNo1(String no1) {
                this.no1 = no1;
            }

            public Object getOperNames() {
                return operNames;
            }

            public void setOperNames(Object operNames) {
                this.operNames = operNames;
            }

            public Object getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(Object orgCode) {
                this.orgCode = orgCode;
            }

            public Object getParamsObj() {
                return paramsObj;
            }

            public void setParamsObj(Object paramsObj) {
                this.paramsObj = paramsObj;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPost() {
                return post;
            }

            public void setPost(String post) {
                this.post = post;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public Object getState() {
                return state;
            }

            public void setState(Object state) {
                this.state = state;
            }

            public String getStateContent() {
                return stateContent;
            }

            public void setStateContent(String stateContent) {
                this.stateContent = stateContent;
            }
        }

        public static class K1Bean implements Serializable{
            /**
             * bankNo : null
             * birthday :
             * code : S0001
             * createTime : 2016-12-14T17:15:56
             * credit : G001-湖北省交通运输厅高速公路路政执法总队京珠支队第二大队
             * email : yf@126.com
             * id : 2
             * mobilephone :
             * name : 叶飞
             * names : S0001-叶飞
             * notice : 1
             * operNames : null
             * orgCode : null
             * paramsObj : null
             * phone :
             * pwdTime : null
             * remark : null
             * sex : 1
             * state : 1
             * stateContent : 启用
             * upload : null
             * uploadContentType : null
             * uploadFileName : null
             * userName : yf
             */

            private Object bankNo;
            private String birthday;
            private String code;
            private String createTime;
            private String credit;
            private String email;
            private int id;
            private String mobilephone;
            private String name;
            private String names;
            private String notice;
            private Object operNames;
            private Object orgCode;
            private Object paramsObj;
            private String phone;
            private Object pwdTime;
            private Object remark;
            private String sex;
            private String state;
            private String stateContent;
            private Object upload;
            private Object uploadContentType;
            private Object uploadFileName;
            private String userName;

            public Object getBankNo() {
                return bankNo;
            }

            public void setBankNo(Object bankNo) {
                this.bankNo = bankNo;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMobilephone() {
                return mobilephone;
            }

            public void setMobilephone(String mobilephone) {
                this.mobilephone = mobilephone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNames() {
                return names;
            }

            public void setNames(String names) {
                this.names = names;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }

            public Object getOperNames() {
                return operNames;
            }

            public void setOperNames(Object operNames) {
                this.operNames = operNames;
            }

            public Object getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(Object orgCode) {
                this.orgCode = orgCode;
            }

            public Object getParamsObj() {
                return paramsObj;
            }

            public void setParamsObj(Object paramsObj) {
                this.paramsObj = paramsObj;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getPwdTime() {
                return pwdTime;
            }

            public void setPwdTime(Object pwdTime) {
                this.pwdTime = pwdTime;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getStateContent() {
                return stateContent;
            }

            public void setStateContent(String stateContent) {
                this.stateContent = stateContent;
            }

            public Object getUpload() {
                return upload;
            }

            public void setUpload(Object upload) {
                this.upload = upload;
            }

            public Object getUploadContentType() {
                return uploadContentType;
            }

            public void setUploadContentType(Object uploadContentType) {
                this.uploadContentType = uploadContentType;
            }

            public Object getUploadFileName() {
                return uploadFileName;
            }

            public void setUploadFileName(Object uploadFileName) {
                this.uploadFileName = uploadFileName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class K2Bean implements Serializable {
            /**
             * bankNo : null
             * birthday :
             * code : S0001
             * createTime : 2016-12-14T17:15:56
             * credit : G001-湖北省交通运输厅高速公路路政执法总队京珠支队第二大队
             * email : yf@126.com
             * id : 2
             * mobilephone :
             * name : 叶飞
             * names : S0001-叶飞
             * notice : 1
             * operNames : null
             * orgCode : null
             * paramsObj : null
             * phone :
             * pwdTime : null
             * remark : null
             * sex : 1
             * state : 1
             * stateContent : 启用
             * upload : null
             * uploadContentType : null
             * uploadFileName : null
             * userName : yf
             */

            private Object bankNo;
            private String birthday;
            private String code;
            private String createTime;
            private String credit;
            private String email;
            private int id;
            private String mobilephone;
            private String name;
            private String names;
            private String notice;
            private Object operNames;
            private Object orgCode;
            private Object paramsObj;
            private String phone;
            private Object pwdTime;
            private Object remark;
            private String sex;
            private String state;
            private String stateContent;
            private Object upload;
            private Object uploadContentType;
            private Object uploadFileName;
            private String userName;

            public Object getBankNo() {
                return bankNo;
            }

            public void setBankNo(Object bankNo) {
                this.bankNo = bankNo;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMobilephone() {
                return mobilephone;
            }

            public void setMobilephone(String mobilephone) {
                this.mobilephone = mobilephone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNames() {
                return names;
            }

            public void setNames(String names) {
                this.names = names;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }

            public Object getOperNames() {
                return operNames;
            }

            public void setOperNames(Object operNames) {
                this.operNames = operNames;
            }

            public Object getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(Object orgCode) {
                this.orgCode = orgCode;
            }

            public Object getParamsObj() {
                return paramsObj;
            }

            public void setParamsObj(Object paramsObj) {
                this.paramsObj = paramsObj;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getPwdTime() {
                return pwdTime;
            }

            public void setPwdTime(Object pwdTime) {
                this.pwdTime = pwdTime;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getStateContent() {
                return stateContent;
            }

            public void setStateContent(String stateContent) {
                this.stateContent = stateContent;
            }

            public Object getUpload() {
                return upload;
            }

            public void setUpload(Object upload) {
                this.upload = upload;
            }

            public Object getUploadContentType() {
                return uploadContentType;
            }

            public void setUploadContentType(Object uploadContentType) {
                this.uploadContentType = uploadContentType;
            }

            public Object getUploadFileName() {
                return uploadFileName;
            }

            public void setUploadFileName(Object uploadFileName) {
                this.uploadFileName = uploadFileName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class O1Bean implements Serializable {
            /**
             * bankNo : null
             * birthday :
             * code : S0001
             * createTime : 2016-12-14T17:15:56
             * credit : G001-湖北省交通运输厅高速公路路政执法总队京珠支队第二大队
             * email : yf@126.com
             * id : 2
             * mobilephone :
             * name : 叶飞
             * names : S0001-叶飞
             * notice : 1
             * operNames : null
             * orgCode : null
             * paramsObj : null
             * phone :
             * pwdTime : null
             * remark : null
             * sex : 1
             * state : 1
             * stateContent : 启用
             * upload : null
             * uploadContentType : null
             * uploadFileName : null
             * userName : yf
             */

            private Object bankNo;
            private String birthday;
            private String code;
            private String createTime;
            private String credit;
            private String email;
            private int id;
            private String mobilephone;
            private String name;
            private String names;
            private String notice;
            private Object operNames;
            private Object orgCode;
            private Object paramsObj;
            private String phone;
            private Object pwdTime;
            private Object remark;
            private String sex;
            private String state;
            private String stateContent;
            private Object upload;
            private Object uploadContentType;
            private Object uploadFileName;
            private String userName;

            public Object getBankNo() {
                return bankNo;
            }

            public void setBankNo(Object bankNo) {
                this.bankNo = bankNo;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMobilephone() {
                return mobilephone;
            }

            public void setMobilephone(String mobilephone) {
                this.mobilephone = mobilephone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNames() {
                return names;
            }

            public void setNames(String names) {
                this.names = names;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }

            public Object getOperNames() {
                return operNames;
            }

            public void setOperNames(Object operNames) {
                this.operNames = operNames;
            }

            public Object getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(Object orgCode) {
                this.orgCode = orgCode;
            }

            public Object getParamsObj() {
                return paramsObj;
            }

            public void setParamsObj(Object paramsObj) {
                this.paramsObj = paramsObj;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getPwdTime() {
                return pwdTime;
            }

            public void setPwdTime(Object pwdTime) {
                this.pwdTime = pwdTime;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getStateContent() {
                return stateContent;
            }

            public void setStateContent(String stateContent) {
                this.stateContent = stateContent;
            }

            public Object getUpload() {
                return upload;
            }

            public void setUpload(Object upload) {
                this.upload = upload;
            }

            public Object getUploadContentType() {
                return uploadContentType;
            }

            public void setUploadContentType(Object uploadContentType) {
                this.uploadContentType = uploadContentType;
            }

            public Object getUploadFileName() {
                return uploadFileName;
            }

            public void setUploadFileName(Object uploadFileName) {
                this.uploadFileName = uploadFileName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class O2Bean implements Serializable {
            /**
             * bankNo : null
             * birthday :
             * code : S0001
             * createTime : 2016-12-14T17:15:56
             * credit : G001-湖北省交通运输厅高速公路路政执法总队京珠支队第二大队
             * email : yf@126.com
             * id : 2
             * mobilephone :
             * name : 叶飞
             * names : S0001-叶飞
             * notice : 1
             * operNames : null
             * orgCode : null
             * paramsObj : null
             * phone :
             * pwdTime : null
             * remark : null
             * sex : 1
             * state : 1
             * stateContent : 启用
             * upload : null
             * uploadContentType : null
             * uploadFileName : null
             * userName : yf
             */

            private Object bankNo;
            private String birthday;
            private String code;
            private String createTime;
            private String credit;
            private String email;
            private int id;
            private String mobilephone;
            private String name;
            private String names;
            private String notice;
            private Object operNames;
            private Object orgCode;
            private Object paramsObj;
            private String phone;
            private Object pwdTime;
            private Object remark;
            private String sex;
            private String state;
            private String stateContent;
            private Object upload;
            private Object uploadContentType;
            private Object uploadFileName;
            private String userName;

            public Object getBankNo() {
                return bankNo;
            }

            public void setBankNo(Object bankNo) {
                this.bankNo = bankNo;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMobilephone() {
                return mobilephone;
            }

            public void setMobilephone(String mobilephone) {
                this.mobilephone = mobilephone;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNames() {
                return names;
            }

            public void setNames(String names) {
                this.names = names;
            }

            public String getNotice() {
                return notice;
            }

            public void setNotice(String notice) {
                this.notice = notice;
            }

            public Object getOperNames() {
                return operNames;
            }

            public void setOperNames(Object operNames) {
                this.operNames = operNames;
            }

            public Object getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(Object orgCode) {
                this.orgCode = orgCode;
            }

            public Object getParamsObj() {
                return paramsObj;
            }

            public void setParamsObj(Object paramsObj) {
                this.paramsObj = paramsObj;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getPwdTime() {
                return pwdTime;
            }

            public void setPwdTime(Object pwdTime) {
                this.pwdTime = pwdTime;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getStateContent() {
                return stateContent;
            }

            public void setStateContent(String stateContent) {
                this.stateContent = stateContent;
            }

            public Object getUpload() {
                return upload;
            }

            public void setUpload(Object upload) {
                this.upload = upload;
            }

            public Object getUploadContentType() {
                return uploadContentType;
            }

            public void setUploadContentType(Object uploadContentType) {
                this.uploadContentType = uploadContentType;
            }

            public Object getUploadFileName() {
                return uploadFileName;
            }

            public void setUploadFileName(Object uploadFileName) {
                this.uploadFileName = uploadFileName;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }

        public static class SiteBean implements Serializable {
            /**
             * checkPlace : 鄂北收费站02号车道
             * code : null
             * createTime : 2017-03-30T08:57:26
             * id : 57
             * modelContent : null
             * name : G20
             * names : null-G20
             * operNames : null
             * orgCode : null
             * paramsObj : null
             * remark : null
             * shipDirection : 武汉
             * state : null
             * stateContent : 历史
             */

            private String checkPlace;
            private Object code;
            private String createTime;
            private int id;
            private Object modelContent;
            private String name;
            private String names;
            private Object operNames;
            private Object orgCode;
            private Object paramsObj;
            private Object remark;
            private String shipDirection;
            private Object state;
            private String stateContent;

            public String getCheckPlace() {
                return checkPlace;
            }

            public void setCheckPlace(String checkPlace) {
                this.checkPlace = checkPlace;
            }

            public Object getCode() {
                return code;
            }

            public void setCode(Object code) {
                this.code = code;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getModelContent() {
                return modelContent;
            }

            public void setModelContent(Object modelContent) {
                this.modelContent = modelContent;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNames() {
                return names;
            }

            public void setNames(String names) {
                this.names = names;
            }

            public Object getOperNames() {
                return operNames;
            }

            public void setOperNames(Object operNames) {
                this.operNames = operNames;
            }

            public Object getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(Object orgCode) {
                this.orgCode = orgCode;
            }

            public Object getParamsObj() {
                return paramsObj;
            }

            public void setParamsObj(Object paramsObj) {
                this.paramsObj = paramsObj;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getShipDirection() {
                return shipDirection;
            }

            public void setShipDirection(String shipDirection) {
                this.shipDirection = shipDirection;
            }

            public Object getState() {
                return state;
            }

            public void setState(Object state) {
                this.state = state;
            }

            public String getStateContent() {
                return stateContent;
            }

            public void setStateContent(String stateContent) {
                this.stateContent = stateContent;
            }
        }

        public static class TpsCaseBean implements Serializable {
            /**
             * beginTime1 : null
             * beginTime2 : null
             * beginTime3 : 2017-3-20
             * beginTime4 : 2017-3-20
             * d1 : 2017年3月20日
             * endTime1 : 2017-3-20
             * endTime2 : null
             * endTime3 : 2017-3-20
             * endTime4 : 2017-3-20
             * id : 57
             * t1 :
             * t3 :
             * t4 :
             * te3 :
             * te4 :
             */

            private Object beginTime1;
            private Object beginTime2;
            private String beginTime3;
            private String beginTime4;
            private String d1;
            private String endTime1;
            private Object endTime2;
            private String endTime3;
            private String endTime4;
            private int id;
            private String t1;
            private String t3;
            private String t4;
            private String te3;
            private String te4;

            public Object getBeginTime1() {
                return beginTime1;
            }

            public void setBeginTime1(Object beginTime1) {
                this.beginTime1 = beginTime1;
            }

            public Object getBeginTime2() {
                return beginTime2;
            }

            public void setBeginTime2(Object beginTime2) {
                this.beginTime2 = beginTime2;
            }

            public String getBeginTime3() {
                return beginTime3;
            }

            public void setBeginTime3(String beginTime3) {
                this.beginTime3 = beginTime3;
            }

            public String getBeginTime4() {
                return beginTime4;
            }

            public void setBeginTime4(String beginTime4) {
                this.beginTime4 = beginTime4;
            }

            public String getD1() {
                return d1;
            }

            public void setD1(String d1) {
                this.d1 = d1;
            }

            public String getEndTime1() {
                return endTime1;
            }

            public void setEndTime1(String endTime1) {
                this.endTime1 = endTime1;
            }

            public Object getEndTime2() {
                return endTime2;
            }

            public void setEndTime2(Object endTime2) {
                this.endTime2 = endTime2;
            }

            public String getEndTime3() {
                return endTime3;
            }

            public void setEndTime3(String endTime3) {
                this.endTime3 = endTime3;
            }

            public String getEndTime4() {
                return endTime4;
            }

            public void setEndTime4(String endTime4) {
                this.endTime4 = endTime4;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getT1() {
                return t1;
            }

            public void setT1(String t1) {
                this.t1 = t1;
            }

            public String getT3() {
                return t3;
            }

            public void setT3(String t3) {
                this.t3 = t3;
            }

            public String getT4() {
                return t4;
            }

            public void setT4(String t4) {
                this.t4 = t4;
            }

            public String getTe3() {
                return te3;
            }

            public void setTe3(String te3) {
                this.te3 = te3;
            }

            public String getTe4() {
                return te4;
            }

            public void setTe4(String te4) {
                this.te4 = te4;
            }
        }

        public static class TpsGoodsBean implements Serializable {
            /**
             * code : null
             * createTime : 2017-03-30T08:57:26
             * endPlace : 666
             * goods : 煤炭
             * id : 57
             * modelContent : null
             * name : 666
             * names : null-666
             * operNames : null
             * orgCode : null
             * paramsObj : null
             * phone : 666
             * remark : null
             * startPlace : 666
             * state : null
             * stateContent : 历史
             */

            private Object code;
            private String createTime;
            private String endPlace;
            private String goods;
            private int id;
            private Object modelContent;
            private String name;
            private String names;
            private Object operNames;
            private Object orgCode;
            private Object paramsObj;
            private String phone;
            private Object remark;
            private String startPlace;
            private Object state;
            private String stateContent;

            public Object getCode() {
                return code;
            }

            public void setCode(Object code) {
                this.code = code;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getEndPlace() {
                return endPlace;
            }

            public void setEndPlace(String endPlace) {
                this.endPlace = endPlace;
            }

            public String getGoods() {
                return goods;
            }

            public void setGoods(String goods) {
                this.goods = goods;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public Object getModelContent() {
                return modelContent;
            }

            public void setModelContent(Object modelContent) {
                this.modelContent = modelContent;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNames() {
                return names;
            }

            public void setNames(String names) {
                this.names = names;
            }

            public Object getOperNames() {
                return operNames;
            }

            public void setOperNames(Object operNames) {
                this.operNames = operNames;
            }

            public Object getOrgCode() {
                return orgCode;
            }

            public void setOrgCode(Object orgCode) {
                this.orgCode = orgCode;
            }

            public Object getParamsObj() {
                return paramsObj;
            }

            public void setParamsObj(Object paramsObj) {
                this.paramsObj = paramsObj;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getStartPlace() {
                return startPlace;
            }

            public void setStartPlace(String startPlace) {
                this.startPlace = startPlace;
            }

            public Object getState() {
                return state;
            }

            public void setState(Object state) {
                this.state = state;
            }

            public String getStateContent() {
                return stateContent;
            }

            public void setStateContent(String stateContent) {
                this.stateContent = stateContent;
            }
        }
    }

}
