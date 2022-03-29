package com.mycompany.sudoku.model;

public interface SudokuSolver {
    void solve(SudokuBoard board);

    SudokuSolver newInstance();
}
