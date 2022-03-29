package com.mycompany.sudoku.model.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mycompany.sudoku.model.BacktrackingSudokuSolver;
import com.mycompany.sudoku.model.Helper;
import com.mycompany.sudoku.model.SudokuBoard;
import java.util.Arrays;
import org.junit.jupiter.api.Test;


public class SudokuSegmentTest {

    // private final int[][] Helper.validTestBoard = {
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



    final SudokuBoard validSudokuFieldMatrix = new SudokuBoard(new BacktrackingSudokuSolver());
    final SudokuBoard validSudokuFieldMatrix2 = new SudokuBoard(new BacktrackingSudokuSolver());

    {
        validSudokuFieldMatrix.setBoard(Helper.convert(Helper.validTestBoard));
        validSudokuFieldMatrix2.setBoard(Helper.convert(Helper.validTestBoard));

    }

    /**
     * Row.
     */
    final SudokuRow row = new SudokuRow(validSudokuFieldMatrix, 0);
    final int[] testRow = Helper.validTestBoard[0];


    /**
     * Column.
     */
    final SudokuColumn column = new SudokuColumn(validSudokuFieldMatrix, 0);
    final int[] testColumn = new int[9];

    {
        for (int i = 0; i < 9; i++) {
            testColumn[i] = Helper.validTestBoard[i][0];
        }

    }


    /**
     * Block.
     */
    final SudokuBlock block = new SudokuBlock(validSudokuFieldMatrix, 0, 0);
    final int[] testBlock = new int[9];

    {
        int iter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                testBlock[iter++] = Helper.validTestBoard[i][j];
            }
        }
    }

    @Test
    public void testBLockConstructor() {
        var fields = Helper.convert(Helper.validTestBoard)[0];
        SudokuBlock segment = new SudokuBlock(fields);
        for (int i = 0; i < segment.getFields().length; i++) {
            assertEquals(fields[i].getField(), segment.getFields()[i].getField());
        }
    }

    @Test
    public void testRowConstructor() {
        var fields = Helper.convert(Helper.validTestBoard)[5];
        SudokuRow segment = new SudokuRow(fields);
        for (int i = 0; i < segment.getFields().length; i++) {
            assertEquals(fields[i].getField(), segment.getFields()[i].getField());
        }
    }

    @Test
    public void testColumnConstructor() {
        var fields = Helper.convert(Helper.validTestBoard)[5];
        SudokuColumn segment = new SudokuColumn(fields);
        for (int i = 0; i < segment.getFields().length; i++) {
            assertEquals(fields[i].getField(), segment.getFields()[i].getField());
        }
    }


    @Test
    public void testSudokuRowConstructor() {

        assertEquals(
                Arrays.toString(testRow), 
                Arrays.toString(Helper.convert(row.getFields()))
        );
    }

    @Test
    public void testSudokuColumnConstructorTest() {

        assertEquals(
                Arrays.toString(testColumn), 
                Arrays.toString(Helper.convert(column.getFields()))
        );
    }

    @Test
    public void testSudokuBlockConstructor() {

        assertEquals(
                Arrays.toString(testBlock), 
                Arrays.toString(Helper.convert(block.getFields()))
        );
    }

    @Test
    public void testSudokuRowValidation() {
        assertTrue(row.verify());

        row.getFields()[0].setField(5);
        row.getFields()[1].setField(5);

        assertFalse(row.verify());
    }

    @Test
    public void testSudokuColumnValidation() {
        assertTrue(column.verify());

        column.getFields()[0].setField(5);
        column.getFields()[1].setField(5);

        assertFalse(column.verify());
    }

    @Test
    public void testSudokuBlockValidation() {
        assertTrue(block.verify());

        block.getFields()[0].setField(5);
        block.getFields()[1].setField(5);

        assertFalse(block.verify());
    }

    @Test
    public void testSudokuSegmentValidation1() {
        assertTrue(block.verify());

        block.getFields()[0].setField(53);
        block.getFields()[1].setField(53);

        assertFalse(block.verify());
    }


    @Test
    public void testSudokuSegmentValidation2() {
        assertTrue(block.verify());

        block.getFields()[0].setField(-53);
        block.getFields()[1].setField(-53);

        assertFalse(block.verify());
    }


    @Test
    void testHashCode() {
        SudokuColumn column1 = new SudokuColumn(Helper.convert(Helper.validTestBoard)[0]);
        SudokuField[] tmp = Helper.convert(Helper.validTestBoard)[0].clone();
        SudokuColumn column2 = new SudokuColumn(tmp);
        SudokuField[] tmp2 = Helper.convert(Helper.validTestBoard)[0].clone();
        tmp2[0].setField(10);
        SudokuColumn column3 = new SudokuColumn(tmp2);

        assertEquals(column1.hashCode(), column2.hashCode());
        assertNotEquals(column1.hashCode(), column3.hashCode());
    }

    @Test
    void testEquals() {
        SudokuColumn column1 = new SudokuColumn(Helper.convert(Helper.validTestBoard)[0]);
        SudokuField[] tmp = Helper.convert(Helper.validTestBoard)[0].clone();
        SudokuColumn column2 = new SudokuColumn(tmp);
        SudokuField[] tmp2 = Helper.convert(Helper.validTestBoard)[0].clone();
        tmp2[0].setField(10);

        assertEquals(column1, column2);
        assertEquals(column1.hashCode(), column2.hashCode()); // ensure hashcode <-> equals
        assertEquals(column1, column1);
        assertEquals(column1.hashCode(), column2.hashCode()); // ensure hashcode <-> equals

        SudokuColumn column3 = new SudokuColumn(tmp2);
        assertNotEquals(column1, column3);
        assertNotEquals(column1, null);
        assertNotEquals(column1, new Object());
    }

    @Test
    void testColumnToString() {
        String check = "SudokuColumn{4 6 1 8 3 9 5 2 7}";
        assertEquals(check, column.toString());
        assertNotEquals("qwerty", column.toString());
    }
    
    @Test
    void testRowToString() {
        
        String check = "SudokuRow{4 3 5 2 6 9 7 8 1}";
        assertEquals(check, row.toString());
        assertNotEquals("qwerty", row.toString());
    }
    
    @Test
    void testBlockToString() {
        
        String check = "SudokuBlock{4 3 5 6 8 2 1 9 7}";
        assertEquals(check, block.toString());
        assertNotEquals("qwerty", block.toString());
    }


    @Test
    public void testCloneRow() {
        var fields = Helper.convert(Helper.validTestBoard)[5];
        SudokuRow segment = new SudokuRow(fields);
        try {
            SudokuRow newSegment = segment.clone();
            assertNotSame(segment,newSegment);
            assertEquals(segment,newSegment);
            // weryfikacja pełnej rozłączności z obiektem
            SudokuField[] fields1 = segment.getFields();
            SudokuField[] fields2 = newSegment.getFields();
            for (int i = 0; i < fields1.length; i++) {
                assertNotSame(fields1[i], fields2[i]);
                assertEquals(fields1[i],fields2[i]);
            }
        } catch (CloneNotSupportedException e) {
            System.out.print("Clone not supported.");
        }

    }

    @Test
    public void testCloneColumn() {
        var fields = Helper.convert(Helper.validTestBoard)[5];
        SudokuSegment segment = new SudokuColumn(fields);
        try {
            SudokuSegment newSegment = segment.clone();
            assertNotSame(segment,newSegment);
            assertEquals(segment,newSegment);
            // weryfikacja pełnej rozłączności z obiektem
            SudokuField[] fields1 = segment.getFields();
            SudokuField[] fields2 = newSegment.getFields();
            for (int i = 0; i < fields1.length; i++) {
                assertNotSame(fields1[i], fields2[i]);
                assertEquals(fields1[i],fields2[i]);
            }
        } catch (CloneNotSupportedException e) {
            System.out.print("Clone not supported.");
        }
    }

    @Test
    public void testCloneBlock() {
        var fields = Helper.convert(Helper.validTestBoard)[5];
        SudokuBlock segment = new SudokuBlock(fields);
        try {
            SudokuBlock newSegment = segment.clone();
            assertNotSame(segment,newSegment);
            assertEquals(segment,newSegment);
            // weryfikacja pełnej rozłączności z obiektem
            SudokuField[] fields1 = segment.getFields();
            SudokuField[] fields2 = newSegment.getFields();
            for (int i = 0; i < fields1.length; i++) {
                assertNotSame(fields1[i], fields2[i]);
                assertEquals(fields1[i],fields2[i]);
            }
        } catch (CloneNotSupportedException e) {
            System.out.print("Clone not supported.");
        }
    }
}
