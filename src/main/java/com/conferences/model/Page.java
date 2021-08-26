package com.conferences.model;

public class Page {

    private int itemsCount;
    private int pageNumber;

    public Page() {}

    public Page(int itemsCount, int pageNumber) {
        this.itemsCount = itemsCount;
        this.pageNumber = pageNumber;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
