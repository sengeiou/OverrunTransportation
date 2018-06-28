package com.android.hcbd.overruntransportation.entity;

import java.util.List;

/**
 * Created by guocheng on 2017/3/16.
 */

public class LoginInfo {
    /**
     * permitList : ["路政员"]
     * lastAddr : 武汉
     * token : co66rWtGIgj7vck8q9bnAQ==
     * data : {"birthday":"","code":"S0089","email":"12312@126.com","mobilephone":"","name":"测试有权限","names":"S0089-测试有权限","no":"12312","notice":"1","orgCode":"027","phone":"","sex":"1","state":"1","unit":"G001-湖北省交通运输厅高速公路路政执法总队京珠支队第二大队","userName":"app2"}
     * menuList : [{"code":"1,1,1,1","id":null,"name":"超限处罚","names":"1,1,1,1-超限处罚"},{"code":"1,1,1,1","id":null,"name":"车型信息","names":"1,1,1,1-车型信息"},{"code":"1,1,1,1","id":null,"name":"法律名称","names":"1,1,1,1-法律名称"},{"code":"1,1,1,1","id":null,"name":"法条信息","names":"1,1,1,1-法条信息"},{"code":"1,1,1,1","id":null,"name":"模版管理","names":"1,1,1,1-模版管理"},{"code":"1,1,1,1","id":null,"name":"默认问题","names":"1,1,1,1-默认问题"},{"code":"1,1,1,1","id":null,"name":"默认图片名称","names":"1,1,1,1-默认图片名称"},{"code":"1,1,1,1","id":null,"name":"工作日安排","names":"1,1,1,1-工作日安排"},{"code":"0,0,0,0","id":null,"name":"时间计算","names":"0,0,0,0-时间计算"}]
     * loginCount : 8
     */

    private String[] permitList;
    private String lastAddr;
    private String token;
    private UserInfo userInfo;
    private List<MenuInfo> menuList;
    private String loginCount;


    public String[] getPermitList() {
        return permitList;
    }

    public void setPermitList(String[] permitList) {
        this.permitList = permitList;
    }

    public String getLastAddr() {
        return lastAddr;
    }

    public void setLastAddr(String lastAddr) {
        this.lastAddr = lastAddr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<MenuInfo> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuInfo> menuList) {
        this.menuList = menuList;
    }

    public String getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(String loginCount) {
        this.loginCount = loginCount;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfo{

        /**
         * birthday :
         * code : S0089
         * email : 12312@126.com
         * mobilephone :
         * name : 测试有权限
         * names : S0089-测试有权限
         * no : 12312
         * notice : 1
         * orgCode : 027
         * phone :
         * sex : 1
         * state : 1
         * unit : G001-湖北省交通运输厅高速公路路政执法总队京珠支队第二大队
         * userName : app2
         */

        private String birthday;
        private String code;
        private String email;
        private String mobilephone;
        private String name;
        private String names;
        private String no;
        private String notice;
        private String orgCode;
        private String phone;
        private String sex;
        private String state;
        private String unit;
        private String userName;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getOrgCode() {
            return orgCode;
        }

        public void setOrgCode(String orgCode) {
            this.orgCode = orgCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
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

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    public static class MenuInfo{

        /**
         * code : 1,1,1,1
         * id : null
         * name : 超限处罚
         * names : 1,1,1,1-超限处罚
         */

        private String code;
        private Object id;
        private String name;
        private String names;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
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
    }

}
