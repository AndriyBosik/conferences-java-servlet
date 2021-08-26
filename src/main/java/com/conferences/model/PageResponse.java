package com.conferences.model;

import java.util.ArrayList;
import java.util.List;

public class PageResponse<T> {

    private T item;
    private int pagesCount;

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }
}