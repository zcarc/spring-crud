package com.wrkbr.mapper;

import com.wrkbr.domain.BoardAttachVO;

import java.util.List;

public interface BoardAttachMapper {

    public void insert(BoardAttachVO boardAttachVO);
    public void delete(String uuid);
    public List<BoardAttachVO> getListAttach(Long bno);

    public void deleteAll(Long bno);

    public List<BoardAttachVO> getOldFiles();

}
