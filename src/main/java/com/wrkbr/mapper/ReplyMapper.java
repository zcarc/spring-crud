package com.wrkbr.mapper;

import com.wrkbr.domain.Criteria;
import com.wrkbr.domain.ReplyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReplyMapper {


    public int insert(ReplyVO replyVO);
    public ReplyVO read(Long rno);
    public int delete(Long rno);
    public int update(ReplyVO replyVO);
    public List<ReplyVO> getListWithPagination(@Param("bno") Long bno,
                                               @Param("criteria") Criteria criteria);
    public int replyCount(Long bno);
}
