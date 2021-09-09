package com.conferences.util;

/**
 * <p>
 *     Defines functions to process files
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public class FileUtil {

    private FileUtil() {}

    /**
     * <p>
     *     Returns file extension
     * </p>
     * @param filename name of file to extract extension from
     * @return file extension
     */
    public static String getFileExtension(String filename) {
        String[] parts = filename.split("\\.");
        return parts[parts.length - 1];
    }

    /**
     * <p>
     *     Removes forbidden symbols from {@code filename}
     * </p>
     * @param filename name of file to remove forbidden symbols from
     * @return filename without forbidden symbols
     */
    public static String removeFileForbiddenSymbols(String filename) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filename.length(); i++) {
            if (isAllowed(filename.charAt(i))) {
                sb.append(filename.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * <p>
     *     Checks whatever passed symbol is allowed
     * </p>
     * @param symbol symbol to check if is allowed
     * @return true if symbol is allowed, false otherwise
     */
    private static boolean isAllowed(char symbol) {
        return  (symbol >= 'A' && symbol <= 'Z') ||
                (symbol >= 'a' && symbol <= 'z') ||
                (symbol >= '0' && symbol <= '9') ||
                symbol == '-' ||
                symbol == '_';
    }

}
