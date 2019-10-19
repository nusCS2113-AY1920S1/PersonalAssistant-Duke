package compal.ui;

import compal.logic.LogicManager;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.text.ParseException;

/**
 * Handles GUI.
 * This is a JavaFXML Controller class.
 */
public class MainWindow extends AnchorPane {
    //Class Properties/Variables
    public static final String MESSAGE_EMPTY_INPUT = "Empty Input: Empty input detected!";
    private LogicManager logicManager;


    @FXML
    private TextField userInput;

    /**
     * Main window constructor.
     */
    public MainWindow() {
        this.logicManager = new LogicManager();

    }

    /**
     * Handles user input by sending it to the parser.
     * Called by the enter button inside MainWindow.fxml.
     */
    @FXML
    private void handleUserInput() throws ParserException, CommandException, ParseException {
        String cmd = userInput.getText();
        if (cmd.isEmpty()) {
            throw new ParserException(MESSAGE_EMPTY_INPUT);
        }
        logicManager.logicExecute(cmd);
        userInput.clear();
    }

}