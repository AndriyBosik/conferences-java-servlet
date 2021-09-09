package com.conferences.mapper;

import java.util.Map;

/**
 * {@inheritDoc}
 */
public class SimpleFileFormMapper extends AbstractFileFormMapper<Map<String, String>> {

    /**
     * <p>
     *     Simple mapper that returns data from file form
     * </p>
     */
    @Override
    protected Map<String, String> mapFormDataToReturnValue(Map<String, String> formData) {
        return formData;
    }
}
