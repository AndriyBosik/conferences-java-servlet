package com.conferences.handler.abstraction;

import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *     Defines methods to interact with files
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IFileHandler {

    /**
     * <p>
     *     Saves file to specified destination
     * </p>
     * @param fileItems list containing files
     * @param path path to file loading directory
     * @param filename preferred filename
     * @return true if file was successfully saved, false otherwise
     */
    boolean saveFile(List<FileItem> fileItems, String path, String filename);
}
