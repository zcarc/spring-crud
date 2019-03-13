package com.wrkbr.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
public class ReplyVO {

    private Long bno;
    private Long rno;

    private String reply;
    private String replyer;
    private Date regDate;
    private Date updateDate;
}
