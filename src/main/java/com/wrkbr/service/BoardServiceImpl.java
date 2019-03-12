package com.wrkbr.service;

import com.wrkbr.domain.BoardVO;
import com.wrkbr.domain.Criteria;
import com.wrkbr.mapper.BoardMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j
public class BoardServiceImpl implements BoardService {

    @Autowired(required = false)
    private BoardMapper boardMapper;

    @Override
    public List<BoardVO> getList() {
        log.info("getList()...");
        return boardMapper.getList();
    }

    @Override
    public List<BoardVO> getListWithPagination(Criteria criteria) {
        log.info("getListWithPagination()...");
        return boardMapper.getListWithPagination(criteria);
    }

    @Override
    public int boardCount() {
        return boardMapper.boardCount();
    }

    @Override
    public BoardVO read(Long bno) {
        return boardMapper.read(bno);
    }

    @Override
    public void insert(BoardVO boardVO) {

    }

    @Override
    public void insertSelectKey(BoardVO boardVO) {
        log.info("insertSelectKey()...");
        boardMapper.insertSelectKey(boardVO);
    }

    @Override
    public boolean delete(Long bno) {
        return boardMapper.delete(bno) == 1;

    }

    @Override
    public boolean update(BoardVO boardVO) {
        return boardMapper.update(boardVO) == 1;
    }
}
