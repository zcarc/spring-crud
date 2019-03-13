package com.wrkbr.mapper;

import com.wrkbr.domain.Criteria;
import com.wrkbr.domain.ReplyVO;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.stream.IntStream;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class ReplyMapperTests {

    @Setter(onMethod_ = @Autowired)
    private ReplyMapper replyMapper;

    @Test
    public void testMapper(){
        log.info(replyMapper);
    }

    @Test
    public void testInsert(){

        Long arr[] = {130L, 128L, 127L};

        IntStream.rangeClosed(0,2).forEach(i -> {

            ReplyVO replyVO = new ReplyVO();
            log.info("i: " + i);
            replyVO.setBno(arr[i]);
            replyVO.setReply("댓글" + i);
            replyVO.setReplyer("작성자" + i);
            replyMapper.insert(replyVO);

            log.info("replyVO: " + replyVO);
        });

    }

    @Test
    public void testRead(){
        log.info(replyMapper.read(2L));
    }

    @Test
    public void testDelete(){
        replyMapper.delete(2L);
    }

    @Test
    public void testUpdate(){
        ReplyVO replyVO = new ReplyVO();
        replyVO.setRno(3L);
        replyVO.setReply("댓글수정");
        replyMapper.update(replyVO);
    }

    @Test
    public void testGetListWithPagination(){
        Criteria criteria = new Criteria(2, 10);

        replyMapper.getListWithPagination(130L, criteria);
    }

}
