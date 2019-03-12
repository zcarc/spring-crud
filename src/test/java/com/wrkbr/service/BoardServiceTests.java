package com.wrkbr.service;

import com.wrkbr.domain.BoardVO;
import com.wrkbr.domain.Criteria;
import lombok.extern.log4j.Log4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class BoardServiceTests {

    @Inject
    private BoardService boardService;

    @Test
    public void testExist(){
        log.info("boardService: " + boardService);
        Assert.assertNotNull(boardService);
    }

    @Test
    public void testInsertSelectKey(){
        BoardVO boardVO = new BoardVO();
        boardVO.setTitle("테스트제목5");
        boardVO.setContent("테스트내용5");
        boardVO.setWriter("작성자5");
        boardService.insertSelectKey(boardVO);
        log.info("생성된 게시물의 번호: " + boardVO.getBno());
    }

    @Test
    public void testGetList(){
        boardService.getList().forEach(log::info);
    }

    @Test
    public void testRead(){
        log.info(boardService.read(2L));
    }

    @Test
    public void testUpdate(){
        BoardVO boardVO = boardService.read(2L);

        if(boardVO == null)
            return;

        boardVO.setTitle("테스트제목2 수정");
        log.info("Modify result: " + boardService.update(boardVO));
    }

    @Test
    public void testDelete() {
        log.info("Delete Result: " + boardService.delete(3L));
    }

    @Test
    public void testGetListWithPagination(){
        Criteria criteria = new Criteria();
        criteria.setCurrentPage(1);
        boardService.getListWithPagination(criteria).forEach(log::info);
    }

}
