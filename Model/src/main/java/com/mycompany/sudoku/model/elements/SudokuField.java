package com.mycompany.sudoku.model.elements;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SudokuField implements Comparable<SudokuField>, Cloneable {


    private int value = 0;

    public SudokuField(int value) {
        this.value = value;
    }

    public SudokuField() {

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

        SudokuField field = (SudokuField) o;

        return new EqualsBuilder().append(value, field.value).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value).toHashCode();
    }

    public int getField() {
        return this.value;
    }

    public void setField(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public int compareTo(SudokuField o) {
        if (o == null) {
            throw new NullPointerException("Argument is null");
        }
        return this.value - o.value;
    }

    @Override
    protected SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField) super.clone();
    }
}



