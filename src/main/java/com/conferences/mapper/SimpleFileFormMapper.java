package com.conferences.mapper;

import java.util.Map;

public class SimpleFileFormMapper extends AbstractFileFormMapper<Map<String, String>> {

    @Override
    protected Map<String, String> mapFormDataToReturnValue(Map<String, String> formData) {
        return formData;
    }
}
