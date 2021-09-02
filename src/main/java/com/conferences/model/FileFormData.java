package com.conferences.model;

import org.apache.commons.fileupload.FileItem;

import java.util.ArrayList;
import java.util.List;

public class FileFormData<T> {

    private T item;

    private List<FileItem> fileItems;

    public FileFormData() {
        fileItems = new ArrayList<>();
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public List<FileItem> getFileItems() {
        return fileItems;
    }

    public void setFileItems(List<FileItem> fileItems) {
        this.fileItems = fileItems;
    }
}
