package eggventory;

import eggventory.commands.Command;
import eggventory.enums.CommandType;
import eggventory.parsers.Parser;
import eggventory.ui.Gui;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Eggventory is a task list that supports 3 types of classes - Todos, deadlines and events.
 * Tasks can be added, marked as done, searched for, and deleted.
 * Tasks are loaded from and saved to file.
 */

/**
 * Sets up the frontend, the Gui and the event handlers. This will create an instance of the
 * backend, the Eggventory class, and will use that to control the Gui.
 */
public class Eggventory extends Application {
    @FXML
    private TextField inputField;
    @FXML
    private TextArea outputField;
    @FXML
    private TableView outputTable;
    @FXML
    private ScrollPane outputTableScroll;

    private static Storage storage;
    private static Parser parser;
    private static Gui gui;
    private static StockList stockList;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/Gui.fxml"));
            fxmlLoader.setController(this);
            stage = fxmlLoader.load();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Event handler for pressing ENTER
        inputField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                handleUserInput();
            }
        });

        // Event handler for pressing TAB
        inputField.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent ->  {
            if (keyEvent.getCode() == KeyCode.TAB) {
                inputField.appendText("Tab has been pressed! ");
                keyEvent.consume();
            }
        });

        initialize();
    }

    private void initialize() {
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + "/data/saved_tasks.txt";

        storage = new Storage(filePath);
        parser = new Parser();
        gui = new Gui(inputField, outputField, outputTable, outputTableScroll);
        stockList = storage.load();

        gui.printIntro();
    }

    private void handleUserInput() {
        String userInput = inputField.getText();

        if (!userInput.equals("")) { // Check for blank input
            inputField.setText("");
            outputField.appendText("\n" + userInput);

            try {
                Command command = parser.parse(userInput);
                command.execute(stockList, gui, storage);

                if (command.getType() == CommandType.BYE) {
                    System.exit(0);
                }
            } catch (Exception e) {
                gui.printError(e);
            }
        }
    }
}
