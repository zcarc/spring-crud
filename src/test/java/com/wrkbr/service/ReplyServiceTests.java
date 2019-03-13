package com.wrkbr.service;

import com.wrkbr.domain.Criteria;
import com.wrkbr.domain.ReplyPagesDTO;
import com.wrkbr.domain.ReplyVO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class ReplyServiceTests {

    @Inject
    private ReplyService replyService;

    @Test
    public void testExist(){
        log.info(replyService);
    }

    @Test
    public void testRead(){
        log.info(replyService.read(3L));
    }

    @Test
    public void testInsert(){
        ReplyVO replyVO = new ReplyVO();
        replyVO.setBno(128L);
        replyVO.setReply("테스트1");
        replyVO.setReplyer("테스트1");
        log.info("result: " + replyService.insert(replyVO));
    }

    @Test
    public void testGetListWithPagination(){
        ReplyPagesDTO replyPagesDTO = replyService.getListWithPagination(130L, new Criteria());
        replyPagesDTO.getReplyList().forEach(log::info);
    }

}
