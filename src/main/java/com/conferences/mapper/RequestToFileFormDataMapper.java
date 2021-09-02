package com.conferences.mapper;

import com.conferences.config.Constants;
import com.conferences.model.FileFormData;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestToFileFormDataMapper implements IMapper<HttpServletRequest, FileFormData> {

    @Override
    public FileFormData map(HttpServletRequest request) {
        if (ServletFileUpload.isMultipartContent(request)) {
            FileFormData data = new FileFormData();
            Map<String, String> formData = new HashMap<>();
            ServletFileUpload upload = configureServletFileUpload();

            try {
                List<FileItem> fileItems = upload.parseRequest(request);
                data.setFileItems(fileItems);

                for (FileItem fi : fileItems) {
                    if (fi.isFormField()) {
                        formData.put(fi.getFieldName(), fi.getString());
                    } else {
                        formData.put(fi.getFieldName(), fi.getName());
                    }
                }
                data.setFormData(formData);
                return data;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }

    private ServletFileUpload configureServletFileUpload() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(Constants.THRESHOLD);
        factory.setRepository(new File(Constants.REPOSITORY));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(Constants.MAX_SIZE);

        return upload;
    }
}
