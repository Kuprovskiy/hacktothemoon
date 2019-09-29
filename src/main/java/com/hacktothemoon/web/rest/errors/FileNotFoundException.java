package com.hacktothemoon.web.rest.errors;

public class FileNotFoundException extends BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public FileNotFoundException() {
        super(ErrorConstants.DEFAULT_TYPE, "File not found exception", "fileManagement", "filenotfound");
    }
}
