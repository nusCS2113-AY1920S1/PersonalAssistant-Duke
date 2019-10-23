package spinbox.gui;

import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Window;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import spinbox.SpinBox;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.TaskList;
import spinbox.entities.Module;
import spinbox.entities.items.tasks.Schedulable;
import spinbox.entities.items.tasks.Task;
import spinbox.entities.items.tasks.TaskType;
import spinbox.exceptions.DataReadWriteException;
import spinbox.exceptions.FileCreationException;
import spinbox.exceptions.InvalidIndexException;

import java.util.ArrayList;
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
    private ModuleContainer moduleContainer;
    private Popup popup = new Popup();
    private ArrayList<String> history = new ArrayList<>();
    private int count = 0;

    @FXML
    public void initialize()  {
    }

    /**
     * Set up the required stuff for the main window.
     * @param spinbox SpinBox instance
     * @throws DataReadWriteException storage exception.
     * @throws FileCreationException file creation exception.
     * @throws InvalidIndexException invalid index exception.
     */
    public void setUpMain(SpinBox spinbox) throws DataReadWriteException, FileCreationException, InvalidIndexException {
        spinBox = spinbox;
        update();
        setPopup(popup);
        userInput.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case UP:
                if (count < history.size()) {
                    userInput.setText(history.get(count));
                    count += 1;
                }
                userInput.end();
                break;
            case DOWN:
                count -= 1;
                if (count > 0) {
                    count -= 1;
                    userInput.setText(history.get(count));
                } else {
                    userInput.clear();
                    count = 0;
                }
                userInput.end();
                break;
            default:
                break;
            }

        });
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing SpinBox's reply and then appends
     * them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() throws InvalidIndexException, DataReadWriteException, FileCreationException {
        history.add(0, userInput.getText());
        count = 0;
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
            update();
            getPopup(response);
            break;
        }
        userInput.clear();
        if (spinBox.isShutdown()) {
            System.exit(0);
        }
    }

    private void update() throws InvalidIndexException, DataReadWriteException, FileCreationException {
        updateMain();
        updateCalendar();
        updateModules();
    }


    private void updateMain() throws InvalidIndexException, DataReadWriteException, FileCreationException {
        updateUrgentTask();
        updateExams();
    }

    private void updateUrgentTask() throws DataReadWriteException, InvalidIndexException, FileCreationException {

        TaskList allTasks = new TaskList("Main");
        urgentTasks.getChildren().clear();
        moduleContainer = spinBox.getModuleContainer();
        HashMap<String, Module> modules = moduleContainer.getModules();
        for (Map.Entry module : modules.entrySet()) {
            String moduleCode = (String) module.getKey();
            Module moduleObject = (Module) module.getValue();
            TaskList tasks = moduleObject.getTasks();
            for (Task task : tasks.getList()) {
                if (!task.getDone()) {
                    allTasks.add(task);
                }
            }
        }

        allTasks.sort();

        int boxes;
        if (allTasks.size() < 5) {
            boxes = allTasks.size();
        }  else {
            boxes = 5;
        }
        for (int i = 0; i < boxes; i++) {
            Task addTask = allTasks.get(i);
            String description = addTask.getTaskType().name();
            description += ": " + addTask.getName();
            String dates = "";
            if (addTask.isSchedulable()) {
                Schedulable task = ((Schedulable)addTask);
                dates += task.getStartDate().toString();
                if (TaskType.taskWithBothDates().contains(task.getTaskType())) {
                    dates += " " + task.getEndDate().toString();
                    dates = "At: " + dates;
                } else {
                    dates = "By: " + dates;
                }
            }
            String moduleCode = "";
            urgentTasks.getChildren().add(TaskBox.getTaskBox(description, moduleCode, dates));
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

    public void setPopup(Popup popup) {
        popup.setAutoHide(true);
        popup.setAnchorLocation(PopupWindow.AnchorLocation.WINDOW_TOP_LEFT);
    }

    /**
     * Set up popup style and add label
     * and show popup with string res.
     * @param res response string from spinbox.
     */
    public void getPopup(String res) {
        popup.getContent().clear();
        GridPane grid = new GridPane();
        Label response = new Label();
        response.setText(res);
        grid.setStyle("-fx-background-color:white;"
                + "-fx-border-color: black;"
                + "-fx-border-width:2;"
                + "-fx-border-radius:3;"
                + "-fx-hgap:3;"
                + "-fx-vgap:5;");
        grid.getChildren().add(response);
        popup.getContent().add(grid);
        Window window = tabPane.getScene().getWindow();
        popup.setX(600);
        popup.setY(788);
        popup.show(window);
    }

}
