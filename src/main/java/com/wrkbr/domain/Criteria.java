package com.wrkbr.domain;

import lombok.ToString;
import lombok.extern.log4j.Log4j;
import org.springframework.web.util.UriComponentsBuilder;

@ToString
@Log4j
public class Criteria {

    private int currentPage;
    private int displayRecords;

    private String type;
    private String keyword;

    public Criteria() {
        this(1, 10);
    }

    public Criteria(int currentPage, int displayRecords) {
        this.currentPage = currentPage;
        this.displayRecords = displayRecords;
    }

    public String [] getTypeArr(){
        return type == null ? new String[] {} : type.split("");
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getDisplayRecords() {
        return displayRecords;
    }

    public void setDisplayRecords(int displayRecords) {
        this.displayRecords = displayRecords;
    }

    public int getOffset(){
        return (currentPage - 1) * displayRecords;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getListLink(){

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("currentPage", this.currentPage)
                .queryParam("displayRecords", this.displayRecords)
                .queryParam("type", this.type)
                .queryParam("keyword", this.keyword);

        log.info("getListLink...  UriComponentsBuilder : " + builder.toUriString() );

        return builder.toUriString();
    }
}
