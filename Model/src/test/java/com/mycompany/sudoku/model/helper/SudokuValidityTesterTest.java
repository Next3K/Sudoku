package com.mycompany.sudoku.model.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mycompany.sudoku.model.BacktrackingSudokuSolver;
import com.mycompany.sudoku.model.SudokuBoard;
import org.junit.jupiter.api.Test;


public class SudokuValidityTesterTest {
    private final int[][] validTestBoard = {
            {4, 3, 5, 2, 6, 9, 7, 8, 1},
            {6, 8, 2, 5, 7, 1, 4, 9, 3},
            {1, 9, 7, 8, 3, 4, 5, 6, 2},
            {8, 2, 6, 1, 9, 5, 3, 4, 7},
            {3, 7, 4, 6, 8, 2, 9, 1, 5},
            {9, 5, 1, 7, 4, 3, 6, 2, 8},
            {5, 1, 9, 3, 2, 6, 8, 7, 4},
            {2, 4, 8, 9, 5, 7, 1, 3, 6},
            {7, 6, 3, 4, 1, 8, 2, 5, 9}
    };
    private final int[][] invalidTestBoardWrongValues = {
            {4, 3, 5, 2, 6, 9, 71, 8, 1},
            {6, 83, 2, 5, 7, 1, 4, 9, 3},
            {1, 9, 7, 8, 3, 4, 5, 6, 2},
            {8, 2, 6, 1, 97, 5, 3, 4, 7},
            {3, 7, 4, 6, 8, 2, 9, 1, 5},
            {9, 5, 1, 7, 4, 3, 6, 2, 8},
            {5, 1, 9, 3, 2, 0, 8, 7, 4},
            {2, 4, 8, 9, 5, 7, 1, 3, 6},
            {7, 6, 3, 4, 1, 8, 2, 5, 9}
    };
    private final int[][] invalidTestBoardRegion = {
            {4, 2, 5, 2, 6, 9, 7, 8, 1},
            {6, 8, 2, 5, 7, 1, 4, 9, 3},
            {1, 9, 7, 8, 3, 4, 5, 6, 2},
            {8, 2, 6, 1, 9, 5, 3, 4, 7},
            {3, 7, 4, 6, 8, 2, 9, 1, 5},
            {9, 5, 1, 7, 4, 3, 6, 2, 8},
            {5, 1, 9, 3, 2, 6, 8, 7, 4},
            {2, 4, 8, 9, 5, 7, 1, 3, 6},
            {7, 6, 3, 4, 1, 8, 2, 5, 9}
    };
    private final int[][] invalidTestBoardRow = {
            {4, 2, 5, 2, 6, 1, 7, 8, 1}, // invalid Row
            {6, 8, 2, 5, 7, 9, 4, 1, 3},
            {1, 9, 7, 8, 3, 4, 5, 6, 2},
            {8, 2, 6, 1, 9, 5, 3, 4, 7},
            {3, 7, 4, 6, 8, 2, 9, 1, 5},
            {9, 5, 1, 7, 4, 3, 6, 2, 8},
            {5, 1, 9, 3, 2, 6, 8, 7, 4},
            {2, 4, 8, 9, 5, 7, 1, 3, 6},
            {7, 6, 3, 4, 1, 8, 2, 5, 9}
    };
    private final int[][] invalidTestBoardColumn = {
            {4, 3, 5, 2, 6, 9, 7, 8, 1},
            {6, 7, 2, 5, 7, 1, 4, 9, 3},
            {1, 9, 7, 8, 3, 4, 5, 6, 2},
            {8, 2, 6, 1, 9, 5, 3, 4, 7},
            {3, 7, 4, 6, 8, 2, 9, 1, 5},
            {9, 5, 1, 7, 4, 3, 6, 2, 8},
            {5, 1, 9, 3, 2, 6, 8, 7, 4},
            {2, 4, 8, 9, 5, 7, 1, 3, 6},
            {7, 6, 3, 4, 1, 8, 2, 5, 9}
    };


    /**
     * Test of validityTester, helper test function.
     */
    @Test
    public void validityTesterTest() {
        SudokuBoard testSudoku = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuValidityTester tester = new SudokuValidityTester();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testSudoku.set(i, j, validTestBoard[i][j]);
            }
        }
        assertTrue(tester.isSudokuValid(testSudoku));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testSudoku.set(i, j, invalidTestBoardWrongValues[i][j]);
            }
        }
        assertFalse(tester.isSudokuValid(testSudoku));

    }

    @Test
    public void checkSegmentsTest() {
        SudokuBoard testSudoku = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuValidityTester tester = new SudokuValidityTester();


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testSudoku.set(i, j, invalidTestBoardRegion[i][j]);
            }
        }
        assertFalse(tester.checkSegments(testSudoku, 'b', 2));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testSudoku.set(i, j, invalidTestBoardColumn[i][j]);
            }
        }
        assertFalse(tester.checkSegments(testSudoku, 'c', 7));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testSudoku.set(i, j, invalidTestBoardRow[i][j]);
            }
        }
        assertFalse(tester.checkSegments(testSudoku, 'r', 2));

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    tester.checkSegments(testSudoku, 'g', 2);
                });
        String expected = "segmentIdentifier must be one of ['b', 'r', 'c'] and you passed g";
        String actual = exception.getMessage();
        
        assertEquals(expected, actual);
    }
}
