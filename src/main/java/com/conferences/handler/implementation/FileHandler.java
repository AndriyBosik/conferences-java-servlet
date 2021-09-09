package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IFileHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 * {@inheritDoc}
 */
public class FileHandler implements IFileHandler {

    private static final Logger LOGGER = LogManager.getLogger(FileHandler.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean saveFile(List<FileItem> fileItems, String path, String filename) {
        for (FileItem fi : fileItems) {
            if (!fi.isFormField()) {
                LOGGER.info("Saving file");
                if (filename == null || filename.trim().isEmpty()) {
                    filename = extractFilename(fi.getName());
                }
                File file = new File(path + filename);
                try {
                    fi.write(file);
                } catch (Exception exception) {
                    LOGGER.error("Unable to save file", exception);
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
