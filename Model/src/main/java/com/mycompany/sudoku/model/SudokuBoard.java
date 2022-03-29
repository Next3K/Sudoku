package com.mycompany.sudoku.model;


import com.mycompany.sudoku.model.elements.SudokuBlock;
import com.mycompany.sudoku.model.elements.SudokuColumn;
import com.mycompany.sudoku.model.elements.SudokuField;
import com.mycompany.sudoku.model.elements.SudokuRow;
import com.mycompany.sudoku.model.helper.SudokuValidityTester;

/**
 * Main Class - SudokuBoard.
 */
public class SudokuBoard implements Cloneable {
    private final SudokuField[][] board = new SudokuField[9][9];
    private SudokuSolver sudokuSolver;
    
    private String name = "Just some random name";

    /**
     * Main constructor.
     *
     * @param solver solver sudoku
     */
    public SudokuBoard(SudokuSolver solver) {
        this.sudokuSolver = solver;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField(0);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (SudokuField[] row : board) {
            StringBuilder line = new StringBuilder();
            for (SudokuField field : row) {
                line.append(field.toString()).append(" ");

            }
            builder.append(line.toString().trim() + "\n");
        }
        builder.setLength(builder.length() - 1);
        return builder.toString();
    }

    /**
     * Returns true if values inside the board are the same.
     *
     * @param o Object to compare with.
     * @return boolean
     */
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

        SudokuBoard that = (SudokuBoard) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(board, that.board).isEquals();
    }

    /**
     * Generates a hashcode from object.
     * We ignore the type of SudokuSolver.
     *
     * @return integer HashCode
     */
    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder
                .HashCodeBuilder(17, 37).append(board).toHashCode();
    }

    /**
     * Copy constructor.
     *
     * @param tmpBoard 2D array of SudokuFields
     */
    public SudokuBoard(SudokuBoard tmpBoard) {
        this.sudokuSolver = tmpBoard.getSudokuSolver().newInstance();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField(tmpBoard.get(i, j));
            }
        }
    }

    /**
     * Solves sudoku.
     */
    public void solveGame() {
        sudokuSolver.solve(this);
    }

    /**
     * SudokuSolver setter.
     *
     * @param solver new instance of SudokuSolver
     */
    void setSudokuSolver(SudokuSolver solver) {
        this.sudokuSolver = solver.newInstance();
    }

    /**
     * SudokuSolver getter.
     *
     * @return reference to SudokuSolver
     */
    SudokuSolver getSudokuSolver() {
        return sudokuSolver.newInstance();
    }

    /**
     * board getter.
     *
     * @return 2D array of SudokuField
     */
    public SudokuField[][] getBoard() {
        SudokuField[][] newBoard = new SudokuField[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                newBoard[i][j] = new SudokuField(this.get(i, j));
            }
        }
        return newBoard;
    }

    /**
     * board setter.
     *
     * @param fields 2D array of SudokuField
     */
    public void setBoard(SudokuField[][] fields) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.set(i, j, fields[i][j].getField());
            }
        }
    }

    /**
     * Checks whether board is valid/correct.
     *
     * @return boolean
     */
    public boolean checkBoard() {
        SudokuValidityTester tester = new SudokuValidityTester();
        return tester.isSudokuValid(this);
    }

    /**
     * Cell value getter.
     *
     * @param row    cells' row number
     * @param column cells' column number
     * @return cells' integer value
     */
    public int get(int row, int column) {
        return this.board[row][column].getField();
    }

    /**
     * Cell value setter.
     *
     * @param row    cells' row number
     * @param column cells' column number
     * @param value  integer value
     */
    public void set(int row, int column, int value) {
        this.board[row][column].setField(value);
    }

    /**
     * Sudoku row getter.
     *
     * @param row row number
     * @return SudokuRow
     */
    public SudokuRow getRow(int row) {
        return new SudokuRow(this, row);
    }

    /**
     * Sudoku column getter.
     *
     * @param column column number
     * @return SudokuColumn
     */
    public SudokuColumn getColumn(int column) {
        return new SudokuColumn(this, column);
    }

    /**
     * Sudoku block getter (3 by 3).
     *
     * @param blockX integer 1 - 3
     * @param blockY integer 1 - 3
     * @return SudokuBlock
     */
    public SudokuBlock getBlock(int blockX, int blockY) {
        return new SudokuBlock(this, blockX, blockY);
    }

    @Override
    public SudokuBoard clone() throws CloneNotSupportedException {
        SudokuBoard copy = new SudokuBoard(this.getSudokuSolver());
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                copy.set(i,j,this.get(i,j));
            }
        }
        return copy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}