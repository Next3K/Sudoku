package com.mycompany.sudoku.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mycompany.sudoku.model.helper.SudokuValidityTester;
import org.junit.jupiter.api.Test;

public class SudokuSolverTest {
    private final SudokuValidityTester tester = new SudokuValidityTester();
    private final SudokuBoard sudoku = new SudokuBoard(new BacktrackingSudokuSolver());

    @Test
    public void solveTest() {
        var solver = sudoku.getSudokuSolver();
        solver.solve(sudoku);
        assertTrue(tester.isSudokuValid(sudoku));
    }
}
