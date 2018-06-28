package com.android.hcbd.overruntransportation.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guocheng on 2017/3/29.
 */

public class TemplateManageInfo implements Serializable{
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
         * code : A000009
         * createTime : 2017-01-05T12:40:05
         * id : 37
         * modelContent : ["模版","/tps/wordAction!"]
         * name : null
         * names : A000009-null
         * operNames : S0000-Eingabe
         * orgCode : 027
         * paramsObj : null
         * remark : null
         * state : 1
         * stateContent : 启用
         * type : All
         * upload : null
         * uploadContentType : application/vnd.openxmlformats-officedocument.wordprocessingml.document
         * uploadFileName : 双超路政罚文书.docx
         * url : //tpsWord//027//双超路政罚文书.docx
         */

        private String code;
        private String createTime;
        private int id;
        private Object name;
        private String names;
        private String operNames;
        private String orgCode;
        private Object paramsObj;
        private Object remark;
        private String state;
        private String stateContent;
        private String type;
        private Object upload;
        private String uploadContentType;
        private String uploadFileName;
        private String url;
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

        public String getUploadContentType() {
            return uploadContentType;
        }

        public void setUploadContentType(String uploadContentType) {
            this.uploadContentType = uploadContentType;
        }

        public String getUploadFileName() {
            return uploadFileName;
        }

        public void setUploadFileName(String uploadFileName) {
            this.uploadFileName = uploadFileName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<String> getModelContent() {
            return modelContent;
        }

        public void setModelContent(List<String> modelContent) {
            this.modelContent = modelContent;
        }
    }

}
