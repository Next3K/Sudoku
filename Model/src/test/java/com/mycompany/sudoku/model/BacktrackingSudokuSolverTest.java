package com.mycompany.sudoku.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mycompany.sudoku.model.helper.SudokuValidityTester;
import org.junit.jupiter.api.Test;


public class BacktrackingSudokuSolverTest {

    private final SudokuValidityTester tester = new SudokuValidityTester();


    @Test
    public void solve() {
        BacktrackingSudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudoku = new SudokuBoard(solver);
        solver.solve(sudoku);
        assertTrue(tester.isSudokuValid(sudoku));
    }
}
