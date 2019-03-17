package com.wrkbr.service;

import com.wrkbr.domain.BoardAttachVO;
import com.wrkbr.domain.BoardVO;
import com.wrkbr.domain.Criteria;
import com.wrkbr.mapper.BoardAttachMapper;
import com.wrkbr.mapper.BoardMapper;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j
public class BoardServiceImpl implements BoardService {

    @Setter(onMethod_ = @Autowired)
    private BoardMapper boardMapper;

    @Setter(onMethod_ = @Autowired)
    private BoardAttachMapper boardAttachMapper;

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
    public int boardCount(Criteria criteria) {
        return boardMapper.boardCount(criteria);
    }

    @Override
    public BoardVO read(Long bno) {
        return boardMapper.read(bno);
    }

//    @Override
//    public void insert(BoardVO boardVO) {
//
//    }

    @Override
    @Transactional
    public void insertSelectKey(BoardVO boardVO) {
        log.info("insertSelectKey... boardVO: " + boardVO);

        // seledtKey로 bno 값 설정
        boardMapper.insertSelectKey(boardVO);
        log.info("after - boardMapper.insertSelectKey(boardVO) - boardVO:" + boardVO);

        if(boardVO.getAttachVOList() == null || boardVO.getAttachVOList().size() <= 0){
            log.info("AttachVOList does not exist.");
            return;
        }

        boardVO.getAttachVOList().forEach(attachVO -> {

            attachVO.setBno(boardVO.getBno());
            boardAttachMapper.insert(attachVO);
        });
    }

    @Override
    @Transactional
    public boolean delete(Long bno) {

        // Delete all records in attach table that matches bno.
        boardAttachMapper.deleteAll(bno);

        // Delete specific bno.
        return boardMapper.delete(bno) == 1;
    }

    @Override
    @Transactional
    public boolean update(BoardVO boardVO) {
        log.info("updates service...");
        log.info("boardVO: " + boardVO);

        boardAttachMapper.deleteAll(boardVO.getBno());
        boolean updatedResult =  boardMapper.update(boardVO) == 1;
        log.info("updatedResult: " + updatedResult);
        log.info("boardVO.getAttachVOList(): " + boardVO.getAttachVOList());

        // 수정 시 첨부파일을 전부 삭제했다면, attachVoList = null
        // 이 경우에는 물리적인 파일이 존재하므로 Quartz를 사용해서 삭제
        if(updatedResult && boardVO.getAttachVOList() != null){

            boardVO.getAttachVOList().forEach(boardAttachVO -> {
                boardAttachVO.setBno(boardVO.getBno());
                boardAttachMapper.insert(boardAttachVO);
            });
        }

        return updatedResult;
    }

    @Override
    public List<BoardAttachVO> getListAttach(Long bno) {
        return boardAttachMapper.getListAttach(bno);
    }
}
