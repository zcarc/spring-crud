package com.wrkbr.domain;

import lombok.Data;

import java.util.Date;

@Data
public class PlatformVO {

    private String userid;

    private String kakao_id;
    private String ka_generate;

    private String facebook_id;
    private String fb_generate;

    private String google_id;
    private String gg_generate;

    private Date loginDate;

}
