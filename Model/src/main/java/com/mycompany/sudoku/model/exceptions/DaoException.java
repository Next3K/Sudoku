package com.mycompany.sudoku.model.exceptions;

import java.util.ResourceBundle;

public class DaoException extends AppException {

    public static final String NULL_NAME = "null.name";
    public static final String BAD_FILE = "bad.file";
    public static final String FAILED_TO_WRITE_FILE = "failed.write";
    public static final String FAILED_TO_READ_FILE = "failed.read";
    public static final String FAILED_TO_CLOSE = "failed.to.close";

    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles.exception");

    public DaoException() {
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        return bundle.getString(getMessage());
    }
}
