package compal.inputs;

import compal.main.Duke;

import java.text.ParseException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Handles GUI.
 * This is a JavaFXML Controller class.
 */
public class MainWindow extends AnchorPane {
    //Class Properties/Variables
    @FXML
    private TextField userInput;
    @FXML
    private Label date;
    private Duke duke;

    /**
     * Prints initialization message.
     */
    @FXML
    public void initialize() {
        System.out.println("MainWindow:LOG: Controller Class Initialized");
    }

    /**
     * Initializes duke.
     *
     * @param d Duke object.
     */
    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Handles user input by sending it to the parser.
     * Called by the enter button inside MainWindow.fxml.
     */
    @FXML
    private void handleUserInput() throws ParseException, Duke.DukeException {
        String cmd = userInput.getText();
        //send to parser to parse
        duke.parser.processCMD(cmd);
        userInput.clear();
    }
}