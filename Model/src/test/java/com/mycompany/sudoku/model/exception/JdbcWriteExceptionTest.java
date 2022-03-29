package com.mycompany.sudoku.model.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.sudoku.model.exceptions.jdbc.JdbcDriverNotFound;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcWriteException;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcWriteExceptionTest {
    @Test
    public void constructorTest() {
        JdbcWriteException e = new JdbcWriteException();
    }

    @Test
    public void parametrizedConstructorTest() {
        JdbcWriteException e = new JdbcWriteException("test message");
        assertEquals(e.getMessage(),"test message");
    }

    @Test
    public void parametrizedConstructorTest2() {
        JdbcWriteException e = new JdbcWriteException("test message",new SQLException());
        assertEquals(e.getMessage(),"test message");
        assertEquals(e.getCause().getClass(),SQLException.class);
    }

    @Test
    public void getLocalizedMessageTest() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.JdbcExceptions");
        JdbcWriteException e = new JdbcWriteException(JdbcWriteException.WRITE_ERROR);
        assertEquals(bundle.getString(JdbcWriteException.WRITE_ERROR), e.getLocalizedMessage());
    }
}
