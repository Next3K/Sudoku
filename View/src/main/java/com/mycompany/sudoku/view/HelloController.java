package com.mycompany.sudoku.view;


import com.mycompany.sudoku.model.BacktrackingSudokuSolver;
import com.mycompany.sudoku.model.SudokuBoard;
import com.mycompany.sudoku.model.dao.JdbcSudokuBoardDao;
import com.mycompany.sudoku.model.dao.SudokuBoardDaoFactory;
import com.mycompany.sudoku.model.exceptions.AppException;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcCantCreateTables;
import com.mycompany.sudoku.model.exceptions.jdbc.JdbcDriverNotFound;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Optional;
import java.util.Locale;
import java.util.function.UnaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloController implements Initializable {

    private DifficultyLevel difficulty = DifficultyLevel.EASY;
    private SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
    private final IntegerProperty[][] propertyBoard = new SimpleIntegerProperty[9][9];
    private ResourceBundle bundle = ResourceBundle.getBundle("bundles.language");
    private final SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();
    private final String[] difficulties = {
            bundle.getString("difficulty.easy"),
            bundle.getString("difficulty.medium"),
            bundle.getString("difficulty.hard")};
    private final String[] languages = {
            bundle.getString("lang.polish"),
            bundle.getString("lang.english")};

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @FXML
    private Button startButton;

    @FXML
    private Button loadSavedBoard;

    @FXML
    private Button saveToFile;

    @FXML
    private Button showAuthors;

    @FXML
    private ChoiceBox<String> pickDifficulty;

    @FXML
    private ChoiceBox<String> pickLanguage;

    @FXML
    private GridPane myGrid81;

    /**
     * filtrowanie inputu.
     */
    private final UnaryOperator<TextFormatter.Change> integerFilter = textChange -> {
        String newText = textChange.getControlNewText();
        String oldText = textChange.getControlText();
        if (newText.matches("") || newText.matches("[1-9]")) {
            logger.info("New value: " + newText + " Old value: " + oldText);
            return textChange;
        } else {
            return null;
        }
    };


    /**
     * inicjalizacja plansszy i elementow.
     *
     * @param url            url data.
     * @param resourceBundle resources language.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        pickDifficulty.getItems().addAll(difficulties);
        pickDifficulty.setOnAction(this::setDifficulty);
        pickLanguage.getItems().addAll(languages);
        pickLanguage.setOnAction(this::setLanguage);
        startButton.setOnAction(this::fillBoard);
        saveToFile.setOnAction(this::saveToFile);
        saveToFile.setText(bundle.getString("save.board.label"));
        loadSavedBoard.setOnAction(this::importBoardFromDatabase);
        loadSavedBoard.setText(bundle.getString("load.board.label"));
        showAuthors.setText(bundle.getString("show.authors"));
        showAuthors.setOnAction(this::showAuthors);
    }

    /**
     * wypelnienie planszy.
     *
     * @param actionEvent button press.
     * @return ection event
     */
    private ActionEvent fillBoard(ActionEvent actionEvent) {
        prepareBoard();
        return actionEvent; // nie usuwać bo pmd się czepia
    }

    /**
     * przycisk pokaż autorów.
     *
     * @param actionEvent button press.
     * @return action event
     */
    private ActionEvent showAuthors(ActionEvent actionEvent) {
        ResourceBundle authors = ResourceBundle.getBundle("com.mycompany.sudoku.view.Authors");
        String author1 = (String) authors.getObject("1");
        String author2 = (String) authors.getObject("2");
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(author1 + " & " + author2);
        a.setTitle(bundle.getString("show.authors"));
        a.setHeaderText("");
        a.show();
        logger.info("Showing: " + bundle.getString("show.authors"));
        return actionEvent; // nie usuwać bo pmd się czepia
    }

    /**
     * Przycisk ustawiania trudnosci.
     *
     * @param event button press.
     */
    public void setDifficulty(ActionEvent event) {
        String choice = pickDifficulty.getValue();
        switch (choice) {
            case "easy" -> this.difficulty = DifficultyLevel.EASY;
            case "medium" -> this.difficulty = DifficultyLevel.MEDIUM;
            case "hard" -> this.difficulty = DifficultyLevel.HARD;
            default -> this.difficulty = DifficultyLevel.EASY;
        }
        logger.info("New difficulty: " + pickDifficulty.getValue());
    }


    private void prepareBoard() {
        sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());
        sudokuBoard.solveGame();
        removeFields(difficulty.numberOfFieldsToRemove());
    }

    /**
     * usuwanie pol w zaleznosci od stopnia trudnosci.
     *
     * @param fieldsToRemove number of fields to remove.
     */
    public void removeFields(int fieldsToRemove) {

        ArrayList<Integer> numbers = new ArrayList<>();
        Random random = new Random();
        while (numbers.size() < fieldsToRemove) {
            int proposition = random.nextInt(81);
            int row = proposition / 9;
            int column = proposition % 9;
            if (!numbers.contains(proposition)) {
                numbers.add(proposition);
            }
            sudokuBoard.set(row, column, 0);
        }

        insertTextValues();


    }

    /**
     * binding dwukierunkowy.
     */
    private void insertTextValues() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                textField.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                textField.setAlignment(Pos.CENTER);
                textField.setFont(Font.font(15));
                StringConverter conv = new IntegerStringConverter();
                propertyBoard[i][j] = new SimpleIntegerProperty();
                Bindings.bindBidirectional(textField.textProperty(), propertyBoard[i][j], conv);
                propertyBoard[i][j].set(sudokuBoard.get(i, j));
                int value = sudokuBoard.get(i, j);
                textField.setText(value == 0 ? "" : String.valueOf(value));
                textField.setTextFormatter(new TextFormatter<Integer>(integerFilter));
                //textField.setDisable(true);
                myGrid81.add(textField, i, j);
            }
        }
    }

    /**
     * enum representing difficulty of Sudoku puzzle.
     */
    public enum DifficultyLevel {
        EASY, MEDIUM, HARD;

        private int numberOfFieldsToRemove() {
            return switch (this) {
                case EASY -> 10;
                case MEDIUM -> 20;
                case HARD -> 30;
                default -> 5;
            };
        }
    }


    //    /**
    //     * Import board from file ./View/files/sudokuBoard.txt .
    //     *
    //     * @param event javaFx action event.
    //     */
    //    private ActionEvent importBoardFromFile(ActionEvent event) {
    //        // Creating a File chooser
    //        FileChooser fileChooser = new FileChooser();
    //        fileChooser.setTitle(bundle.getString("load.from.file.window.title"));
    //        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Txt Files",
    //                "*.*"));
    //        File initial = new File("." + File.separator + "files" + File.separator);
    //        if (initial.exists()) {
    //            fileChooser.setInitialDirectory(initial);
    //        }
    //        Node node = (Node) event.getSource();
    //        Stage currentStage = (Stage) node.getScene().getWindow();
    //        File openFile = fileChooser.showOpenDialog(currentStage);
    //
    //        try (FileSudokuBoardDao sudokuBoardDao =
    //                     (FileSudokuBoardDao) factory.createFileDao(openFile.getName())) {
    //            sudokuBoard = sudokuBoardDao.read();
    //            insertTextValues();
    //            for (int i = 0; i < 9; i++) {
    //                for (int j = 0; j < 9; j++) {
    //                    propertyBoard[i][j].set(sudokuBoard.get(i, j));
    //                }
    //            }
    //
    //        } catch (DaoException e) {
    //            logger.error(e.getLocalizedMessage());
    //            e.printStackTrace();
    //        }
    //        return event; // nie usuwać bo pmd się czepia
    //    }

    public void setLanguage(ActionEvent event) {
        String choice = pickLanguage.getValue();
        if (bundle.getString("lang.polish").equals(choice)) {
            if (Locale.getDefault().getCountry().equals("pl")) {
                return;
            }
            Locale polish = new Locale.Builder().setLanguage("pl").build();
            bundle = ResourceBundle.getBundle("bundles.language", polish);
            Locale.setDefault(polish);
        } else if (bundle.getString("lang.english").equals(choice)) {
            if (Locale.getDefault().getCountry().equals("en")) {
                return;
            }
            Locale english = new Locale.Builder().setLanguage("en").build();
            bundle = ResourceBundle.getBundle("bundles.language", english);
            Locale.setDefault(english);
        }

        logger.info("New language: " + choice);

        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("game-view.fxml"),
                bundle);

        Node node = (Node) event.getSource();
        Stage currentStage = (Stage) node.getScene().getWindow();
        currentStage.close();

        loader.setResources(bundle);

        try {
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("SudokuGame");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            logger.error("Cant load");
        }

    }


    /**
     * Save board to file  ./View/files/sudokuBoard.txt .
     *
     * @param actionEvent press of the button
     */
    private ActionEvent saveToFile(ActionEvent actionEvent) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuBoard.set(i, j, propertyBoard[i][j].getValue());
            }
        }

        //Creating a File chooser
        //        FileChooser fileChooser = new FileChooser();
        //        fileChooser.setTitle(bundle.getString("save.to.file.label"));
        //        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
        //        "Txt Files",
        //                "*.txt"));
        //        Node node = (Node) actionEvent.getSource();
        //        Stage currentStage = (Stage) node.getScene().getWindow();
        //        File saveFile = fileChooser.showSaveDialog(currentStage);
        //
        //        try (FileSudokuBoardDao sudokuBoardDao =
        //                     (FileSudokuBoardDao) factory.createFileDao(saveFile.getName());) {
        //            sudokuBoardDao.write(sudokuBoard);
        //        } catch (DaoException e) {
        //            logger.error(e.getLocalizedMessage());
        //            e.printStackTrace();
        //        }

        // show pop up where to input sudoku name
        TextInputDialog popUp = new TextInputDialog();
        popUp.setHeaderText("Enter the name of sudoku:");
        String name = "";
        Optional<String> result = popUp.showAndWait();
        if (result.isPresent()) {
            name = popUp.getEditor().getText();
        }

        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("game.db")) {
            if (dao.isNameInDatabase(name)) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("Sudoku name already in use");
                a.show();
                return actionEvent;
            }
            sudokuBoard.setName(name);
            dao.write(sudokuBoard);
        } catch (JdbcDriverNotFound | JdbcCantCreateTables e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
        } catch (AppException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actionEvent; // nie usuwać bo pmd się czepia
    }

    private ActionEvent importBoardFromDatabase(ActionEvent event) {
        TextInputDialog popUp = new TextInputDialog();
        popUp.setHeaderText("Enter the name of sudoku:");
        String name = "";
        Optional<String> result = popUp.showAndWait();
        if (result.isPresent()) {
            name = popUp.getEditor().getText();
        }

        try (JdbcSudokuBoardDao dao = (JdbcSudokuBoardDao) factory.createJdbcDao("game.db")) {
            if (!dao.isNameInDatabase(name)) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("No sudoku with given name in database.");
                a.show();
                return event;
            }
            sudokuBoard.setName(name);
            sudokuBoard = dao.readBoardByName(name);
            insertTextValues();
        } catch (AppException e) {
            logger.error(e.getLocalizedMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return event; // nie usuwać bo pmd się czepia
    }

}