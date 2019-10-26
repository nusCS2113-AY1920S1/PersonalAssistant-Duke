package duke.gui;

import duke.Duke;
import duke.models.assignedtasks.AssignedTask;
import duke.models.patients.Patient;
import duke.models.tasks.Task;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, Integer> patientIdCol;
    @FXML
    private TableColumn<Patient, String> patientNameCol;
    @FXML
    private TableColumn<Patient, String> patientRoomCol;
    @FXML
    private TableColumn<Patient, String> patientRemarkCol;
    @FXML
    private TableColumn<Patient, String> patientNricCol;

    @FXML
    private TableView<Task> taskTable;
    @FXML
    private TableColumn<Task, Integer> taskIdCol;
    @FXML
    private TableColumn<Task, String> taskDescriptionCol;

    @FXML
    private TableView<AssignedTask> assignedTaskTable;
    @FXML
    private TableColumn<AssignedTask, Integer> assignedUuidCol;
    @FXML
    private TableColumn<AssignedTask, String> assignedTypeCol;
    @FXML
    private TableColumn<AssignedTask, Integer> assignedTidCol;
    @FXML
    private TableColumn<AssignedTask, String> assignedDescriptionCol;
    @FXML
    private TableColumn<AssignedTask, Integer> assignedPidCol;
    @FXML
    private TableColumn<AssignedTask, String> assignedPnameCol;
    @FXML
    private TableColumn<AssignedTask, Boolean> assignedIsDoneCol;
    @FXML
    private TableColumn<AssignedTask, String> assignedTodoDateCol;
    @FXML
    private TableColumn<AssignedTask, String> assignedStartDateCol;
    @FXML
    private TableColumn<AssignedTask, String> assignedEndDateCol;


    private Duke duke = new Duke("./data");

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/robot.png"));

    private ObservableList<Patient> patientData;
    private ObservableList<Task> taskData;
    private ObservableList<AssignedTask> assignTaskData;

    /**
     * .
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        initializeTableViews();
    }

    /**
     * .
     */
    public void initializeTableViews() {
        assignTaskData = FXCollections.observableArrayList(duke.getAssignedTaskManager().getAssignTasks());
        taskData = FXCollections.observableArrayList(duke.getTaskManager().getTaskList());
        patientData = FXCollections.observableArrayList(duke.getPatientManager().getPatientList());

        patientIdCol.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("id"));
        patientNameCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
        patientNricCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("nric"));
        patientRoomCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("room"));
        patientRemarkCol.setCellValueFactory(new PropertyValueFactory<Patient, String>("remark"));

        taskIdCol.setCellValueFactory(new PropertyValueFactory<Task, Integer>("id"));
        taskDescriptionCol.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));


        assignedUuidCol.setCellValueFactory(new PropertyValueFactory<AssignedTask, Integer>("uuid"));
        assignedTypeCol.setCellValueFactory(new PropertyValueFactory<AssignedTask, String>("type"));
        assignedTidCol.setCellValueFactory(new PropertyValueFactory<AssignedTask, Integer>("tid"));
        assignedPidCol.setCellValueFactory(new PropertyValueFactory<AssignedTask, Integer>("pid"));
        assignedIsDoneCol.setCellValueFactory(new PropertyValueFactory<AssignedTask, Boolean>("IsDone"));
        assignedTodoDateCol.setCellValueFactory(new PropertyValueFactory<AssignedTask, String>("todoDateRaw"));
        assignedStartDateCol.setCellValueFactory(new PropertyValueFactory<AssignedTask, String>("startDateRaw"));
        assignedEndDateCol.setCellValueFactory(new PropertyValueFactory<AssignedTask, String>("endDateRaw"));
        assignedDescriptionCol.setCellValueFactory(cellData -> {
            AssignedTask currentAssignedTask = cellData.getValue();
            return Bindings.createStringBinding(
                () -> {
                    int tid = currentAssignedTask.getTid();
                    return duke.getTaskManager().getTask(tid).getDescription();
                }
            );
        });
        assignedPnameCol.setCellValueFactory(cellData -> {
            AssignedTask currentAssignedTask = cellData.getValue();
            return Bindings.createStringBinding(
                () -> {
                    int pid = currentAssignedTask.getPid();
                    return duke.getPatientManager().getPatient(pid).getName();
                }
            );
        });
        assignedTaskTable.setItems(assignTaskData);
        taskTable.setItems(taskData);
        patientTable.setItems(patientData);
    }

    /**
     * .
     */
    public void updateTableViews() {
        assignTaskData.clear();
        taskData.clear();
        patientData.clear();
        assignTaskData = FXCollections.observableArrayList(duke.getAssignedTaskManager().getAssignTasks());
        taskData = FXCollections.observableArrayList(duke.getTaskManager().getTaskList());
        patientData = FXCollections.observableArrayList(duke.getPatientManager().getPatientList());
        assignedTaskTable.setItems(assignTaskData);
        taskTable.setItems(taskData);
        patientTable.setItems(patientData);
    }

    /**
     * .
     *
     * @param duke .
     */
    public void setDuke(Duke duke) {
        this.duke = duke;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        duke.readUserInputFromGui(input);
        duke.run();
        String responses = duke.getDukeResponses();
        System.out.println(responses);
        updateTableViews();
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage),
            DialogBox.getDukeDialog(responses, dukeImage)
        );
        userInput.clear();
        duke.clearDukeResponses();
    }
}