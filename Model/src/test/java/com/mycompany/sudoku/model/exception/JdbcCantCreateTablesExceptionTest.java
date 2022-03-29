package com.mycompany.sudoku.model.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.sudoku.model.exceptions.jdbc.JdbcCantCreateTables;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcCantCreateTablesExceptionTest {
    @Test
    public void constructorTest() {
        JdbcCantCreateTables e = new JdbcCantCreateTables();
    }

    @Test
    public void parametrizedConstructorTest() {
        JdbcCantCreateTables e = new JdbcCantCreateTables("test message");
        assertEquals(e.getMessage(),"test message");
    }

    @Test
    public void parametrizedConstructorTest2() {
        JdbcCantCreateTables e = new JdbcCantCreateTables("test message",new SQLException());
        assertEquals(e.getMessage(),"test message");
        assertEquals(e.getCause().getClass(),SQLException.class);
    }

    @Test
    public void getLocalizedMessageTest() {
        ResourceBundle bundle = ResourceBundle.getBundle("bundles.JdbcExceptions");
        JdbcCantCreateTables e = new JdbcCantCreateTables(JdbcCantCreateTables.CANT_CREATE_TABLES);
        assertEquals(bundle.getString(JdbcCantCreateTables.CANT_CREATE_TABLES),
                e.getLocalizedMessage());
    }
}
