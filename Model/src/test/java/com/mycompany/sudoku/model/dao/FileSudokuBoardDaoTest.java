package com.mycompany.sudoku.model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mycompany.sudoku.model.BacktrackingSudokuSolver;
import com.mycompany.sudoku.model.Helper;
import com.mycompany.sudoku.model.SudokuBoard;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import com.mycompany.sudoku.model.exceptions.DaoException;
import org.junit.After;
import org.junit.jupiter.api.Test;


class FileSudokuBoardDaoTest {

    @After
    public void deleteAllFiles() {
        Helper.deleteDirectory(
                new File(
                        "." + File.separator + "files" + File.separator
                )
        );
    }

    @Test
    void constructorTest() {
        File directory = new File("." + File.separator + "files" + File.separator);
        Helper.deleteDirectory(directory);
        assertFalse(directory.exists());

        FileSudokuBoardDao file = new FileSudokuBoardDao("constructorTest.txt");
        assertTrue(directory.exists());
        FileSudokuBoardDao file2 = new FileSudokuBoardDao("constructorTest.txt");

    }


    @Test
    void writeTest() throws DaoException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.setBoard(Helper.convert(Helper.validTestBoard));

        File directory = new File("." + File.separator + "files" + File.separator);
        Helper.deleteDirectory(directory);
        FileSudokuBoardDao file = new FileSudokuBoardDao("writeTest.txt");

        assertFalse(file.getFile().exists());
        Helper.deleteDirectory(directory);

        String[] expected = {"Failed to write to the file",
                "Nieudana proba zapisu"};
        List<String> expectedTitlesList = Arrays.asList(expected);


        String message = assertThrows(
                DaoException.class,
                () -> {
                    file.write(board);
                }
        ).getLocalizedMessage();

        assertTrue(expectedTitlesList.contains((message)));

        Helper.createDirectory(directory);
        file.write(board);
        file.getFile().setReadOnly();

        String[] expectedAccess = {".\\files\\writeTest.txt (Access is denied)",
                ".\\files\\writeTest.txt (Odmowa dostępu)"};
        List<String> expectedAccessList = Arrays.asList(expectedAccess);

        String messageAccess = assertThrows(
                DaoException.class,
                () -> {
                    file.write(board);
                }
        ).getCause().getMessage();

        // assertTrue(expectedAccessList.contains((messageAccess)));
    }

    @Test
    void readTest() throws DaoException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.setBoard(Helper.convert(Helper.validTestBoard));

        File directory = new File("." + File.separator + "files" + File.separator);
        Helper.deleteDirectory(directory);
        FileSudokuBoardDao file = new FileSudokuBoardDao("readTest.txt");
        file.write(board);

        SudokuBoard board2 = file.read();
        assertEquals(board.toString(), board2.toString());

        Helper.deleteDirectory(directory);

        String[] expectedRead =
                {"Failed to read from the file",
                        "Nieudana proba odczytu"};
        List<String> expectedTitlesList = Arrays.asList(expectedRead);

        String messageRead = assertThrows(
                DaoException.class,
                () -> {
                    file.read();
                }
        ).getLocalizedMessage();

        // assertTrue(expectedTitlesList.contains((messageRead)));
    }

    @Test
    void closeTest() throws Exception {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        board.setBoard(Helper.convert(Helper.validTestBoard));
        FileSudokuBoardDao file = new FileSudokuBoardDao("closeTest.txt");
        file.close();
    }
}