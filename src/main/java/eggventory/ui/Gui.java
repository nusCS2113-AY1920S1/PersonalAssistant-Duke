package eggventory.ui;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TableColumn;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

//@@author Raghav-B
/**
 * This is a controller class used to control the Gui.fxml from the entry point for
 * our application, the Eggventory class. Inherits most of its functionality from Cli
 * to ensure backwards compatibility with testing and if we choose to fall back to a
 * Cli implementation. Overrides some Cli functionality to interface with the Gui instead
 * of Cli.
 */
public class Gui extends Ui  {
    @FXML
    private TextFlow textFlow;
    @FXML
    private TextArea outputField;
    @FXML
    private TableView<ArrayList<String>> outputTable;
    @FXML
    private ScrollPane outputTableScroll;

    private InputTextBox inputField;

    private TableStruct myTableStruct;

    /**
     * Starts the REPL loop and creates the JavaFX window along with other JavaFX controls
     * and event handlers.
     * @param runMethod Function passed in for REPL loop that is called by some event handlers.
     */
    public void initialize(Runnable runMethod) {
        Platform.startup(() -> {
            Stage stage = new Stage();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Gui.fxml"));
                fxmlLoader.setController(this);
                stage = fxmlLoader.load();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

            inputField = new InputTextBox(textFlow);
            outputTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            printIntro();

            // Event handler for all keypresses
            stage.addEventFilter(KeyEvent.ANY, keyEvent -> {
                if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                    // Handling keypresses apart from typed characters.
                    switch (keyEvent.getCode()) {
                    case ENTER:
                        if (inputField.getAllText().equals("")) {
                            // No input is parsed if there is no text input
                            // in inputField.
                            break;
                        }
                        runMethod.run();
                        break;

                    case BACK_SPACE:
                        inputField.removeTextBackspace();
                        break;

                    case DELETE:
                        inputField.removeTextDelete();
                        break;

                    case TAB:
                        if (inputField.getAllText().equals("")) {
                            // Prevents autocompletion when user has not even input anything.
                            break;
                        }
                        inputField.acceptSearchText();
                        break;

                    case UP:
                        if (keyEvent.isShiftDown()) {
                            inputField.appendText("", -1);
                        } else {
                            inputField.clearAllText();
                            inputField.appendText(CommandHistory.getCommand(-1), 0);
                        }
                        break;

                    case DOWN:
                        if (keyEvent.isShiftDown()) {
                            inputField.appendText("", 1);
                        } else {
                            inputField.clearAllText();
                            inputField.appendText(CommandHistory.getCommand(1), 0);
                        }
                        break;

                    case LEFT:
                        // Used to move caret to edit text on the left.
                        inputField.moveCaret(-1);
                        break;

                    case RIGHT:
                        // Used to move caret to edit text on the right.
                        inputField.moveCaret(1);
                        break;

                    default:
                        // To appease mr. travis
                        break;
                    }
                } else if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                    char inputChar = keyEvent.getCharacter().charAt(0);
                    // Ignoring all inputs that are not standard type-able characters
                    if (!(inputChar >= ' ' && inputChar <= '~')) {
                        return;
                    }
                    inputField.appendText(keyEvent.getCharacter(), 0);
                }
            });
        });
    }

    /**
     * Reads in user input from inputField and outputs to outputField
     * TextArea.
     * @return Returns String to be used by Parser in REPL loop.
     */
    public String read() {
        String userInput = inputField.getAllText();
        CommandHistory.addToHistory(userInput);
        inputField.clearAllText();
        outputField.appendText("\n" + userInput);
        return userInput;
    }

    /**
     * Prints text output in the outputField TextArea.
     * @param printString The raw String to be printed out, after some extra formatting.
     */
    public String print(String printString) {
        String output = printFormatter(printString);
        outputField.appendText("\n" + output);
        return output;
    }

    /**
     * Clears the GUI table output.
     */
    public void clearTable() {
        outputTable.getColumns().clear();
        outputTable.getItems().clear();
        outputTable.refresh();
    }

    /**
     * Can be called to redraw the table output as and when needed. Takes in a StockList object
     * that it uses to redraw the entire table.
     * @param tableStruct Structure that holds all data to be displayed.
     */
    public void drawTable(TableStruct tableStruct) {
        outputTable.getColumns().clear();
        outputTable.getItems().clear();
        outputTable.refresh();

        this.myTableStruct = tableStruct;
        TableColumn mainColumn = new TableColumn(myTableStruct.getTableName());
        mainColumn.setReorderable(false);
        outputTable.getColumns().add(mainColumn);

        // Iterating through columns to setup all columns.
        for (int i = 0; i < tableStruct.getTableColumnSize(); i++) {
            // Creating column with header
            TableColumn<ArrayList<String>, String> column = new TableColumn<>(myTableStruct.getColumnName(i));
            // Assigning column to take row values from data stores in tableFormat ArrayList.
            int finalI = i;
            if (finalI == 0) {
                column.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(
                        outputTable.getItems().indexOf(cell.getValue()) + 1));
            } else {
                column.setCellValueFactory(cell -> new ReadOnlyObjectWrapper(cell.getValue().get(finalI - 1)));
            }

            // Adding column to table to be visualized.
            column.setReorderable(false);
            mainColumn.getColumns().add(column);
        }

        // Adding data from tableStruct to outputTable row by row.
        for (int i = 0; i < tableStruct.getTableRowSize(); i++) {
            outputTable.getItems().add(tableStruct.getRowData(i));
        }
    }
}
//@@author
