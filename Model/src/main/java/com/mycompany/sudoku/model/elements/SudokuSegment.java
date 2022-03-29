package com.mycompany.sudoku.model.elements;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public abstract class SudokuSegment implements Cloneable {
    protected SudokuField[] fields;

    public SudokuSegment(SudokuField[] fields) {
        this.fields = fields;
    }


    public SudokuSegment() {
    }

    public boolean verify() {
        /*
        Time complexity max: O(n)
        numbers 1 - 9 (indexes 0 - 8)
         */
        int[] numberFrequency = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (SudokuField field : fields) {
            int checkValue = field.getField();
            if (checkValue >= 1 && checkValue <= 9) {
                if (++numberFrequency[checkValue - 1] == 2) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    public SudokuField[] getFields() {
        return fields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (getClass() != o.getClass()) {
            return false;
        }

        SudokuSegment segment = (SudokuSegment) o;

        return new EqualsBuilder().append(fields, segment.fields).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(fields).toHashCode();
    }



    @Override
    public abstract SudokuSegment clone() throws CloneNotSupportedException;

}
