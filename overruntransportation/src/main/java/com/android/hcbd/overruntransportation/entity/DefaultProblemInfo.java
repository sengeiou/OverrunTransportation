package com.android.hcbd.overruntransportation.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guocheng on 2017/3/28.
 */

public class DefaultProblemInfo implements Serializable{
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

    public static class PageInfo implements Serializable{

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
         * code : 005
         * createTime : 2017-01-13T16:02:07
         * id : 105
         * modelContent : ["分析代码数据","/nsp/typeAction!"]
         * name : 本次运输过程中你是否被相关部门处罚过？
         * names : 005-本次运输过程中你是否被相关部门处罚过？
         * operNames : S0002-管理员
         * orgCode : 027
         * paramsObj : null
         * remark : 我在本次运输过程中没有被处罚。
         * state : 1
         * stateContent : 启用
         * type : A011
         * upload : null
         * uploadContentType : null
         * uploadFileName : null
         */

        private String code;
        private String createTime;
        private int id;
        private String name;
        private String names;
        private String operNames;
        private String orgCode;
        private Object paramsObj;
        private String remark;
        private String state;
        private String stateContent;
        private String type;
        private Object upload;
        private Object uploadContentType;
        private Object uploadFileName;
        private List<String> modelContent;

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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
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

        public List<String> getModelContent() {
            return modelContent;
        }

        public void setModelContent(List<String> modelContent) {
            this.modelContent = modelContent;
        }
    }
}
