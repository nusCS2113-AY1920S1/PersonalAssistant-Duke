package compal.ui;

import compal.logic.parser.exceptions.ParseException;
import compal.logic.LogicManager;
import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.Task;
import compal.storage.TaskStorageManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;

/**
 * Handles GUI.
 * This is a JavaFXML Controller class.
 */
public class MainWindow extends AnchorPane {
    //Class Properties/Variables
    private LogicManager logicManager;
    private TaskStorageManager taskStorageManager;
    private UiUtil uiUtil;

    private ArrayList<Task> taskArrList;

    @FXML
    private TextField userInput;

    public MainWindow() {
        this.taskStorageManager = new TaskStorageManager();
        this.logicManager = new LogicManager();
        this.taskArrList = new ArrayList<>();
    }

    /**
     * Handles user input by sending it to the parser.
     * Called by the enter button inside MainWindow.fxml.
     */
    @FXML
    private void handleUserInput() throws ParseException, CommandException {
        String cmd = userInput.getText();
        init();
        logicManager.logicExecute(cmd,taskArrList);
        userInput.clear();
    }

    private void init(){
       taskArrList = taskStorageManager.loadData();
    }


}