package com.wrkbr.service;

import com.wrkbr.domain.Criteria;
import com.wrkbr.domain.ReplyPagesDTO;
import com.wrkbr.domain.ReplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyService {

    public int insert(ReplyVO replyVO);
    public ReplyVO read(Long rno);
    public int delete(Long rno);
    public int update(ReplyVO replyVO);
    public ReplyPagesDTO getListWithPagination(@Param("bno") Long bno,
                                               @Param("criteria") Criteria criteria);
    public int replyCount(Long bno);
}
