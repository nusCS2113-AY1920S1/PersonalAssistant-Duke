package compal.ui;

import compal.compal.Compal;


import java.text.ParseException;

import compal.tasks.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableView<Task> tableView;
    @FXML
    private TableColumn<Task, String> midnight;


    @FXML
    private Label date;
    private Compal compal;

    /**
     * Prints initialization message.
     */
    @FXML
    public void initialize() {
        System.out.println("MainWindow:LOG: Controller Class Initialized");
    }

    /**
     * Initializes Compal.
     *
     * @param d Compal object.
     */
    public void setCompal(Compal d) {
        compal = d;
    }

    /**
     * Handles user input by sending it to the parser.
     * Called by the enter button inside MainWindow.fxml.
     */
    @FXML
    private void handleUserInput() throws ParseException, Compal.DukeException {
        String cmd = userInput.getText();
        //send to parser to parse
        compal.parser.processCmd(cmd);
        userInput.clear();
    }


}