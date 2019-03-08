package com.wrkbr.domain;

import java.util.Date;
import java.util.List;

public class UserVO {

    private String userid;
    private String userpw;
    private String username;
    private String userGender;
    private String userPhone;
    private String userEmail;
    private String userEmailHash;
    private boolean userEmailChecked;
    private Date userRegDate;
    private Date userUpdateDate;
    private String enabled;

    private List<UserAuthVO> authList;


    @Override
    public String toString() {
        return "UserVO{" +
                "userid='" + userid + '\'' +
                ", userpw='" + userpw + '\'' +
                ", username='" + username + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userEmailHash='" + userEmailHash + '\'' +
                ", userEmailChecked=" + userEmailChecked +
                ", userRegDate=" + userRegDate +
                ", userUpdateDate=" + userUpdateDate +
                ", enabled='" + enabled + '\'' +
                ", authList=" + authList +
                '}';
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpw() {
        return userpw;
    }

    public void setUserpw(String userpw) {
        this.userpw = userpw;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmailHash() {
        return userEmailHash;
    }

    public void setUserEmailHash(String userEmailHash) {
        this.userEmailHash = userEmailHash;
    }

    public boolean isUserEmailChecked() {
        return userEmailChecked;
    }

    public void setUserEmailChecked(boolean userEmailChecked) {
        this.userEmailChecked = userEmailChecked;
    }

    public Date getUserRegDate() {
        return userRegDate;
    }

    public void setUserRegDate(Date userRegDate) {
        this.userRegDate = userRegDate;
    }

    public Date getUserUpdateDate() {
        return userUpdateDate;
    }

    public void setUserUpdateDate(Date userUpdateDate) {
        this.userUpdateDate = userUpdateDate;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public List<UserAuthVO> getAuthList() {
        return authList;
    }

    public void setAuthList(List<UserAuthVO> authList) {
        this.authList = authList;
    }
}
