package com.mycompany.sudoku.model.elements;

import com.mycompany.sudoku.model.SudokuBoard;

public class SudokuRow extends SudokuSegment implements Cloneable {


    public SudokuRow(SudokuField[] fields) {
        super(fields);
    }
    
    public SudokuRow(SudokuBoard board, int x) {
        this.fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
            fields[i].setField(board.get(x,i));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (SudokuField field : fields) {
            builder.append(field.toString()).append(" ");
        }

        return "SudokuRow{"
                + builder.toString().trim()
                + '}';
    }

    @Override
    public SudokuRow clone() throws CloneNotSupportedException {
        SudokuField[] current = this.getFields();
        SudokuField[] copied = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            copied[i] = new SudokuField(current[i].getField());
        }
        return new SudokuRow(copied);
    }
}
