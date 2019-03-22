package com.wrkbr.domain;

import org.hibernate.annotations.Entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
public class UserVO {

    @Id
    @Pattern(regexp="[A-Za-z0-9+]{5,50}", message="영문,숫자로 최소 5자리부터 입력하세요.")
    private String userid;

    @Column
    @Pattern(regexp="^[a-zA-Z0-9]{6,15}$", message="패스워드는 숫자와 영문자로 6~15자리까지 입력하세요.")
    private String userpw;

    @Column
    @Pattern(regexp="[가-힣]{2,10}", message="이름을 확인하세요.")
    private String username;

    @Column
    @NotNull(message="성별을 선택하세요.")
    private String userGender;

    @Id
    @Pattern(regexp="\\d{3}-?\\d{4}-\\d{4}", message="휴대폰 번호를 확인하세요.")
    private String userPhone;

    @Column
    @Pattern(regexp="[a-z0-9]+([-+._][a-z0-9]+){0,2}@.*?(\\.(a(?:[cdefgilmnoqrstuwxz]|ero|(?:rp|si)a)|b(?:[abdefghijmnorstvwyz]iz)|c(?:[acdfghiklmnoruvxyz]|at|o(?:m|op))|d[ejkmoz]|e(?:[ceghrstu]|du)|f[ijkmor]|g(?:[abdefghilmnpqrstuwy]|ov)|h[kmnrtu]|i(?:[delmnoqrst]|n(?:fo|t))|j(?:[emop]|obs)|k[eghimnprwyz]|l[abcikrstuvy]|m(?:[acdeghklmnopqrstuvwxyz]|il|obi|useum)|n(?:[acefgilopruz]|ame|et)|o(?:m|rg)|p(?:[aefghklmnrstwy]|ro)|qa|r[eosuw]|s[abcdeghijklmnortuvyz]|t(?:[cdfghjklmnoprtvwz]|(?:rav)?el)|u[agkmsyz]|v[aceginu]|w[fs]|y[etu]|z[amw])\\b){1,2}", message="올바른 이메일 형식이 아닙니다.")
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
