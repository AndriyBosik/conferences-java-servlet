package com.conferences.handler.abstraction;

import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IFileHandler {

    boolean saveFile(HttpServletRequest request, String path, String filename);

    boolean saveFile(List<FileItem> fileItems, String path, String filename);

}
