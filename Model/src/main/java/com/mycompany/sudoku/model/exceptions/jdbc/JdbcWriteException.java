package com.mycompany.sudoku.model.exceptions.jdbc;

import com.mycompany.sudoku.model.exceptions.AppException;
import java.util.ResourceBundle;

public class JdbcWriteException extends AppException {

    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles.JdbcExceptions");
    public static final String WRITE_ERROR = "failed.to.write.to.database";
    public static final String WRITE_FILED_ERROR = "failed.to.write.field.to.database";
    public static final String NAME_USED = "provided.name.in.use";

    public JdbcWriteException() {

    }

    public JdbcWriteException(String message) {
        super(message);
    }

    public JdbcWriteException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getLocalizedMessage() {
        return bundle.getString(getMessage());
    }
}
