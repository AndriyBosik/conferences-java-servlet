package com.conferences.model;

import org.apache.commons.fileupload.FileItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileFormData {

    private Map<String, String> formData;

    private List<FileItem> fileItems;

    public FileFormData() {
        formData = new HashMap<>();
        fileItems = new ArrayList<>();
    }

    public Map<String, String> getFormData() {
        return formData;
    }

    public void setFormData(Map<String, String> formData) {
        this.formData = formData;
    }

    public List<FileItem> getFileItems() {
        return fileItems;
    }

    public void setFileItems(List<FileItem> fileItems) {
        this.fileItems = fileItems;
    }
}
