package com.wrkbr.service;

import com.wrkbr.domain.BoardVO;

import java.util.List;

public interface BoardService {

    public List<BoardVO> getList();
    public BoardVO read(Long bno);
    public void insert(BoardVO boardVO);
    public void insertSelectKey(BoardVO boardVO);
    public boolean delete(Long bno);
    public boolean update(BoardVO boardVO);


}
