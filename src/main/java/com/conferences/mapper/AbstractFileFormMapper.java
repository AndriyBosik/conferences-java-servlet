package com.conferences.mapper;

import com.conferences.config.Constants;
import com.conferences.model.FileFormData;
import com.conferences.util.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractFileFormMapper<U> implements IMapper<HttpServletRequest, FileFormData<U>> {

    protected abstract U mapFormDataToReturnValue(Map<String, String> formData);

    @Override
    public FileFormData<U> map(HttpServletRequest request) {
        if (ServletFileUpload.isMultipartContent(request)) {
            FileFormData<U> data = new FileFormData<>();
            Map<String, String> formData = new HashMap<>();
            ServletFileUpload upload = configureServletFileUpload();
            upload.setHeaderEncoding("UTF-8");

            try {
                List<FileItem> fileItems = upload.parseRequest(request);
                data.setFileItems(fileItems);

                for (FileItem fi : fileItems) {
                    if (fi.isFormField()) {
                        formData.put(fi.getFieldName(), StringUtil.convertStringToUTF8(fi.getString(), StandardCharsets.ISO_8859_1));
                    } else {
                        formData.put(fi.getFieldName(), StringUtil.convertStringToUTF8(fi.getName(), StandardCharsets.ISO_8859_1));
                    }
                }

                data.setItem(mapFormDataToReturnValue(formData));
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
