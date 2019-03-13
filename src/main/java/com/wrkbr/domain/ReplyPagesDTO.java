package com.wrkbr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReplyPagesDTO {

    private int replyCountInDTO;
    private List<ReplyVO> replyList;

}
