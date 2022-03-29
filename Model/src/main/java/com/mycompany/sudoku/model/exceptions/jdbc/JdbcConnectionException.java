package com.mycompany.sudoku.model.exceptions.jdbc;

import com.mycompany.sudoku.model.exceptions.AppException;
import java.util.ResourceBundle;

public class JdbcConnectionException extends AppException {

    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles.JdbcExceptions");
    public static final String CONNECTION_ERROR = "database.connection.error";

    public JdbcConnectionException() {

    }

    public JdbcConnectionException(String message) {
        super(message);
    }

    public JdbcConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        return bundle.getString(getMessage());
    }
}
