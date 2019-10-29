package compal.ui;

import compal.logic.LogicManager;
import compal.logic.command.CommandResult;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.text.ParseException;

//@@author SholihinK
/**
 * Handles GUI.
 * This is a JavaFXML Controller class.
 */
public class MainWindow extends AnchorPane {
    //Class Properties/Variables
    private LogicManager logicManager;
    private final UiUtil uiUtil;

    @FXML
    private TextField userInput;

    /**
     * Main window constructor.
     */
    public MainWindow() {
        this.logicManager = new LogicManager();
        this.uiUtil = new UiUtil();
    }

    /**
     * Handles user input by sending it to the parser.
     * Called by the enter button inside MainWindow.fxml.
     */
    @FXML
    private void handleUserInput() throws ParserException, CommandException, ParseException {
        String cmd = userInput.getText();
        uiUtil.clearPrimary();
        CommandResult cmdResult =  logicManager.logicExecute(cmd);
        uiUtil.printg(cmdResult.feedbackToUser);
        userInput.clear();
    }
}