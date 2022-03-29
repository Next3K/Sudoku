package com.mycompany.sudoku.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mycompany.sudoku.model.helper.SudokuValidityTester;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * Testy SudokuBoard.
 *
 * @author pawel & *****
 */
public class SudokuBoardTest {
    private SudokuBoard sudoku;
    private final SudokuValidityTester tester = new SudokuValidityTester();
    private final SudokuSolver solver = new BacktrackingSudokuSolver();


    private final int[][] invalidTestBoard = {
            {4, 3, 5, 2, 6, 9, 7, 8, 1},
            {6, 8, 2, 5, 7, 1, 4, 9, 3},
            {1, 9, 7, 8, 3, 4, 5, 6, 2},
            {8, 2, 6, 1, 9, 5, 3, 4, 7},
            {3, 7, 4, 6, 8, 2, 9, 1, 5},
            {9, 5, 1, 7, 4, 3, 6, 2, 8},
            {5, 1, 9, 3, 2, 6, 8, 7, 4},
            {2, 4, 8, 9, 5, 7, 1, 3, 6},
            {7, 6, 3, 4, 1, 8, 2, 9, 9} // invalid number
    };

    private final int[][] invalidTestBoard2 = {
            {4, 4, 5, 2, 6, 9, 7, 8, 1}, // invalid 4
            {6, 8, 2, 5, 7, 1, 4, 9, 3},
            {1, 9, 7, 8, 3, 4, 5, 6, 2},
            {8, 2, 6, 1, 9, 5, 3, 4, 7},
            {3, 7, 4, 6, 8, 2, 9, 1, 5},
            {9, 5, 1, 7, 4, 3, 6, 2, 8},
            {5, 1, 9, 3, 2, 6, 8, 7, 4},
            {2, 4, 8, 9, 5, 7, 1, 3, 6},
            {7, 6, 3, 4, 1, 8, 2, 9, 9}
    };

    private final int[][] invalidTestBoard3 = {
            {4, 3, 1, 2, 6, 9, 7, 8, 1}, // invalid first region
            {6, 8, 2, 5, 7, 1, 4, 9, 3},
            {1, 9, 7, 8, 3, 4, 5, 6, 2},
            {8, 2, 6, 1, 9, 5, 3, 4, 7},
            {3, 7, 4, 6, 8, 2, 9, 1, 5},
            {9, 5, 1, 7, 4, 3, 6, 2, 8},
            {5, 1, 9, 3, 2, 6, 8, 7, 4},
            {2, 4, 8, 9, 5, 7, 1, 3, 6},
            {7, 6, 3, 4, 1, 8, 2, 9, 9}
    };

    private final int[][] emptyBoard = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    // private final int[][] validTestBoard = {
    //         {4, 3, 5, 2, 6, 9, 7, 8, 1},
    //         {6, 8, 2, 5, 7, 1, 4, 9, 3},
    //         {1, 9, 7, 8, 3, 4, 5, 6, 2},
    //         {8, 2, 6, 1, 9, 5, 3, 4, 7},
    //         {3, 7, 4, 6, 8, 2, 9, 1, 5},
    //         {9, 5, 1, 7, 4, 3, 6, 2, 8},
    //         {5, 1, 9, 3, 2, 6, 8, 7, 4},
    //         {2, 4, 8, 9, 5, 7, 1, 3, 6},
    //         {7, 6, 3, 4, 1, 8, 2, 5, 9}
    // };

    // private SudokuField[][] Helper.convert(int [][] tmp) {
    //     SudokuField [][] tmpFields = new SudokuField[9][9];
    //     for (int i = 0; i < 9; i++) {
    //         for (int j = 0; j < 9; j++) {
    //             tmpFields[i][j] = new SudokuField(tmp[i][j]);
    //         }
    //     }
    //     return tmpFields;
    // }

    /**
     * Test of solveGame method, of class SudokuBoard.
     */
    @Test
    public void testSolveGame_generatesValidBoard() {
        for (int i = 0; i < 1000; i++) {
            sudoku = new SudokuBoard(solver);
            sudoku.solveGame();
            assertTrue(tester.isSudokuValid(sudoku));
        }
    }

    /**
     * Test uniqueness of two consecutive generations.
     */
    @Test
    public void testSolveGame_generatesUnique() {
        sudoku = new SudokuBoard(solver);
        sudoku.solveGame();

        assertTrue(tester.isSudokuValid(sudoku));

        int[] copy1 = new int[81];
        for (int i = 0, k = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++, k++) {
                copy1[k] = sudoku.get(i, j);
            }
        }

        sudoku.solveGame();
        assertTrue(tester.isSudokuValid(sudoku));
        int[] copy2 = new int[81];

        for (int i = 0, k = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++, k++) {
                copy2[k] = sudoku.get(i, j);
            }
        }
        assertNotEquals(Arrays.toString(copy1), Arrays.toString(copy2));
    }

    /**
     * test whether collision chance is lower than 1%.
     */
    @Test
    public void testsolveGame_collisionChance() {
        SudokuBoard sudoku = new SudokuBoard(solver);

        ArrayList<Integer> hashArray = new ArrayList<>(1000);
        int numberOfGenerations = 1000;
        for (int i = 0; i < numberOfGenerations; i++) { // generate board 1000 times
            sudoku.solveGame();
            int[][] tmp = new int[9][9];
            for (int j = 0; j < 9; j++) { // insert int values to temporary board
                for (int k = 0; k < 9; k++) {
                    tmp[j][k] = sudoku.get(j, k);
                }
            }
            hashArray.add(Arrays.deepHashCode(tmp));
        }

        // expect number of unique board to be at least 99%
        assertTrue(numberOfGenerations * 0.99 <= hashArray.stream().distinct().count());
    }

    /**
     * Test of getBoard method, of class SudokuBoard.
     */
    @Test
    public void testGetBoard() {
        sudoku = new SudokuBoard(new BacktrackingSudokuSolver());
        sudoku.setBoard(Helper.convert(Helper.validTestBoard));
        var memberBoard = sudoku.getBoard();
        assertNotEquals(sudoku.getBoard(), memberBoard); // different references
        for (int i = 0; i < 9; i++) { // but the same content
            for (int j = 0; j < 9; j++) {
                assertEquals(sudoku.get(i, j), memberBoard[i][j].getField());
            }
        }
    }

    /**
     * Test of getCell method, of class SudokuBoard.
     */
    @Test
    public void testGetCell() {
        sudoku = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(0, sudoku.get(0, 0));
        sudoku.setBoard(Helper.convert(Helper.validTestBoard));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(Helper.validTestBoard[i][j], sudoku.get(i, j));
                sudoku.set(i, j, 0);
                assertEquals(0, sudoku.get(i, j));
            }
        }
    }

    /**
     * Test of setSudokuSolver method, of class SudokuBoard.
     */
    @Test
    public void testSetSudokuSolver() {
        sudoku = new SudokuBoard(solver);
        SudokuSolver solver = new BacktrackingSudokuSolver();
        sudoku.setSudokuSolver(solver);
        assertEquals(solver.getClass(), sudoku.getSudokuSolver().getClass());
    }

    /**
     * Test of getSudokuSolver method, of class SudokuBoard.
     */
    @Test
    public void testGetSudokuSolver() {
        sudoku = new SudokuBoard(solver);
        SudokuSolver solver = new BacktrackingSudokuSolver();
        assertEquals(solver.getClass(), sudoku.getSudokuSolver().getClass());
    }

    /**
     * Test of checkBoard method, of class SudokuBoard.
     */
    @Test
    public void testCheckBoard() {
        sudoku = new SudokuBoard(solver);
        sudoku.setBoard(Helper.convert(Helper.validTestBoard));
        assertTrue(sudoku.checkBoard());
        sudoku.setBoard(Helper.convert(invalidTestBoard));
        assertFalse(sudoku.checkBoard());
        sudoku.setBoard(Helper.convert(invalidTestBoard2));
        assertFalse(sudoku.checkBoard());
        sudoku.setBoard(Helper.convert(invalidTestBoard3));
        assertFalse(sudoku.checkBoard());
        sudoku.setBoard(Helper.convert(emptyBoard));
        assertFalse(sudoku.checkBoard());
    }

    /**
     * Test of getColumn method, of class SudokuBoard.
     */
    @Test
    void testGetColumn() {
        sudoku = new SudokuBoard(solver);
        sudoku.solveGame();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(sudoku.get(i, j),
                        sudoku.getColumn(j).getFields()[i].getField());
            }
        }
    }


    /**
     * Test of getRow method, of class SudokuBoard.
     */
    @Test
    void testGetRow() {
        sudoku = new SudokuBoard(solver);
        sudoku.setBoard(Helper.convert(Helper.validTestBoard));
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(
                        Helper.validTestBoard[i][j], 
                        sudoku.getRow(i).getFields()[j].getField()
                );
            }
        }
    }


    /**
     * Test of getBlock method, of class SudokuBoard.
     */
    @Test
    void testGetBlock() {
        sudoku = new SudokuBoard(solver);
        sudoku.setBoard(Helper.convert(Helper.validTestBoard));
        int[] values = {4, 3, 5, 6, 8, 2, 1, 9, 7};
        for (int i = 0; i < 9; i++) {
            assertEquals(values[i], sudoku.getBlock(0, 0).getFields()[i].getField());
        }

    }


    /**
     * Test of class SudokuBoard's copy constructor.
     */
    @Test
    void copyConstructorTest() {
        sudoku = new SudokuBoard(new BacktrackingSudokuSolver());
        sudoku.solveGame();
        SudokuBoard sudokuCopy = new SudokuBoard(sudoku);

        // different references
        assertNotSame(sudokuCopy, sudoku); // different references
        assertNotEquals(sudokuCopy.getSudokuSolver(), sudoku.getSudokuSolver());
        assertNotEquals(sudokuCopy.getBoard(), sudoku.getBoard());
        // the same class types
        assertEquals(sudoku.getClass(), sudokuCopy.getClass());
        assertEquals(sudoku.getSudokuSolver().getClass(), sudokuCopy.getSudokuSolver().getClass());
        // the same content
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                assertEquals(sudoku.get(i, j), sudokuCopy.get(i, j));
            }
        }
    }

    @Test
    void hashCodeTest() {
        SudokuBoard sudoku1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudoku2 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode()); // empty sudoku
        sudoku1.setBoard(Helper.convert(Helper.validTestBoard));
        sudoku2.setBoard(Helper.convert(Helper.validTestBoard));
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode()); // full sudoku
        sudoku1.setBoard(Helper.convert(invalidTestBoard));
        sudoku2.setBoard(Helper.convert(invalidTestBoard));
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode());
        sudoku1.setBoard(Helper.convert(invalidTestBoard2));
        assertNotEquals(sudoku1.hashCode(), sudoku2.hashCode());
    }

    @Test
    void equalsTest() {
        SudokuBoard sudoku1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard sudoku2 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertEquals(sudoku1, sudoku2); // empty sudoku
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode()); // ensure hashCode <-> equals
        assertEquals(sudoku1, sudoku1);
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode()); // ensure hashCode <-> equals
        assertNotEquals(sudoku1, null);
        assertNotEquals(sudoku1, new Object());
        sudoku1.setBoard(Helper.convert(Helper.validTestBoard));
        sudoku2.setBoard(Helper.convert(Helper.validTestBoard));
        assertEquals(sudoku1, sudoku2); // full sudoku
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode()); // ensure hashCode <-> equals
        sudoku1.setBoard(Helper.convert(invalidTestBoard));
        sudoku2.setBoard(Helper.convert(invalidTestBoard));
        assertEquals(sudoku1, sudoku2);
        assertEquals(sudoku1.hashCode(), sudoku2.hashCode()); // ensure hashCode <-> equals
        sudoku1.setBoard(Helper.convert(invalidTestBoard2));
        assertNotEquals(sudoku1, sudoku2);
    }

    @Test
    void toStringTest() {
        SudokuBoard sudoku1 = new SudokuBoard(new BacktrackingSudokuSolver());
        assertNotEquals(" ", sudoku1.toString());
    }

    @Test
    public void cloneTest() {
        SudokuBoard sudoku = new SudokuBoard(new BacktrackingSudokuSolver());
        try {
            SudokuBoard newSudoku = sudoku.clone();
            assertNotSame(sudoku,newSudoku);
            assertEquals(sudoku,newSudoku);
            // zapewnij rozłącznosc elementow
            //List<List<SudokuField>> board1 = sudoku.getUnmodifiableBoard();
            //List<List<SudokuField>> board2 = newSudoku.getUnmodifiableBoard();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    //assertNotSame(board1.get(i).get(j),board2.get(i).get(j));
                    //assertEquals(board1.get(i).get(j),board2.get(i).get(j));
                    assertNotSame(sudoku.getBoard()[i][j],sudoku.getBoard()[i][j]);
                }
            }

        } catch (CloneNotSupportedException e) {
            System.out.print("Clone not supported.");
        }

    }

    //@Test
    //public void getUnmodifiableBoardTest() {
    //    sudoku = new SudokuBoard(new BacktrackingSudokuSolver());
    //    sudoku.setBoard(Helper.convert(Helper.validTestBoard));
    //    for (int i = 0; i < 9; i++) {
    //        for (int j = 0; j < 9; j++) {
    //            assertEquals(sudoku.getUnmodifiableBoard().get(i).get(j),sudoku.get(i,j));
    //        }
    //    }
    //}

}