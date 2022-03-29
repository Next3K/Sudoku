package com.mycompany.sudoku.model.dao;

import com.mycompany.sudoku.model.SudokuBoard;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcCantCreateTables;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcDriverNotFound;

public class SudokuBoardDaoFactory {
    
    public Dao<SudokuBoard> createFileDao(String filename) {
        return new FileSudokuBoardDao(filename);
    }

    public Dao<SudokuBoard> createJdbcDao(String filename) {
        try {
            return new JdbcSudokuBoardDao(filename);
        } catch (JdbcDriverNotFound e) {
            e.printStackTrace();
        } catch (JdbcCantCreateTables e) {
            e.printStackTrace();
        }
        return null;
    }
}
