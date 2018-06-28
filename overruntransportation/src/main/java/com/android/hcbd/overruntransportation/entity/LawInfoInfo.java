package com.android.hcbd.overruntransportation.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guocheng on 2017/3/28.
 */

public class LawInfoInfo implements Serializable{
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
         * code : L009
         * createTime : 2017-01-05T10:39:42
         * id : 10
         * n1 : 第四十三条
         * n2 : 第一款
         * n3 : 第二项
         * name : 超限运输车辆行驶公路管理规定
         * seq : 2
         * state : 1
         * suffix :
         * wordCode : A000004
         * wordId : 32
         * wordName : null
         */

        private String code;
        private String createTime;
        private int id;
        private String n1;
        private String n2;
        private String n3;
        private String name;
        private int seq;
        private String state;
        private String suffix;
        private String wordCode;
        private int wordId;
        private String wordName;

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

        public String getN1() {
            return n1;
        }

        public void setN1(String n1) {
            this.n1 = n1;
        }

        public String getN2() {
            return n2;
        }

        public void setN2(String n2) {
            this.n2 = n2;
        }

        public String getN3() {
            return n3;
        }

        public void setN3(String n3) {
            this.n3 = n3;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getWordCode() {
            return wordCode;
        }

        public void setWordCode(String wordCode) {
            this.wordCode = wordCode;
        }

        public int getWordId() {
            return wordId;
        }

        public void setWordId(int wordId) {
            this.wordId = wordId;
        }

        public String getWordName() {
            return wordName;
        }

        public void setWordName(String wordName) {
            this.wordName = wordName;
        }
    }
}
