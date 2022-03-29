package com.mycompany.sudoku.model;

import com.mycompany.sudoku.model.dao.FileSudokuBoardDao;
import com.mycompany.sudoku.model.elements.SudokuField;
import java.io.File;

/**
 * Just some helper functions and variables.
 */
public class Helper {

    /**
     * The valid test board.
     */
    public static final int[][] validTestBoard = {
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

    /**
     * Convert int matrix to SudokuField matrix.
     *
     * @param matrix int[][]
     * @return SudokuField[][]
     */
    public static SudokuField[][] convert(int[][] matrix) {
        SudokuField[][] tmpSudoku = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                tmpSudoku[i][j] = new SudokuField();
                tmpSudoku[i][j].setField(matrix[i][j]);
            }
        }
        return tmpSudoku;
    }

    /**
     * Convert SudokuField matrix to int matrix.
     *
     * @param fields SudokuField[]
     * @return int[]
     */
    public static int[] convert(SudokuField[] fields) {
        int[] fieldsInt = new int[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldsInt[i] = fields[i].getField();
        }
        return fieldsInt;
    }


    /**
     * Deletes directory recursively.
     *
     * @param directory the directory to delete
     */
    public static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                file.delete();
            }
            directory.delete();
        }
    }

    public static void createDirectory(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Creates file with valid board to test.
     *
     * @param filename name of the file
     */
    public static void createTestFile(String filename) {
        
        try (FileSudokuBoardDao file = new FileSudokuBoardDao(filename);) {
            // file = new FileSudokuBoardDao(filename);
            SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
            board.setBoard(convert(validTestBoard));
            file.write(board);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
