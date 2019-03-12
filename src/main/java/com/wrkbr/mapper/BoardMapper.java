package com.wrkbr.mapper;

import com.wrkbr.domain.BoardVO;
import com.wrkbr.domain.Criteria;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BoardMapper {


    public List<BoardVO> getList();
    public List<BoardVO> getListWithPagination(Criteria criteria);
    public int boardCount(Criteria criteria);
    public void insert(BoardVO boardVO);
    public void insertSelectKey(BoardVO boardVO);
    public BoardVO read(Long bno);
    public int delete(Long bno);
    public int update(BoardVO boardVO);



}
