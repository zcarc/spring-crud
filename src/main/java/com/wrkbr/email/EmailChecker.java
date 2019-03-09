package com.wrkbr.email;

public class EmailChecker {

    private String userMail;
    private String userMailHash;
    private boolean userMailChecked;


    public EmailChecker(String userMail, String userMailHash, boolean userMailChecked) {

        this.userMail = userMail;
        this.userMailHash = userMailHash;
        this.userMailChecked = userMailChecked;
    }

    public String getUserMail() {
        return userMail;
    }
    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }
    public String getUserMailHash() {
        return userMailHash;
    }
    public void setUserMailHash(String userMailHash) {
        this.userMailHash = userMailHash;
    }
    public boolean getUserMailChecked() {
        return userMailChecked;
    }
    public void setUserMailChecked(boolean userMailChecked) {
        this.userMailChecked = userMailChecked;
    }

    //메일,해쉬값 비교
    public boolean equals(String userMail, String userMailHash) {

        if(this.userMail.equals(userMail) && this.userMailHash.equals(userMailHash)) {
            this.userMailChecked = true;
            return true;
        } else {
            return false;
        }

    }


}