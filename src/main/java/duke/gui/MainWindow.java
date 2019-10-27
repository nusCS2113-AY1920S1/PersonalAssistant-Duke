package duke.gui;

import duke.Duke;
import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTask;
import duke.models.patients.Patient;
import duke.models.tasks.Task;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private Button addPatientButton;
    @FXML
    private Button deletePatientButton;
    @FXML
    private Button updatePatientButton;
    @FXML
    private Button listPatientsButton;
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
    @FXML
    private TextField patientSearchTextField;
    @FXML
    private TextField taskSearchTextField;
    @FXML
    private TextField assignedTaskSearchTextField;
    @FXML
    private TextField addPatientNameField;
    @FXML
    private TextField addPatientNricField;
    @FXML
    private TextField addPatientRemarkField;
    @FXML
    private TextField addPatientRoomField;
    @FXML
    private TextField deletePatientIdField;
    @FXML
    private TextField updatePatientIdField;
    @FXML
    private TextField updatePatientColumnField;
    @FXML
    private TextField updatePatientContentField;

    private final Duke duke = new Duke("./data");

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/robot.png"));

    private ObservableList<AssignedTask> assignedTaskData = FXCollections
        .observableArrayList(duke.getAssignedTaskManager().getAssignTasks());
    private ObservableList<Task> taskData = FXCollections.observableArrayList(duke.getTaskManager().getTaskList());
    private ObservableList<Patient> patientData = FXCollections
        .observableArrayList(duke.getPatientManager().getPatientList());

    /**
     * .
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        initializeTableViews();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        executeDukeWithInput(userInput.getText());
        userInput.clear();
    }

    /**
     * Action takes to after add patient button is being pressed.
     */
    @FXML
    private void handleAddPatientButton() {
        String name = addPatientNameField.getText();
        String nric = addPatientNricField.getText();
        String room = addPatientRoomField.getText();
        String remark = addPatientRemarkField.getText();
        String input = "add patient:" + name + ":" + nric + ":" + room + ":" + remark;
        executeDukeWithInput(input);
        addPatientNameField.clear();
        addPatientNricField.clear();
        addPatientRoomField.clear();
        addPatientRemarkField.clear();
    }

    /**
     * Action takes to after add patient button is being pressed.
     */
    @FXML
    private void handleUpdatePatientButton() {
        String id = updatePatientIdField.getText();
        String field = updatePatientColumnField.getText();
        String content = updatePatientContentField.getText();
        String input = "update patient:" + "#" + id + ":" + field + ":" + content;
        executeDukeWithInput(input);
        updatePatientIdField.clear();
        updatePatientColumnField.clear();
        updatePatientContentField.clear();
    }

    /**
     * Action takes to after add patient button is being pressed.
     */
    @FXML
    private void handleDeletePatientButton() {
        String id = deletePatientIdField.getText();
        String input = "delete patient:" + "#" + id;
        executeDukeWithInput(input);
        deletePatientIdField.clear();
    }

    /**
     * Action takes to after add patient button is being pressed.
     */
    @FXML
    private void handleListPatientsButton() {
        executeDukeWithInput("list patients");
    }

    /**
     * execute duke core with userInput command given,
     * and update dialog container and tables.
     */
    private void executeDukeWithInput(String inputCommand) {
        String dukeResponses;
        boolean isException = false;
        try {
            dukeResponses = duke.run(inputCommand);
        } catch (DukeException de) {
            dukeResponses = de.getMessage();
            isException = true;
        }
        updateTableViews();
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(inputCommand, userImage),
            DialogBox.getDukeDialog(dukeResponses, dukeImage, isException)
        );
        duke.clearDukeResponses();
    }

    /**
     * .
     */
    public void initializeTableViews() {
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

        assignedTaskTable.setItems(assignedTaskData);
        taskTable.setItems(taskData);
        patientTable.setItems(patientData);
        patientSearchBarListener();
        assignedTaskSearchBarListener();
        taskSearchBarListener();
    }

    /**
     * .
     */
    public void updateTableViews() {
        assignedTaskData.clear();
        taskData.clear();
        patientData.clear();
        assignedTaskData = FXCollections.observableArrayList(duke.getAssignedTaskManager().getAssignTasks());
        taskData = FXCollections.observableArrayList(duke.getTaskManager().getTaskList());
        patientData = FXCollections.observableArrayList(duke.getPatientManager().getPatientList());
        assignedTaskTable.setItems(assignedTaskData);
        taskTable.setItems(taskData);
        patientTable.setItems(patientData);
        patientSearchBarListener();
        assignedTaskSearchBarListener();
        taskSearchBarListener();
    }

    private void patientSearchBarListener() {
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Patient> filteredPatients = new FilteredList<>(patientData, b -> true);

        // Capture Patient's search bar text field
        patientSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPatients.setPredicate(patient -> {
                // If filter text is empty, display all patients
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (patient.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(patient.getId()).contains(lowerCaseFilter)) {
                    return true;
                } else if (patient.getRoom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (patient.getNric().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (patient.getRemark().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false; // No match
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Patient> sortedPatientData = new SortedList<>(filteredPatients);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedPatientData.comparatorProperty().bind(patientTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        patientTable.setItems(sortedPatientData);
    }

    /**
     * .
     */
    private void taskSearchBarListener() {
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Task> filteredPatients = new FilteredList<>(taskData, b -> true);

        // Capture Patient's search bar text field
        taskSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredPatients.setPredicate(task -> {
                // If filter text is empty, display all patients
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (task.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(task.getId()).contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false; // No match
                }
            });
        });
        SortedList<Task> sortedTaskData = new SortedList<>(filteredPatients);
        sortedTaskData.comparatorProperty().bind(taskTable.comparatorProperty());
        taskTable.setItems(sortedTaskData);
    }

    private void assignedTaskSearchBarListener() {
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<AssignedTask> filteredAssignedTasks = new FilteredList<>(assignedTaskData, b -> true);
        assignedTaskSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAssignedTasks.setPredicate(assignedTask -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                try {
                    if (assignedTask.getType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (duke.getTaskManager().getTask((assignedTask.getTid()))
                        .getDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (duke.getPatientManager().getPatient((assignedTask.getPid()))
                        .getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(assignedTask.getUuid()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(assignedTask.getTid()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(assignedTask.getPid()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (String.valueOf(assignedTask.getIsDone()).contains(lowerCaseFilter)) {
                        return true;
                    } else if (assignedTask.getStartDateRaw().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (assignedTask.getEndDateRaw().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (assignedTask.getTodoDateRaw().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else {
                        return false; // Does not match.
                    }
                } catch (DukeException e) {
                    return false;
                }
            });
        });
        SortedList<AssignedTask> sortedAssignedTaskData = new SortedList<>(filteredAssignedTasks);
        sortedAssignedTaskData.comparatorProperty().bind(assignedTaskTable.comparatorProperty());
        assignedTaskTable.setItems(sortedAssignedTaskData);
    }

}