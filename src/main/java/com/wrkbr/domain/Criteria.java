package com.wrkbr.domain;


public class Criteria {

    private int currentPage;
    private int displayRecords;

    public Criteria() {
        this(1, 10);
    }

    public Criteria(int currentPage, int displayRecords) {
        this.currentPage = currentPage;
        this.displayRecords = displayRecords;
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

}
