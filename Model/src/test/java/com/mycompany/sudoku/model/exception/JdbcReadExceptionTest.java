package com.mycompany.sudoku.model.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.sudoku.model.exceptions.jdbc.JdbcReadException;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcReadExceptionTest {
    @Test
    public void constructorTest() {
        JdbcReadException e = new JdbcReadException();
    }

    @Test
    public void parametrizedConstructorTest() {
        JdbcReadException e = new JdbcReadException("test message");
        assertEquals(e.getMessage(),"test message");
    }

    @Test
    public void parametrizedConstructorTest2() {
        JdbcReadException e = new JdbcReadException("test message",new SQLException());
        assertEquals(e.getMessage(),"test message");
        assertEquals(e.getCause().getClass(),SQLException.class);
    }

    @Test
    public void getLocalizedMessageTest() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.JdbcExceptions");
        JdbcReadException e = new JdbcReadException(JdbcReadException.READ_ERROR);
        assertEquals(bundle.getString(JdbcReadException.READ_ERROR), e.getLocalizedMessage());
    }
}
