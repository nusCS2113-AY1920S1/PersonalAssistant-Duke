package compal.inputs;

import compal.main.Duke;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.text.ParseException;

/**
 * JavaFXML Controller class for handling GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private TextField userInput;
    @FXML
    private Label date;

    private Duke duke;


    @FXML
    public void initialize() {
        System.out.println("Controller Class Initialized");

    }

    public void setDuke(Duke d) {
        duke = d;
    }


    /**
     * Called by the enter button (inside MainWindow.fxml). Handles user input by sending it to the parser.
     * Basically the eyes of the program
     */
    @FXML
    private void handleUserInput() throws ParseException {
        String cmd = userInput.getText();
        //send to parser to parse
        duke.parser.processCommands(cmd);

        userInput.clear();
    }


}