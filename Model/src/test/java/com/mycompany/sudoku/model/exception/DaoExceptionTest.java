package com.mycompany.sudoku.model.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.sudoku.model.exceptions.AppException;
import com.mycompany.sudoku.model.exceptions.DaoException;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class DaoExceptionTest {
    @Test
    public void constructorOneTest() {
        DaoException e = new DaoException();
    }

    @Test
    public void constructorTwoTest() {
        AppException e = new DaoException("test message");
        assertEquals(e.getMessage(),"test message");
    }

    @Test
    public void constructorThreeTest() {
        AppException e = new DaoException("test message", new IOException("abcd"));
        assertEquals(e.getMessage(),"test message");
        assertEquals(e.getCause().getClass(),IOException.class);
    }
}
