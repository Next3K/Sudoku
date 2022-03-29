package com.mycompany.sudoku.model.elements;

import com.mycompany.sudoku.model.SudokuBoard;

public class SudokuBlock extends SudokuSegment implements Cloneable {


    public SudokuBlock(SudokuField[] fields) {
        super(fields);
    }

    public SudokuBlock(SudokuBoard board, int x, int y) {
        this.fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
        }
        int iter = 0;
        for (int i = x * 3; i < (x + 1) * 3; i++) {
            for (int j = y * 3; j < (y + 1) * 3; j++) {
                fields[iter++].setField(board.get(i,j));
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (SudokuField field : fields) {
            builder.append(field.toString()).append(" ");
        }

        return "SudokuBlock{"
                + builder.toString().trim()
                + '}';
    }

    @Override
    public SudokuBlock clone() throws CloneNotSupportedException {
        SudokuField[] current = this.getFields();
        SudokuField[] copied = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            copied[i] = new SudokuField(current[i].getField());
        }
        return new SudokuBlock(copied);
    }
}
