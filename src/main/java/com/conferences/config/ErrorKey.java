package com.conferences.config;

import com.conferences.handler.abstraction.IPropertiesHandler;
import com.conferences.handler.implementation.PropertiesHandler;

public enum ErrorKey {
    OK(""),
    INVALID_LOGIN_OR_PASSWORD("errors.invalid_login_or_password"),
    PASSWORDS_ARE_NOT_EQUAL("errors.passwords_are_not_equal"),
    INVALID_OLD_PASSWORD("errors.invalid_old_password"),
    REQUIRED_FIELD("errors.required_field"),
    FIELD_MINIMUM_LENGTH("errors.field_minimum_length"),
    FUTURE_DATE("errors.future_date"),
    IMAGE_LOADING_FAILED("errors.image_loading_failed"),
    CREATION_ERROR("errors.creation_error"),
    UPDATING_ERROR("errors.updating_error"),
    DELETION_ERROR("errors.deletion_error"),
    INVALID_EMAIL("errors.invalid_email"),
    EXISTING_USER("errors.existing_user"),
    INVALID_ROLE("errors.invalid_role"),
    REGISTRATION_ERROR("errors.registration_error"),
    EMPTY_PASSWORD_FIELD("errors.empty_password_field"),
    JOINING_ERROR("errors.joining_error"),
    PRESENCE_ERROR("errors.presence_error"),
    SAVING_PROPOSAL_ERROR("errors.proposal_saving_error"),
    REJECT_PROPOSAL_ERROR("errors.proposal_rejection_error"),
    PROPOSE_SPEAKER_ERROR("errors.propose_speaker_error"),
    ACCEPT_PROPOSAL_ERROR("errors.accept_proposal_error");

    private final String key;
    private final IPropertiesHandler propertiesHandler;

    ErrorKey(String key) {
        this.key = key;
        propertiesHandler = new PropertiesHandler();
    }

    @Override
    public String toString() {
        return key;
    }

}
