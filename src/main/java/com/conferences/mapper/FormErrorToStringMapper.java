package com.conferences.mapper;

import com.conferences.config.Constants;
import com.conferences.factory.HandlerFactory;
import com.conferences.handler.abstraction.IPropertiesHandler;
import com.conferences.model.FormError;

/**
 * {@inheritDoc}
 */
public class FormErrorToStringMapper implements IMapper<FormError, String> {

    private static final String FIELDS_MESSAGES_PREFIX = "fields.";

    private final IPropertiesHandler handler;

    public FormErrorToStringMapper() {
        handler = HandlerFactory.getInstance().getPropertiesHandler();
    }

    /**
     * {@inheritDoc}
     * <p>
     *     Localize error message
     * </p>
     */
    @Override
    public String map(FormError model) {
        String lang = model.getLang();
        String message = handler.getPropertyValue(Constants.LOCALIZATION_MESSAGES, lang, model.getErrorKey().toString());
        Object[] params = new Object[model.getParams().size()];
        for (int i = 0; i < params.length; i++) {
            String fieldKey = FIELDS_MESSAGES_PREFIX + model.getParams().get(i);
            String value = handler.getPropertyValue(Constants.LOCALIZATION_MESSAGES, lang, fieldKey);
            if (fieldKey.equals(value)) {
                params[i] = model.getParams().get(i);
            } else {
                params[i] = value;
            }
        }
        return String.format(message, params);
    }
}
