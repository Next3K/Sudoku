package com.mycompany.sudoku.model.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mycompany.sudoku.model.BacktrackingSudokuSolver;
import com.mycompany.sudoku.model.SudokuBoard;
import com.mycompany.sudoku.model.SudokuSolver;
import org.junit.jupiter.api.Test;

public class SudokuPrototypeHandlerTest {

    @Test
    public void testPut() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuPrototypeHandler handler = new SudokuPrototypeHandler(solver);
        SudokuBoard testBoard1 = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard testBoard2 = new SudokuBoard(new BacktrackingSudokuSolver());
        testBoard1.solveGame();
        testBoard2.solveGame();
        handler.put("one",testBoard1);
        handler.put("two",testBoard2);
        var copy1 = handler.get("one");
        var copy2 = handler.get("two");
        assertNotSame(testBoard1,copy1);
        assertEquals(testBoard1,copy1);
        assertNotSame(testBoard2,copy2);
        assertEquals(testBoard2,copy2);
    }

    @Test
    public void testGet() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuPrototypeHandler handler = new SudokuPrototypeHandler(solver);
        SudokuBoard emptyBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuBoard testBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuValidityTester tester = new SudokuValidityTester();
        assertEquals(handler.get("empty"),emptyBoard);
        assertTrue(tester.isSudokuValid(handler.get("solved")));
        handler.put("test",testBoard);
        SudokuBoard returned = handler.get("test");
        assertNotSame(testBoard,returned);
        assertEquals(testBoard,returned);
        assertNull(handler.get("none"));

    }

    @Test
    public void testConstructor() {
        SudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuPrototypeHandler handler = new SudokuPrototypeHandler(solver);
        SudokuBoard emptyBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        SudokuValidityTester tester = new SudokuValidityTester();
        assertEquals(handler.get("empty"),emptyBoard);
        assertTrue(tester.isSudokuValid(handler.get("solved")));
    }

}
