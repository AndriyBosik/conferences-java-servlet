package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IFileHandler;
import com.conferences.mapper.IMapper;
import com.conferences.mapper.RequestToFileFormDataMapper;
import com.conferences.model.FileFormData;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

public class FileHandler implements IFileHandler {

    private final IMapper<HttpServletRequest, FileFormData> mapper;

    public FileHandler() {
        mapper = new RequestToFileFormDataMapper();
    }

    @Override
    public boolean saveFile(HttpServletRequest request, String path, String filename) {
        FileFormData data = mapper.map(request);
        return saveFile(data.getFileItems(), path, filename);
    }

    // Returns null when there was an error during file saving
    // Map with form fields otherwise
    @Override
    public boolean saveFile(List<FileItem> fileItems, String path, String filename) {
        for (FileItem fi : fileItems) {
            if (!fi.isFormField()) {
                if (filename == null || filename.trim().isEmpty()) {
                    filename = extractFilename(fi.getName());
                }
                File file = new File(path + filename);
                try {
                    fi.write(file);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return true;
            }
        }
        return false;
    }

    private String extractFilename(String fullFilename) {
        String[] parts = fullFilename.split("/");
        return parts[parts.length - 1];
    }

}
