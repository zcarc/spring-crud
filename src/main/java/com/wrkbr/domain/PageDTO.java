package com.wrkbr.domain;

import lombok.ToString;

@ToString
public class PageDTO {

    private int startPageNum;
    private int endPageNum;
    private int boardCount;
    private int perPageNum = 10;
    private boolean prev;
    private boolean next;
    private Criteria criteria;

    public PageDTO(Criteria criteria, int boardCount){

        this.criteria = criteria;
        this.boardCount = boardCount;


        endPageNum = (int)Math.ceil( criteria.getCurrentPage() / (double)perPageNum ) * perPageNum;
        startPageNum = endPageNum - 9;

        if((endPageNum * perPageNum) > boardCount)
            endPageNum = (int)Math.ceil(boardCount / (double)perPageNum);

        if(startPageNum != 1)
            prev = true;

        if( endPageNum * perPageNum < boardCount)
            next = true;

    }

    public int getStartPageNum() {
        return startPageNum;
    }

    public int getEndPageNum() {
        return endPageNum;
    }

    public int getBoardCount() {
        return boardCount;
    }

    public int getPerPageNum() {
        return perPageNum;
    }

    public boolean isPrev() {
        return prev;
    }

    public boolean isNext() {
        return next;
    }

    public Criteria getCriteria() {
        return criteria;
    }
}
