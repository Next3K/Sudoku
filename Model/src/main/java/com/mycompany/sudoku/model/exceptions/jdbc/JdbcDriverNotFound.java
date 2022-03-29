package com.mycompany.sudoku.model.exceptions.jdbc;

import com.mycompany.sudoku.model.exceptions.AppException;
import java.util.ResourceBundle;

public class JdbcDriverNotFound extends AppException {

    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles.JdbcExceptions");
    public static final String DRIVER_NOT_FOUND = "database.driver.error";

    public JdbcDriverNotFound() {
    }

    public JdbcDriverNotFound(String message) {
        super(message);
    }

    public JdbcDriverNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        return bundle.getString(getMessage());
    }
}
