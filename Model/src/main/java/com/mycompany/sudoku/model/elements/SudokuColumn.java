package com.mycompany.sudoku.model.elements;

import com.mycompany.sudoku.model.SudokuBoard;

public class SudokuColumn extends SudokuSegment implements Cloneable {

    public SudokuColumn(SudokuField[] fields) {
        super(fields);
    }

    public SudokuColumn(SudokuBoard board, int x) {
        this.fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
            fields[i].setField(board.get(i,x));
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (SudokuField field : fields) {
            builder.append(field.toString()).append(" ");
        }

        return "SudokuColumn{"
                + builder.toString().trim()
                + '}';
    }

    @Override
    public SudokuColumn clone() throws CloneNotSupportedException {
        SudokuField[] current = this.getFields();
        SudokuField[] copied = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            copied[i] = new SudokuField(current[i].getField());
        }
        return new SudokuColumn(copied);
    }
}
