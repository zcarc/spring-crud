package com.wrkbr.domain;

import lombok.Data;

@Data
public class ProfileVO {


    private String userid;
    private String userpw;
    private String changedid;

    private String kakao_id;
    private String facebook_id;
    private String google_id;
}
