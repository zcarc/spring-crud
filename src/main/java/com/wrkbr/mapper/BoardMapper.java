package com.wrkbr.mapper;

import com.wrkbr.domain.BoardVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BoardMapper {


    public List<BoardVO> getList();
    public void insert(BoardVO boardVO);
    public void insertSelectKey(BoardVO boardVO);
    public BoardVO read(Long bno);
    public int delete(Long bno);
    public int update(BoardVO boardVO);



}
