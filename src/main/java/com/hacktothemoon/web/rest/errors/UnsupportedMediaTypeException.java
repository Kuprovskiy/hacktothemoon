package com.hacktothemoon.web.rest.errors;

public class UnsupportedMediaTypeException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public UnsupportedMediaTypeException() {
        super(ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Unsupported media type!", "fileManagement", "mediatype");
    }
}
