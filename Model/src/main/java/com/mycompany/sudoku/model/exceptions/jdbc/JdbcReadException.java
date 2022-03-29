package com.mycompany.sudoku.model.exceptions.jdbc;

import com.mycompany.sudoku.model.exceptions.AppException;
import java.util.ResourceBundle;

public class JdbcReadException extends AppException {

    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles.JdbcExceptions");
    public static final String READ_ERROR = "failed.read.from.database";
    public static final String READ_TABLE_ERROR = "failed.read.table.from.database";
    public static final String READ_FIELDS_ERROR = "failed.read.fields.from.database";
    public static final String ID_ERROR = "failed.to.read.id";


    public JdbcReadException() {

    }

    public JdbcReadException(String message) {
        super(message);
    }

    public JdbcReadException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        return bundle.getString(getMessage());
    }
}
