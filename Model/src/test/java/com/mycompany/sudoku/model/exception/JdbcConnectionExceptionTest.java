package com.mycompany.sudoku.model.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.sudoku.model.exceptions.jdbc.JdbcConnectionException;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcConnectionExceptionTest {
    @Test
    public void constructorTest() {
        JdbcConnectionException e = new JdbcConnectionException();
    }

    @Test
    public void parametrizedConstructorTest() {
        JdbcConnectionException e = new JdbcConnectionException("test message");
        assertEquals(e.getMessage(),"test message");
    }

    @Test
    public void parametrizedConstructorTest2() {
        JdbcConnectionException e = new JdbcConnectionException("test message",new SQLException());
        assertEquals(e.getMessage(),"test message");
        assertEquals(e.getCause().getClass(),SQLException.class);
    }

    @Test
    public void getLocalizedMessageTest() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.JdbcExceptions");
        JdbcConnectionException e = new JdbcConnectionException(
                JdbcConnectionException.CONNECTION_ERROR);
        assertEquals(bundle.getString(JdbcConnectionException.CONNECTION_ERROR),
                e.getLocalizedMessage());
    }
}
