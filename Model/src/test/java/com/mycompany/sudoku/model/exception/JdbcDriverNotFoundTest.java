package com.mycompany.sudoku.model.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.sudoku.model.exceptions.jdbc.JdbcDriverNotFound;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class JdbcDriverNotFoundTest {
    @Test
    public void constructorTest() {
        JdbcDriverNotFound e = new JdbcDriverNotFound();
    }

    @Test
    public void parametrizedConstructorTest() {
        JdbcDriverNotFound e = new JdbcDriverNotFound("test message");
        assertEquals(e.getMessage(),"test message");
    }

    @Test
    public void parametrizedConstructorTest2() {
        JdbcDriverNotFound e = new JdbcDriverNotFound("test message",new SQLException());
        assertEquals(e.getMessage(),"test message");
        assertEquals(e.getCause().getClass(),SQLException.class);
    }

    @Test
    public void getLocalizedMessageTest() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.JdbcExceptions");
        JdbcDriverNotFound e = new JdbcDriverNotFound(JdbcDriverNotFound.DRIVER_NOT_FOUND);
        assertEquals(bundle.getString(JdbcDriverNotFound.DRIVER_NOT_FOUND),
                e.getLocalizedMessage());
    }
}
