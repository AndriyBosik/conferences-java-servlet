package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IFileHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler implements IFileHandler {

    private static final int THRESHOLD = 1024 * 1024;
    private static final long MAX_SIZE = 1024 * 1024 * 10L;
    private static final String REPOSITORY = "D:\\files";

    // Returns null when there was an error during file saving
    // Map with form fields otherwise
    @Override
    public Map<String, String> saveFile(HttpServletRequest request, String path) {
        Map<String, String> formData = new HashMap<>();

        if (ServletFileUpload.isMultipartContent(request)) {
            ServletFileUpload upload = configureServletFileUpload();

            try {
                List<FileItem> fileItems = upload.parseRequest(request);

                for (FileItem fi : fileItems) {
                    if (!fi.isFormField()) {
                        String filename = extractFilename(fi.getName());

                        formData.put(fi.getFieldName(), filename);

                        File file = new File(path + filename);

                        fi.write(file);
                    } else {
                        formData.put(fi.getFieldName(), fi.getString());
                    }
                }

                return formData;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private ServletFileUpload configureServletFileUpload() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD);
        factory.setRepository(new File(REPOSITORY));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(MAX_SIZE);

        return upload;
    }

    private String extractFilename(String fullFilename) {
        String[] parts = fullFilename.split("/");
        return parts[parts.length - 1];
    }

}
