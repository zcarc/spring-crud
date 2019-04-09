package com.wrkbr.domain;

import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
public class BoardVO {

    private Long bno;
    private String title;
    private String content;
    private String writer;
    private String writerId;
    private Long bgroup;
    private int bstep;
    private int bindent;
    private Date regDate;
    private Date updateDate;

    private int replyCount;

    private List<BoardAttachVO> attachVOList;


    public Long getBno() {
        return bno;
    }

    public void setBno(Long bno) {
        this.bno = bno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public Long getBgroup() {
        return bgroup;
    }

    public void setBgroup(Long bgroup) {
        this.bgroup = bgroup;
    }

    public int getBstep() {
        return bstep;
    }

    public void setBstep(int bstep) {
        this.bstep = bstep;
    }

    public int getBindent() {
        return bindent;
    }

    public void setBindent(int bindent) {
        this.bindent = bindent;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public List<BoardAttachVO> getAttachVOList() {
        return attachVOList;
    }

    public void setAttachVOList(List<BoardAttachVO> attachVOList) {
        this.attachVOList = attachVOList;
    }
}
