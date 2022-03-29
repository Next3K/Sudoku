package com.mycompany.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BacktrackingSudokuSolver implements SudokuSolver {

    @Override
    public SudokuSolver newInstance() {
        return new BacktrackingSudokuSolver();
    }

    @Override
    public void solve(SudokuBoard board) {
        SudokuBoard tmpBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        // clear board before filling
        for (int i = 0; i < 9; i++) {
            for (int k = 0; k < 9; k++) {
                tmpBoard.set(i,k,0);
            }
        }

        //lista list dostepnych wartosci dla poszczególnych komórek
        ArrayList<ArrayList<Integer>> available = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 81; i++) {
            Integer[] tempArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            ArrayList<Integer> tempList = new ArrayList<>();
            Collections.addAll(tempList, tempArray);
            available.add(tempList);
        }

        for (int i = 0; i < 81; ) {
            ArrayList<Integer> cell = available.get(i);
            int row = i / 9;
            int column = i % 9;

            if (cell.isEmpty()) {
                Integer[] tempArray = {1, 2, 3, 4, 5, 6, 7, 8, 9};
                Collections.addAll(cell, tempArray);
                tmpBoard.set(row,column,0);
                i--;
                continue;
            }

            //losujemy wartość zlisty dostępnych dla danej komórki
            int number = cell.get(rand.nextInt(cell.size()));
            cell.removeIf(value -> value.equals(number));

            int regionRow = row / 3;
            int regionColumn = column / 3;

            // sprawdzamy czy wartość nie występuje już w danej kolumnie, wierszu lub segmencie 3x3
            boolean numberPresent = getRow(row,tmpBoard).contains(number)
                    || getColumn(column,tmpBoard).contains(number)
                    || getRegion(regionRow,regionColumn,tmpBoard).contains(number);

            if (numberPresent) {
                continue;
            }
            tmpBoard.set(row,column,number);
            i++;
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board.set(i,j,tmpBoard.get(i,j));
            }
        }
    }

    private ArrayList<Integer> getRow(int row, SudokuBoard board) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            result.add(board.get(row,i));
        }
        return result;
    }

    private ArrayList<Integer> getRegion(int regionRow, int regionColumn,SudokuBoard board) {
        int startRow = regionRow * 3;
        int startColumn = regionColumn * 3;
        ArrayList<Integer> result = new ArrayList<>();

        for (int row = startRow; row < startRow + 3; row++) {
            for (int column = startColumn; column < startColumn + 3; column++) {
                result.add(board.get(row,column));
            }
        }
        return result;
    }


    private ArrayList<Integer> getColumn(int column, SudokuBoard board) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            result.add(board.get(i,column));
        }
        return result;
    }
}
