package com.conferences.util;

public class FileUtil {

    private FileUtil() {}

    public static String getFileExtension(String filename) {
        String[] parts = filename.split("\\.");
        return parts[parts.length - 1];
    }

    public static String removeFileForbiddenSymbols(String filename) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < filename.length(); i++) {
            if (isAllowed(filename.charAt(i))) {
                sb.append(filename.charAt(i));
            }
        }
        return sb.toString();
    }

    private static boolean isAllowed(char symbol) {
        return  (symbol >= 'A' && symbol <= 'Z') ||
                (symbol >= 'a' && symbol <= 'z') ||
                (symbol >= '0' && symbol <= '9') ||
                symbol == '-' ||
                symbol == '_';
    }

}
