package com.wrkbr.service;

import com.wrkbr.domain.Criteria;
import com.wrkbr.domain.ReplyPagesDTO;
import com.wrkbr.domain.ReplyVO;
import com.wrkbr.mapper.BoardMapper;
import com.wrkbr.mapper.ReplyMapper;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j
public class ReplyServiceImpl implements  ReplyService {

    @Setter(onMethod_ = @Autowired)
    private ReplyMapper replyMapper;

    @Setter(onMethod_ = @Autowired)
    private BoardMapper boardMapper;

    @Override
    @Transactional
    public int insert(ReplyVO replyVO) {
        log.info("insert()");
        boardMapper.updateReplyCount(replyVO.getBno(), 1);
        return replyMapper.insert(replyVO);
    }

    @Override
    public ReplyVO read(Long rno) {
        log.info("read()");
        return replyMapper.read(rno);
    }

    @Override
    @Transactional
    public int delete(Long rno) {
        log.info("delete()");
        ReplyVO replyVO = replyMapper.read(rno);
        boardMapper.updateReplyCount(replyVO.getBno(), -1);
        return replyMapper.delete(rno);
    }

    @Override
    public int update(ReplyVO replyVO) {
        log.info("update()");
        return replyMapper.update(replyVO);
    }

    @Override
    public ReplyPagesDTO getListWithPagination(Long bno, Criteria criteria) {
        log.info("getListWithPagination()");
        return new ReplyPagesDTO(
                replyMapper.replyCount(bno),
                replyMapper.getListWithPagination(bno, criteria));
    }

    @Override
    public int replyCount(Long bno) {
        return replyMapper.replyCount(bno);
    }
}
