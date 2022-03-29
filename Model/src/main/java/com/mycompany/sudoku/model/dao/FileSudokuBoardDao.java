package com.mycompany.sudoku.model.dao;

import com.mycompany.sudoku.model.BacktrackingSudokuSolver;
import com.mycompany.sudoku.model.SudokuBoard;
import com.mycompany.sudoku.model.elements.SudokuField;
import com.mycompany.sudoku.model.exceptions.DaoException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Data Access Object (DAO) - txt files.
 *  Not used because JdbcSudokuBoardDao is implemented
 *  and data is saved and retrieved from database.
 */
public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private final File file;
    private static final Logger logger = LoggerFactory.getLogger(FileSudokuBoardDao.class);

    /**
     * Instantiates a new File sudoku board dao and creates ./files/ directory
     *
     * @param filename name of the file
     */
    public FileSudokuBoardDao(String filename) {
        File directory = new File("." + File.separator + "files" + File.separator);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        this.file = new File(directory, filename);
    }

    /**
     * Reads file in ./files/ directory
     *
     * @return SudokuBoard
     */
    @Override
    public SudokuBoard read() throws DaoException {
        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            SudokuField[][] boardMatrix = new SudokuField[9][9];
            String[] line;

            for (int i = 0; i < 9; i++) {
                line = reader.readLine().trim().split(" ");
                for (int j = 0; j < 9; j++) {
                    boardMatrix[i][j] = new SudokuField(Integer.parseInt(line[j]));
                }
            }
            board.setBoard(boardMatrix);
        } catch (IOException e) {
            var exception = new DaoException(DaoException.FAILED_TO_READ_FILE,e);
            logger.error(exception.getLocalizedMessage() + " Filename: " + file.getPath());
            throw exception;
        }
        return board;
    }


    /**
     * Writes SudokuBoard into ./files/ directory
     *
     * @param board SudokuBoard to write
     */
    @Override
    public void write(SudokuBoard board) throws DaoException {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                var exception = new DaoException(DaoException.FAILED_TO_WRITE_FILE,e);
                logger.error(exception.getLocalizedMessage() + " Filename: " + file.getPath());
                throw exception;
            }
        }

        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(board.toString());

        } catch (IOException e) {
            var exception = new DaoException(DaoException.FAILED_TO_WRITE_FILE,e);
            logger.error(exception.getLocalizedMessage() + " Filename: " + file.getPath());
            throw exception;
        }
    }

    @Override
    public void close() throws DaoException {
    }


    public File getFile() {
        return file;
    }

}
