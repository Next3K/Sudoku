package com.mycompany.sudoku.model.dao;

import com.mycompany.sudoku.model.exceptions.AppException;


public interface Dao<T> extends AutoCloseable {
    
    T read() throws AppException;
    
    void write(T obj) throws AppException;

}
