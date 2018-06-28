package com.android.hcbd.overruntransportation.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guocheng on 2017/4/5.
 */

public class OverLoadDataInfo implements Serializable {
    private LegalNameInfo.PageInfo pageInfo;
    private List<LegalNameInfo.DataInfo> dataInfoList;

    public LegalNameInfo.PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(LegalNameInfo.PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<LegalNameInfo.DataInfo> getDataInfoList() {
        return dataInfoList;
    }

    public void setDataInfoList(List<LegalNameInfo.DataInfo> dataInfoList) {
        this.dataInfoList = dataInfoList;
    }

    public static class PageInfo implements Serializable {

        /**
         * currentPage : 1
         * next : 0
         * nowPageSize : 5
         * pageSize : 25
         * previous : 0
         * totalSize : 1
         * url : null
         */

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
         * a1 : 11.0
         * a2 : 11.0
         * a3 : 11.0
         * a4 : 11.0
         * a5 : 11.0
         * beginTime : null
         * code : D000001
         * company : {"addr":"湖北省武汉市古田四路21号","bank":"建行武汉汉口支行","bankAcc":"湖北省交通厅高速公路路政执法总队","bankAddr":"银行地址","bankNo":"42001865108050001320","code":"02","confirmTeam":"湖北省人民政府或者湖北省交通运输厅","createTime":"2017-04-05T09:10:16","id":1,"lawOper":"法定 ","modelContent":["单位信息","/tps/companyAction!"],"name":"湖北省交通运输厅高速公路路政执法总队","names":"02-京珠支队第二大队","operNames":null,"orgCode":"027","paramsObj":null,"phone":"027-83450092","post":"430000","remark":null,"shortName":"京珠支队第二大队","state":"1","stateContent":"启用","teamName":"京珠支队第二大队"}
         * createTime : 2017-04-05T16:55:09
         * d1 : 11
         * d10 : 11
         * d11 : 11
         * d12 : 11
         * d13 : 11
         * d14 : 11
         * d15 : 11
         * d16 : 11
         * d17 : 11
         * d2 : 11
         * d3 : 11
         * d4 : 11
         * d5 : 11
         * d6 : 11
         * d7 : 11
         * d8 : 11
         * d9 : 11
         * dataTime : 2017-04-05
         * endTime : null
         * id : 1
         * model : null
         * modelContent : ["治超信息汇总表","/tps/dataAction!"]
         * name : null
         * names : D000001-null
         * operNames : S007-App测试账户
         * orgCode : 027
         * paramsObj : null
         * remark : null
         * state : 1
         * stateContent : 启用
         */

        private double a1;
        private double a2;
        private double a3;
        private double a4;
        private double a5;
        private Object beginTime;
        private String code;
        private CompanyBean company;
        private String createTime;
        private int d1;
        private int d10;
        private int d11;
        private int d12;
        private int d13;
        private int d14;
        private int d15;
        private int d16;
        private int d17;
        private int d2;
        private int d3;
        private int d4;
        private int d5;
        private int d6;
        private int d7;
        private int d8;
        private int d9;
        private String dataTime;
        private Object endTime;
        private int id;
        private Object model;
        private Object name;
        private String names;
        private String operNames;
        private String orgCode;
        private Object paramsObj;
        private Object remark;
        private String state;
        private String stateContent;
        private List<String> modelContent;

        public double getA1() {
            return a1;
        }

        public void setA1(double a1) {
            this.a1 = a1;
        }

        public double getA2() {
            return a2;
        }

        public void setA2(double a2) {
            this.a2 = a2;
        }

        public double getA3() {
            return a3;
        }

        public void setA3(double a3) {
            this.a3 = a3;
        }

        public double getA4() {
            return a4;
        }

        public void setA4(double a4) {
            this.a4 = a4;
        }

        public double getA5() {
            return a5;
        }

        public void setA5(double a5) {
            this.a5 = a5;
        }

        public Object getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(Object beginTime) {
            this.beginTime = beginTime;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public CompanyBean getCompany() {
            return company;
        }

        public void setCompany(CompanyBean company) {
            this.company = company;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getD1() {
            return d1;
        }

        public void setD1(int d1) {
            this.d1 = d1;
        }

        public int getD10() {
            return d10;
        }

        public void setD10(int d10) {
            this.d10 = d10;
        }

        public int getD11() {
            return d11;
        }

        public void setD11(int d11) {
            this.d11 = d11;
        }

        public int getD12() {
            return d12;
        }

        public void setD12(int d12) {
            this.d12 = d12;
        }

        public int getD13() {
            return d13;
        }

        public void setD13(int d13) {
            this.d13 = d13;
        }

        public int getD14() {
            return d14;
        }

        public void setD14(int d14) {
            this.d14 = d14;
        }

        public int getD15() {
            return d15;
        }

        public void setD15(int d15) {
            this.d15 = d15;
        }

        public int getD16() {
            return d16;
        }

        public void setD16(int d16) {
            this.d16 = d16;
        }

        public int getD17() {
            return d17;
        }

        public void setD17(int d17) {
            this.d17 = d17;
        }

        public int getD2() {
            return d2;
        }

        public void setD2(int d2) {
            this.d2 = d2;
        }

        public int getD3() {
            return d3;
        }

        public void setD3(int d3) {
            this.d3 = d3;
        }

        public int getD4() {
            return d4;
        }

        public void setD4(int d4) {
            this.d4 = d4;
        }

        public int getD5() {
            return d5;
        }

        public void setD5(int d5) {
            this.d5 = d5;
        }

        public int getD6() {
            return d6;
        }

        public void setD6(int d6) {
            this.d6 = d6;
        }

        public int getD7() {
            return d7;
        }

        public void setD7(int d7) {
            this.d7 = d7;
        }

        public int getD8() {
            return d8;
        }

        public void setD8(int d8) {
            this.d8 = d8;
        }

        public int getD9() {
            return d9;
        }

        public void setD9(int d9) {
            this.d9 = d9;
        }

        public String getDataTime() {
            return dataTime;
        }

        public void setDataTime(String dataTime) {
            this.dataTime = dataTime;
        }

        public Object getEndTime() {
            return endTime;
        }

        public void setEndTime(Object endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getModel() {
            return model;
        }

        public void setModel(Object model) {
            this.model = model;
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

        public List<String> getModelContent() {
            return modelContent;
        }

        public void setModelContent(List<String> modelContent) {
            this.modelContent = modelContent;
        }

        public static class CompanyBean implements Serializable {
            /**
             * addr : 湖北省武汉市古田四路21号
             * bank : 建行武汉汉口支行
             * bankAcc : 湖北省交通厅高速公路路政执法总队
             * bankAddr : 银行地址
             * bankNo : 42001865108050001320
             * code : 02
             * confirmTeam : 湖北省人民政府或者湖北省交通运输厅
             * createTime : 2017-04-05T09:10:16
             * id : 1
             * lawOper : 法定
             * modelContent : ["单位信息","/tps/companyAction!"]
             * name : 湖北省交通运输厅高速公路路政执法总队
             * names : 02-京珠支队第二大队
             * operNames : null
             * orgCode : 027
             * paramsObj : null
             * phone : 027-83450092
             * post : 430000
             * remark : null
             * shortName : 京珠支队第二大队
             * state : 1
             * stateContent : 启用
             * teamName : 京珠支队第二大队
             */

            private String addr;
            private String bank;
            private String bankAcc;
            private String bankAddr;
            private String bankNo;
            private String code;
            private String confirmTeam;
            private String createTime;
            private int id;
            private String lawOper;
            private String name;
            private String names;
            private Object operNames;
            private String orgCode;
            private Object paramsObj;
            private String phone;
            private String post;
            private Object remark;
            private String shortName;
            private String state;
            private String stateContent;
            private String teamName;
            private List<String> modelContent;

            public String getAddr() {
                return addr;
            }

            public void setAddr(String addr) {
                this.addr = addr;
            }

            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public String getBankAcc() {
                return bankAcc;
            }

            public void setBankAcc(String bankAcc) {
                this.bankAcc = bankAcc;
            }

            public String getBankAddr() {
                return bankAddr;
            }

            public void setBankAddr(String bankAddr) {
                this.bankAddr = bankAddr;
            }

            public String getBankNo() {
                return bankNo;
            }

            public void setBankNo(String bankNo) {
                this.bankNo = bankNo;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getConfirmTeam() {
                return confirmTeam;
            }

            public void setConfirmTeam(String confirmTeam) {
                this.confirmTeam = confirmTeam;
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

            public String getLawOper() {
                return lawOper;
            }

            public void setLawOper(String lawOper) {
                this.lawOper = lawOper;
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

            public String getShortName() {
                return shortName;
            }

            public void setShortName(String shortName) {
                this.shortName = shortName;
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

            public String getTeamName() {
                return teamName;
            }

            public void setTeamName(String teamName) {
                this.teamName = teamName;
            }

            public List<String> getModelContent() {
                return modelContent;
            }

            public void setModelContent(List<String> modelContent) {
                this.modelContent = modelContent;
            }
        }
    }

}
