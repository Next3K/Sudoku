package com.mycompany.sudoku.model.exceptions.jdbc;

import com.mycompany.sudoku.model.exceptions.AppException;
import java.util.ResourceBundle;

public class JdbcCantCreateTables extends AppException {

    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles.JdbcExceptions");
    public static final String CANT_CREATE_TABLES = "cant.create.database.tables";

    public JdbcCantCreateTables() {
    }

    public JdbcCantCreateTables(String message) {
        super(message);
    }

    public JdbcCantCreateTables(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        return bundle.getString(getMessage());
    }
}
