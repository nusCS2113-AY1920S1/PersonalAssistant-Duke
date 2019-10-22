package spinbox.gui;

import javafx.scene.control.TabPane;
import spinbox.SpinBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import spinbox.containers.lists.TaskList;
import spinbox.entities.Module;
import spinbox.entities.items.tasks.Task;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.InvalidIndexException;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private TabPane tabPane;
    @FXML
    private VBox urgentTasks;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private SpinBox spinBox;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
    }

    public void setSpinBox(SpinBox d) {
        spinBox = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing SpinBox's reply and then appends
     * them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws InvalidIndexException, DataReadWriteException {
        String input = userInput.getText();
        String response = spinBox.getResponse(input, true);
        switch (response) {
        case "/main":
            tabPane.getSelectionModel().select(0);
            break;
        case "/calendar":
            tabPane.getSelectionModel().select(1);
            break;
        case "/modules":
            tabPane.getSelectionModel().select(2);
            break;
        default:
            //update();
            break;
        }
        userInput.clear();
        if (spinBox.isShutdown()) {
            System.exit(0);
        }
    }

    private void update() throws InvalidIndexException, DataReadWriteException {
        updateMain();
        updateCalendar();
        updateModules();
    }

    private void updateMain() throws InvalidIndexException, DataReadWriteException {
        updateUrgentTask();
        updateExams();
        updateModules();
    }

    private void updateUrgentTask() throws DataReadWriteException, InvalidIndexException {
        TaskList allTasks = null;
        urgentTasks.getChildren().clear();
        HashMap<String, Module> modules = spinBox.getModules();
        for (Map.Entry module : modules.entrySet()) {
            String moduleCode = (String) module.getKey();
            Module moduleObject = (Module) module.getValue();
            TaskList tasks = moduleObject.getTasks();
            for (Task task : tasks.getList()) {
                allTasks.add(task);
            }
        }

        allTasks.sort();
        for (int i = 0; i < 5; i++) {
            Task addTask = allTasks.get(i);
            String description = addTask.getName();
            String dates = "";
            String moduleCode = "";
        }

    }

    private void updateExams() {
        assert true;
    }

    private void updateCalendar() {
        assert true;
    }

    private void updateModules() {
        assert true;
    }
}
