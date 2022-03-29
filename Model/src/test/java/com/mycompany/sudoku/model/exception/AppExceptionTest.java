package com.mycompany.sudoku.model.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mycompany.sudoku.model.exceptions.AppException;
import org.junit.jupiter.api.Test;
import java.io.IOException;


public class AppExceptionTest {
    @Test
    public void constructorOneTest() {
        AppException e = new AppException();
    }

    @Test
    public void constructorTwoTest() {
        AppException e = new AppException("test message");
        assertEquals(e.getMessage(),"test message");
    }

    @Test
    public void constructorThreeTest() {
        AppException e = new AppException("test message", new IOException("abcd"));
        assertEquals(e.getMessage(),"test message");
        assertEquals(e.getCause().getClass(),IOException.class);
    }
}






