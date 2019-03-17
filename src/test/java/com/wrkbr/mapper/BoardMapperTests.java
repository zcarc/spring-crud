package com.wrkbr.mapper;

import com.wrkbr.domain.BoardVO;
import com.wrkbr.domain.Criteria;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class BoardMapperTests {

    @Setter(onMethod_ = @Autowired)
    private BoardMapper boardMapper;

    @Test
    public void testGetList(){
        boardMapper.getList().forEach(log::info);
    }

    @Test
    public void testInsert(){
        BoardVO boardVO = new BoardVO();
        boardVO.setTitle("테스트제목4");
        boardVO.setContent("테스트내용4");
        boardVO.setWriter("작성자4");
        //boardMapper.insert(boardVO);
    }

    @Test
    public void testRead(){
        boardMapper.read(4L);
    }

    @Test
    public void testDelete(){
        boardMapper.delete(4L);
    }

    @Test
    public void testUpdate(){
        BoardVO boardVO = new BoardVO();
        boardVO.setBno(3L);
        boardVO.setTitle("테스트제목3 수정");
        boardVO.setContent("테스트내용3 수정");
        boardVO.setWriter("작성자3 수정");
        boardMapper.update(boardVO);
    }

    @Test
    public void testGetListWithPagination(){
        Criteria criteria = new Criteria();
        criteria.setCurrentPage(2);
        boardMapper.getListWithPagination(criteria).forEach(log::info);
    }

    @Test
    public void testBoardCount(){

        log.info(boardMapper.boardCount(new Criteria()));
    }

    @Test
    public void testGetListWithSearch(){
        Criteria criteria = new Criteria();
        criteria.setKeyword("Auth");
        criteria.setType("TW");
        boardMapper.getListWithPagination(criteria).forEach(log::info);
    }

}
