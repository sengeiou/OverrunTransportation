package com.android.hcbd.overruntransportation.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by guocheng on 2017/3/21.
 */

public class OtherInfo implements Serializable{
    private String total;
    private String editFlag;
    private List<QuestionBean> questionList;
    private List<PicBean> picList;
    private List<RowsBean> rows;
    private DiscussBean discussBean;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(String editFlag) {
        this.editFlag = editFlag;
    }

    public List<QuestionBean> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<QuestionBean> questionList) {
        this.questionList = questionList;
    }

    public List<PicBean> getPicList() {
        return picList;
    }

    public void setPicList(List<PicBean> picList) {
        this.picList = picList;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public DiscussBean getDiscussBean() {
        return discussBean;
    }

    public void setDiscussBean(DiscussBean discussBean) {
        this.discussBean = discussBean;
    }

    public static class QuestionBean implements Serializable{
        private String answer;
        private int id;
        private String question;
        private int seq;
        private List<String> modelContent;

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public int getSeq() {
            return seq;
        }

        public void setSeq(int seq) {
            this.seq = seq;
        }

        public List<String> getModelContent() {
            return modelContent;
        }

        public void setModelContent(List<String> modelContent) {
            this.modelContent = modelContent;
        }
    }

    public static class PicBean implements Serializable{
        private String url;
        private String name;
        private Long id;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }

    public static class RowsBean implements Serializable{

        private String code;
        private String name;
        private String names;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
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
    }

    public static class DiscussBean implements Serializable{

        private String beginTime;
        private String endTime;
        private int id;
        private String n1;
        private String n2;
        private String n3;
        private String n4;
        private String n5;
        private String t1;
        private String te1;

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

        public String getN4() {
            return n4;
        }

        public void setN4(String n4) {
            this.n4 = n4;
        }

        public String getN5() {
            return n5;
        }

        public void setN5(String n5) {
            this.n5 = n5;
        }

        public String getT1() {
            return t1;
        }

        public void setT1(String t1) {
            this.t1 = t1;
        }

        public String getTe1() {
            return te1;
        }

        public void setTe1(String te1) {
            this.te1 = te1;
        }
    }

}
