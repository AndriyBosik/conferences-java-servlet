package com.conferences.config;

public enum SortOrder {
    ASC("ASC"),
    DESC("DESC");

    private String sortValue;

    SortOrder(String sortValue) {
        this.sortValue = sortValue;
    }

    public static SortOrder fromString(String sortOrder) {
        try {
            return SortOrder.valueOf(sortOrder.toUpperCase());
        } catch (IllegalArgumentException exception) {
            return ASC;
        }
    }

    @Override
    public String toString() {
        return sortValue;
    }
}
