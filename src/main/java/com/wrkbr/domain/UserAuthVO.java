package com.wrkbr.domain;


public class UserAuthVO {

    private String userid;
    private String auth;


    @Override
    public String toString() {
        return "UserAuthVO{" +
                "userid='" + userid + '\'' +
                ", auth='" + auth + '\'' +
                '}';
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }


}
