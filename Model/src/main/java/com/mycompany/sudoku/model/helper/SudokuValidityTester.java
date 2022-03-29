package com.mycompany.sudoku.model.helper;

import com.mycompany.sudoku.model.SudokuBoard;
import com.mycompany.sudoku.model.elements.SudokuField;
import com.mycompany.sudoku.model.elements.SudokuSegment;
import java.util.Arrays;
import java.util.List;

public class SudokuValidityTester {


    public boolean isSudokuValid(SudokuBoard board) {

        Integer[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<Integer> allowedValues = Arrays.asList(values);
        boolean check = true;


        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!allowedValues.contains(board.get(i, j))) {
                    check = false;
                }
            }
        }


        for (int possibleVal = 1; possibleVal < 10; possibleVal++) {

            if (!checkSegments(board, 'b', possibleVal)) {
                check = false;
            }
            if (!checkSegments(board, 'r', possibleVal)) {
                check = false;
            }
            if (!checkSegments(board, 'c', possibleVal)) {
                check = false;
            }

        }
        return check;
    }

    public boolean checkSegments(SudokuBoard board, Character segmentIdentifier, int value) {
        int count;
        for (int i = 0; i < 9; i++) {
            count = 0;
            SudokuSegment segment = switch (segmentIdentifier) {
                case 'b' -> board.getBlock(i / 3, i % 3);
                case 'r' -> board.getRow(i);
                case 'c' -> board.getColumn(i);
                default -> throw new IllegalArgumentException("segmentIdentifier must be one of "
                        + "['b', 'r', 'c'] and you passed " + segmentIdentifier);
            };
            for (SudokuField field : segment.getFields()) {
                if (field.getField() == value) {
                    count++;
                }
            }
            if (count != 1) {
                return false;
            }
        }
        return true;
    }

}
