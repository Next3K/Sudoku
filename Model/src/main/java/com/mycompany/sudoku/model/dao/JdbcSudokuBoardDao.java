package com.mycompany.sudoku.model.dao;

import com.mycompany.sudoku.model.BacktrackingSudokuSolver;
import com.mycompany.sudoku.model.SudokuBoard;
import com.mycompany.sudoku.model.exceptions.AppException;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcCantCreateTables;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcConnectionException;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcDriverNotFound;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcReadException;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcWriteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *  Data Access Object (DAO) - txt files.
 *  Data is saved and retrieved from database.
 */
public class JdbcSudokuBoardDao implements Dao<SudokuBoard> {

    Logger logger = LoggerFactory.getLogger(JdbcSudokuBoardDao.class);
    public static final String DRIVER = "org.sqlite.JDBC";
    private final String dbUrl;


    public JdbcSudokuBoardDao(String baseName) throws JdbcDriverNotFound, JdbcCantCreateTables {
        File directory = new File("." + File.separator + "databases" + File.separator);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        dbUrl = "jdbc:sqlite:databases" + File.separator + baseName;

        try {
            Class.forName(JdbcSudokuBoardDao.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new JdbcDriverNotFound(JdbcDriverNotFound.DRIVER_NOT_FOUND, e);
        }

        try {
            createTables();
        } catch (JdbcConnectionException e) {
            throw new JdbcCantCreateTables(JdbcCantCreateTables.CANT_CREATE_TABLES, e);
        }

    }


    @Override
    public void write(SudokuBoard obj) throws AppException {

        int boardID = 0;
        if (this.isNameInDatabase(obj.getName())) {
            throw new JdbcWriteException(JdbcWriteException.NAME_USED);
        }

        try (Connection conn = DriverManager.getConnection(dbUrl);
        Connection conn2 = DriverManager.getConnection(dbUrl)) {
            conn.setAutoCommit(false);
            conn2.setAutoCommit(false);
            /**
             * Wpisanie danych do tabeli boards.
             */
            try (PreparedStatement prepStatement = conn.prepareStatement(
                         "INSERT INTO boards VALUES (NULL, ?)"
                 );
            ) {
                prepStatement.setString(1, obj.getName());
                prepStatement.execute();
            }

            /**
             * Znalezienie największego ID sudokuBoard w tabeli boards.
             */
            try (Statement statement = conn.createStatement();
                 ResultSet getID = statement.executeQuery("SELECT max(boardid) from boards")) {
                boardID = getID.getInt(1);
            }

            /**
             * Dodanie wartości pól sudoku do tabeli fields.
             */
            try (Statement statement = conn.createStatement();
                 PreparedStatement preparedStatement = conn.prepareStatement(
                         "INSERT INTO fields VALUES (NULL, ?, ?, ?)")) {

                logger.info("Adding sudoku: " + obj.getName() + " to database.");
                for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {
                        preparedStatement.setInt(1, boardID);
                        preparedStatement.setInt(2, Integer.parseInt(
                                Integer.toString(x)
                                        + Integer.toString(y)));
                        preparedStatement.setInt(3, obj.get(x, y));
                        preparedStatement.execute();
                        logger.info("write field: position"
                                + x + y
                                + " value: " + obj.get(x, y));
                    }
                }
            }

            conn.commit();
            conn2.commit();
        } catch (SQLException e) {
            throw new JdbcWriteException(JdbcWriteException.WRITE_ERROR,e);
        }
    }

    @Override
    public void close() throws Exception {

    }

    private void createTables() throws JdbcConnectionException {

        String createBoards = "CREATE TABLE IF NOT EXISTS boards "
                + "(boardid INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name varchar(255))";
        String createFields = "CREATE TABLE IF NOT EXISTS fields "
                + "(fieldid INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "boardid int, position int, value int)";

        /**
         * Utworzenie tabel fields
         */
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.execute(createBoards);
                statement.execute(createFields);
            }
            connection.commit();
        } catch (SQLException e) {
            throw new JdbcConnectionException(JdbcConnectionException.CONNECTION_ERROR, e);
        }
    }

    public SudokuBoard readBoardByName(String name) throws JdbcReadException {

        if (!this.isNameInDatabase(name)) {
            throw new JdbcReadException(JdbcReadException.ID_ERROR);
        }

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            connection.setAutoCommit(false);

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(
                         "SELECT * FROM boards"
                 )) {
                while (resultSet.next()) {
                    if (resultSet.getString("name").equals(name)) {
                        int boardId = resultSet.getInt("boardid");
                        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
                        board.setName(name);
                        logger.info("Reading board with ID: " + String.valueOf(boardId)
                                + " name: " + name);

                        try (ResultSet fieldSet = statement.executeQuery(
                                "SELECT * FROM fields"
                        )) {
                            while (fieldSet.next()) {
                                if (fieldSet.getInt("boardid") == boardId) {
                                    int value = fieldSet.getInt("value");
                                    int position = fieldSet.getInt("position");

                                    int x = calculatePosition(position)[0];
                                    int y = calculatePosition(position)[1];
                                    board.set(x, y, value);
                                }
                            }
                        } catch (SQLException e) {
                            throw new JdbcReadException(JdbcReadException.READ_ERROR, e);

                        }
                        return board;
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            throw new JdbcReadException(JdbcReadException.READ_TABLE_ERROR, e);
        }

        return null;
    }

    private int[] calculatePosition(int position) {
        int[] array;
        if (position < 10) {
            array = new int[]{0, position};
            return array;
        }
        array = new int[]{position / 10, position % 10};
        return array;
    }

    @Override
    public SudokuBoard read() throws AppException {

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(
                         "SELECT * FROM boards"
                 )) {

                int boardID = resultSet.getInt("boardid");
                SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());
                board.setName(resultSet.getString("name"));
                logger.info("Reading board with ID: " + String.valueOf(boardID)
                        + " name: " + board.getName());

                try (ResultSet fieldSet = statement.executeQuery(
                        "SELECT * FROM fields")) {

                    while (fieldSet.next()) {
                        if (fieldSet.getInt("boardid") == boardID) {
                            int value = fieldSet.getInt("value");
                            int extractedPosition = fieldSet.getInt("position");
                            int x = calculatePosition(extractedPosition)[0];
                            int y = calculatePosition(extractedPosition)[1];
                            board.set(x, y, value);
                            logger.info("Setting field: " + x + " " + y + " value: " + value);
                        }
                    }
                } catch (SQLException e) {
                    throw new JdbcReadException(JdbcReadException.READ_TABLE_ERROR);
                }
                connection.commit();
                return board;
            }
        } catch (SQLException e) {
            throw new JdbcReadException(JdbcReadException.READ_ERROR,e);
        }

    }

    public boolean isNameInDatabase(String name) {

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(
                         "SELECT * FROM boards"
                 )) {
                while (resultSet.next()) {
                    String tmpName = resultSet.getString("name");
                    if (tmpName.equals(name)) {
                        return true;
                    }
                }
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }



}
