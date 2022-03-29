package com.mycompany.sudoku.model.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SudokuFieldTest {
    private final SudokuField field = new SudokuField(0);

    @Test
    void testEquals() {
        SudokuField newField = new SudokuField(0);
        assertEquals(field,newField);
        assertEquals(field.hashCode(), newField.hashCode()); // ensure hashCode <-> equals
        assertEquals(field,field);
        assertEquals(field.hashCode(), field.hashCode()); // ensure hashCode <-> equals
        assertNotEquals(field,null);
        assertNotEquals(field,new Object());
    }

    @Test
    void testHashCode() {
        SudokuField newField = new SudokuField(0);
        SudokuField newField2 = new SudokuField(1);
        assertEquals(field.hashCode(),newField.hashCode());
        assertNotEquals(field.hashCode(),newField2.hashCode());
    }

    @Test
    void getField() {
        assertEquals(0,field.getField());
    }

    @Test
    void setField() {
        field.setField(5);
        assertEquals(5,field.getField());
    }

    @Test
    void toStringTest() {
        assertEquals("0", field.toString());
    }

    @Test
    public void compareToTestEquals() {
        SudokuField newField = new SudokuField(0);
        SudokuField newField2 = new SudokuField(0);
        assertEquals(0, newField.compareTo(newField2));
        assertEquals(0, newField2.compareTo(newField));
    }

    @Test
    public void compareToTestLess() {
        SudokuField newField = new SudokuField(1);
        SudokuField newField2 = new SudokuField(2);
        assertEquals(-1, newField.compareTo(newField2));
    }

    @Test
    public void compareToTestGreater() {
        SudokuField newField = new SudokuField(2);
        SudokuField newField2 = new SudokuField(1);
        assertEquals(1, newField.compareTo(newField2));
    }

    @Test
    public void compareToNullPointerExceptionTest() {
        SudokuField newField = new SudokuField(2);
        Exception exception = assertThrows(NullPointerException.class,
                () -> {
            int x = newField.compareTo(null); });
        assertEquals("Argument is null",exception.getMessage());

    }

    @Test
    public void cloneSudokuField() {
        SudokuField newField = new SudokuField(0);
        try {
            SudokuField newField2 = newField.clone();
            assertNotSame(newField,newField2);
            assertEquals(newField,newField2);
        } catch (CloneNotSupportedException e) {
            System.out.print("Clone not supported.");
        }
    }
}