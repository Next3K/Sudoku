package com.mycompany.sudoku.model.helper;

import com.mycompany.sudoku.model.SudokuBoard;
import com.mycompany.sudoku.model.SudokuSolver;
import java.util.HashMap;
import java.util.Map;

public class SudokuPrototypeHandler {

    private final Map<String, SudokuBoard> boards = new HashMap<>();


    public SudokuPrototypeHandler(SudokuSolver solver) {
        SudokuBoard empty = new SudokuBoard(solver);
        SudokuBoard solved = new SudokuBoard(solver);
        solved.solveGame();
        this.put("empty",empty); // empty sudoku board
        this.put("solved",solved); // solved sudoku board
    }

    public SudokuBoard get(String key) {
        try {
            SudokuBoard sudokuBoard = boards.get(key);
            if (sudokuBoard == null) {
                return null;
            } else {
                return sudokuBoard.clone();
            }
        } catch (CloneNotSupportedException  a) {
            return null;
        }
    }

    public void put(String key, SudokuBoard board) {
        boards.put(key,board);
    }
}
