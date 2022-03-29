package com.mycompany.sudoku.model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mycompany.sudoku.model.BacktrackingSudokuSolver;
import com.mycompany.sudoku.model.Helper;
import com.mycompany.sudoku.model.SudokuBoard;
import com.mycompany.sudoku.model.exceptions.AppException;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcCantCreateTables;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcDriverNotFound;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcReadException;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcWriteException;
import org.junit.After;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcSudokuBoardDaoTest {

    private SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private final ResourceBundle bundle = ResourceBundle.getBundle("bundles.JdbcExceptions");

    /**
     * Clean up after tests.
     * Remove files: ./databases/test.db .
     */
    @After
    public void deleteAllFiles() {
        Helper.deleteDirectory(
                new File(
                        "." + File.separator + "databases" + File.separator
                )
        );
    }

    /**
     * Overall functionality test.
     */
    @Test
    public void start() {
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("test.db")) {
            SudokuBoard b = new SudokuBoard(new BacktrackingSudokuSolver());
            b.setName("test1");
            b.solveGame();
            dao.write(b);
            SudokuBoard tmp = dao.readBoardByName("test1");
        } catch (JdbcDriverNotFound | JdbcWriteException | JdbcCantCreateTables e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor test.
     */
    @Test
    public void testConstructor() {
        File directory = new File("." + File.separator + "databases" + File.separator);
        File newDatabase = new File("." + File.separator
                + "databases" + File.separator + "test.db");
        Helper.deleteDirectory(directory);
        assertFalse(directory.exists());
        assertFalse(newDatabase.exists());
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("test.db")) {
           assertTrue(directory.exists());
           assertTrue(newDatabase.exists());
        } catch (JdbcDriverNotFound | JdbcWriteException | JdbcCantCreateTables e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test writing data to database.
     */
    @Test
    public void testWriteBoardToDatabase() {
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("test.db")) {
            SudokuBoard b = new SudokuBoard(new BacktrackingSudokuSolver());
            b.setName("test2");
            b.solveGame();
            dao.write(b);
            assertEquals(b,dao.readBoardByName("test2"));
            assertNotSame(b,dao.readBoardByName("test2"));
        } catch (AppException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test reading sudoku from database by name.
     */
    @Test
    public void testReadBoardByNameFromDatabase() {
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("test.db")) {
            SudokuBoard b = new SudokuBoard(new BacktrackingSudokuSolver());
            b.setName("test3");
            b.solveGame();
            dao.write(b);
            assertEquals(b,dao.readBoardByName("test3"));
            assertNotSame(b,dao.readBoardByName("test3"));
        } catch (AppException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test whether reading board with incorrect name throws Exception.
     */
    @Test
    public void testReadBoardByNameBadNameException() {
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("test.db")) {
            SudokuBoard b1 = new SudokuBoard(new BacktrackingSudokuSolver());
            b1.setName("test4");
            b1.solveGame();
            dao.write(b1);
            String localMessage = assertThrows(JdbcReadException.class, () -> {
                dao.readBoardByName("bad_name");
            }).getLocalizedMessage();
            assertEquals(bundle.getString(JdbcReadException.ID_ERROR),localMessage);
        } catch (AppException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test whether writing board with duplicate name throws Exception.
     */
    @Test
    public void testWriteBoardNameUsedException() {
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("test.db")) {
            SudokuBoard b1 = new SudokuBoard(new BacktrackingSudokuSolver());
            SudokuBoard b2 = new SudokuBoard(new BacktrackingSudokuSolver());
            b1.setName("test5");
            b2.setName("test5");
            b1.solveGame();
            b2.solveGame();
            dao.write(b1);
            String localMessage = assertThrows(JdbcWriteException.class, () -> {
                dao.write(b2);
            }).getLocalizedMessage();
            assertEquals(bundle.getString(JdbcWriteException.NAME_USED),localMessage);
        } catch (AppException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test of method read().
     */
    @Test
    public void readTest() {
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("test2.db")) {
            SudokuBoard b1 = new SudokuBoard(new BacktrackingSudokuSolver());
            b1.setName("test");
            b1.solveGame();
            dao.write(b1);
            assertEquals(b1,dao.read());
            assertNotSame(b1,dao.read());
        } catch (AppException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test of method isNameInDatabase().
     */
    @Test
    public void isNameInDatabasePositiveTest() {
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("test3.db")) {
            SudokuBoard b1 = new SudokuBoard(new BacktrackingSudokuSolver());
            SudokuBoard b2 = new SudokuBoard(new BacktrackingSudokuSolver());
            SudokuBoard b3 = new SudokuBoard(new BacktrackingSudokuSolver());
            b1.setName("exists");
            b2.setName("None");
            b3.setName("Null");
            dao.write(b1);
            dao.write(b2);
            dao.write(b3);
            assertTrue(dao.isNameInDatabase("exists"));
            assertTrue(dao.isNameInDatabase("None"));
            assertTrue(dao.isNameInDatabase("Null"));
        } catch (AppException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test of method isNameInDatabase().
     */
    @Test
    public void isNameInDatabaseNegativeTest() {
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("test4.db")) {
            SudokuBoard b1 = new SudokuBoard(new BacktrackingSudokuSolver());
            b1.setName("exists");
            dao.write(b1);
            assertFalse(dao.isNameInDatabase("None"));
            assertFalse(dao.isNameInDatabase("Null"));
        } catch (AppException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test of Connection exception.
     */
    @Test
    public void writeExceptionsTest() {
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("test5.db")) {
            SudokuBoard b1 = new SudokuBoard(new BacktrackingSudokuSolver());
            b1.setName("exists");
            /**
             *  deleting database to break the connection.
             */
            File file = new File("." + File.separator + "databases"
                    + File.separator + "test5.db");
            file.delete();
            assertThrows(JdbcWriteException.class, () -> {
                dao.write(b1);
            });
        } catch (AppException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test exception for method read when connection is broken.
     */
    @Test
    public void readExceptionsTest() {
        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("test6.db")) {
            SudokuBoard b1 = new SudokuBoard(new BacktrackingSudokuSolver());
            b1.setName("exists");
            dao.write(b1);
            /**
             *  deleting database to break the connection.
             */
            File file = new File("." + File.separator + "databases"
                    + File.separator + "test6.db");
            file.delete();
            assertThrows(JdbcReadException.class, () -> {
                dao.read();
            });
            assertThrows(JdbcReadException.class, () -> {
                dao.readBoardByName(b1.getName());
            });
        } catch (AppException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
