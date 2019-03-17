package com.wrkbr.service;

import com.wrkbr.domain.BoardAttachVO;
import com.wrkbr.domain.BoardVO;
import com.wrkbr.domain.Criteria;

import java.util.List;

public interface BoardService {

    public List<BoardVO> getList();
    public List<BoardVO> getListWithPagination(Criteria criteria);
    public int boardCount(Criteria criteria);
    public BoardVO read(Long bno);
    //public void insert(BoardVO boardVO);
    public void insertSelectKey(BoardVO boardVO);
    public boolean delete(Long bno);
    public boolean update(BoardVO boardVO);

    public List<BoardAttachVO> getListAttach(Long bno);


}
