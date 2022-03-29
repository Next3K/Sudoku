package com.mycompany.sudoku.model.dao;



import com.mycompany.sudoku.model.Helper;
import com.mycompany.sudoku.model.SudokuBoard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.io.File;

class SudokuBoardDaoFactoryTest {

    @AfterEach
    void tearDown() {
        Helper.deleteDirectory(
                new File(
                        "." + File.separator + "files" + File.separator
                )
        );
    }

    @Test
    void createFileDao() {
        
        SudokuBoardDaoFactory factory  = new SudokuBoardDaoFactory();
        
        Dao<SudokuBoard> dao = factory.createFileDao("daoTest");
    }


    @Test
    public void createJdbcDao() {
        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("factoryTest")) {
            dao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}