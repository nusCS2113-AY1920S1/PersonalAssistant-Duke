package duke.gui;

import duke.Duke;
import duke.commands.Command;
import duke.commands.assignedtask.AssignPeriodTaskCommand;
import duke.commands.gui.FilterCommand;
import duke.commands.task.UpcomingTasksCommand;
import duke.exceptions.DukeException;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.UpcomingTasks;
import duke.models.patients.Patient;
import duke.models.tasks.Task;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Accordion;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//@@author HUANGXUANKUN

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends UiPart<Stage> {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private TabPane centerTabPane;
    @FXML
    private TabPane leftTabPane;
    @FXML
    private Accordion upComingTasksAccordion;
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
    @FXML
    private TextField addTaskNameField;
    @FXML
    private TextField deleteTaskIdField;
    @FXML
    private TextField assignTaskIdField;
    @FXML
    private TextField assignTaskPatientIdField;
    @FXML
    private TextField assignTaskStartTimeField;
    @FXML
    private TextField assignTaskEndTimeField;
    @FXML
    private DatePicker assignTaskStartDatePicker;
    @FXML
    private DatePicker assignTaskEndDatePicker;
    @FXML
    private DatePicker assignDeadlineTaskDatePicker;
    @FXML
    private TextField assignDeadlineTaskTimeField;
    @FXML
    private TextField assignTaskUuidField;
    @FXML
    private VBox helpGuideContainer;
    @FXML
    private ScrollPane helpGuideScrollPane;

    @FXML
    private VBox firstDayBox;
    @FXML
    private VBox secondDayBox;
    @FXML
    private VBox thirdDayBox;
    @FXML
    private VBox fourthDayBox;
    @FXML
    private VBox fifthDayBox;
    @FXML
    private VBox sixthDayBox;
    @FXML
    private VBox seventhDayBox;

    @FXML
    private TitledPane firstTitledPane;
    @FXML
    private TitledPane secondTitledPane;
    @FXML
    private TitledPane thirdTitledPane;
    @FXML
    private TitledPane fourthTitledPane;
    @FXML
    private TitledPane fifthTitledPane;
    @FXML
    private TitledPane sixthTitledPane;
    @FXML
    private TitledPane seventhTitledPane;
    @FXML
    private ScrollPane firstScroll;
    @FXML
    private ScrollPane secondScroll;
    @FXML
    private ScrollPane thirdScroll;
    @FXML
    private ScrollPane fourthScroll;
    @FXML
    private ScrollPane fifthScroll;
    @FXML
    private ScrollPane sixthScroll;
    @FXML
    private ScrollPane seventhScroll;

    //@@author HUANGXUANKUN
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/user.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/robot.png"));
    private ObservableList<AssignedTask> assignedTaskData;
    private ObservableList<Task> taskData;
    private ObservableList<Patient> patientData;
    private Duke duke;
    private Stage primaryStage;
    private static final String FXML = "MainWindow.fxml";

    /**
     * Creates the Main Window.
     *
     * @param primaryStage The stage to display MainWindow on.
     * @param duke         Logic component
     */
    public MainWindow(Stage primaryStage, Duke duke) {
        super(FXML, primaryStage);
        this.primaryStage = primaryStage;
        this.duke = duke;
        initialize();
    }

    public void show() {
        primaryStage.show();
    }

    /**
     * Returns the stage that MainWindow is displayed on.
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Initialize GUI components.
     */
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        initializeTableViews();
        showHelpGuide();
        initializeAutoCompletion();
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
     * Initialize GUI tables.
     */
    public void initializeTableViews() {
        assignedTaskData = FXCollections
            .observableArrayList(duke.getAssignedTaskManager().getAssignTasks());
        taskData = FXCollections.observableArrayList(duke.getTaskManager().getTaskList());
        patientData = FXCollections
            .observableArrayList(duke.getPatientManager().getPatientList());

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
     * Update tables. To be called after each command execution
     */
    public void updateTableViews() throws DukeException {
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
        try {
            showUpcomingTasks();
        } catch (Exception e) {
            throw new DukeException("Failed to update Upcoming Tasks tab.");
        }
    }

    /**
     * Execute duke core with userInput command given
     * and update dialog container and tables.
     */
    private void executeDukeWithInput(String inputCommand) {
        String dukeResponses;
        boolean isException = false;
        try {
            dukeResponses = duke.run(inputCommand);
            Command command = duke.getRunningCommand();
            updateTableViews();
            executeGuiInstructions(command);
        } catch (DukeException de) {
            dukeResponses = de.getMessage();
            isException = true;
        }
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(inputCommand, userImage),
            DialogBox.getDukeDialog(dukeResponses, dukeImage, isException)
        );
        duke.clearDukeResponses();
    }

    /**
     * Manipulate relevant GUI component after a command is successfully executed.
     * For example, after a patient is being added, it will display the patient table at the center tab pane.
     *
     * @param command Running DukeCommand
     */
    private void executeGuiInstructions(Command command) {
        System.out.println(command.getClass().getSimpleName());
        String commandName = command.getClass().getSimpleName();
        switch (commandName) {
        case "AssignDeadlineTaskCommand":
        case "AssignPeriodTaskCommand":
        case "DeleteAssignedTaskCommand":
        case "FindAssignedTaskCommand":
            centerTabPane.getSelectionModel().select(2);
            clearFilter();
            break;
        case "ShowAssignedTasksCommand":
            centerTabPane.getSelectionModel().select(2);
            break;
        case "AddPatientCommand":
        case "DeletePatientCommand":
        case "ListPatientsCommand":
        case "UpdatePatientCommand":
        case "FindPatientCommand":
            centerTabPane.getSelectionModel().select(0);
            clearFilter();
            break;
        case "ShowPatientsCommand":
            centerTabPane.getSelectionModel().select(0);
            break;
        case "AddTaskCommand":
        case "DeleteTaskCommand":
        case "ListTasksCommand":
        case "FindTaskCommand":
            clearFilter();
            centerTabPane.getSelectionModel().select(1);
            break;
        case "ShowTasksCommand":
            centerTabPane.getSelectionModel().select(1);
            break;
        case "ShowHelpGuideCommand":
            leftTabPane.getSelectionModel().select(5);
            break;
        case "ShowTodayCommand":
            leftTabPane.getSelectionModel().select(4);
            upComingTasksAccordion.setExpandedPane(firstTitledPane);
            break;
        case "ShowTomorrowCommand":
            leftTabPane.getSelectionModel().select(4);
            upComingTasksAccordion.setExpandedPane(secondTitledPane);
            break;
        case "FilterCommand":
            FilterCommand filterCommand = (FilterCommand) command;
            String filterContent = filterCommand.getFilterInfo();
            filter(filterContent);
            break;
        case "ClearFilterCommand":
            clearFilter();
            break;
        default:
            break;
        }
    }

    /**
     * It will clear the text in the search bar such that rows in tables are not being filtered.
     */
    private void clearFilter() {
        patientSearchTextField.clear();
        taskSearchTextField.clear();
        assignedTaskSearchTextField.clear();
    }

    /**
     * It will filter the rows in current tables(patient/task/assigned task) with given input.
     */
    private void filter(String input) {
        int currentTableIndex = getSelectedTableIndex();
        if (currentTableIndex == 0) {
            patientSearchTextField.setText(input);
        } else if (currentTableIndex == 1) {
            taskSearchTextField.setText(input);
        } else {
            assignedTaskSearchTextField.setText(input);
        }
    }

    /**
     * Find the index of table being displayed currently at the center of the GUI.
     * Index 0 refers to patient table.
     * Index 1 refers to tasks table.
     * Index 2 refers to assigned tasks table.
     *
     * @return index of current selected table.
     */
    private int getSelectedTableIndex() {
        return centerTabPane.getSelectionModel().getSelectedIndex();
    }

    /**
     * Listen to search bar input for filtering rows in patient table.
     */
    private void patientSearchBarListener() {
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Patient> filteredPatients = new FilteredList<>(patientData, b -> true);

        // Capture Patient's search bar text field
        patientSearchTextField.textProperty().addListener((ObservableValue<? extends String> observable,
                                                           String oldValue, String newValue) -> {
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
     * Listen to search bar input for filtering rows in task table.
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

    /**
     * Listen to search bar input for filtering rows in assign task table.
     */
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

    //@@author

    //@@author qjie7

    /**
     * Action takes to after Undo button is being pressed.
     */
    public void handleUndoButton() {
        executeDukeWithInput("undo");
    }


    /**
     * Event handler of PieChartPopUpButton.
     */
    public void handlePieChartPopUpButton() throws DukeException {
        Map<String, Integer> counterMap = new HashMap<>();
        counterMap = duke.getStorageManager().loadCommandFrequency();
        ArrayList<Integer> frequencyList = new ArrayList<Integer>(counterMap.values());
        final ArrayList<String> commandNameList = new ArrayList<String>(counterMap.keySet());
        final Scene scene = new Scene(new Group());
        Stage stage = new Stage();
        stage.setTitle("Pie Chart");

        stage.setWidth(500);
        stage.setHeight(500);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (int i = 0; i < commandNameList.size(); i++) {
            pieChartData.add(new PieChart.Data(commandNameList.get(i), frequencyList.get(i)));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Command Frequency");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();

    }


    /**
     * Event handler of BarCharPopUpButton.
     */
    public void handleBarChartPopUpButton() throws DukeException {

        Map<String, Integer> counterMap;
        counterMap = duke.getStorageManager().loadCommandFrequency();
        final ArrayList<Integer> frequencyList = new ArrayList<Integer>(counterMap.values());
        final ArrayList<String> commandNameList = new ArrayList<String>(counterMap.keySet());
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<String, Number>(xAxis, yAxis);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        final String yearInString = Integer.toString(year);

        Stage stage = new Stage();
        stage.setTitle("Bar Chart");

        bc.setTitle("Command Frequency");
        xAxis.setLabel("Command");
        yAxis.setLabel("Frequency");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(yearInString);

        for (int i = 0; i < commandNameList.size(); i++) {
            series1.getData().add(new XYChart.Data(commandNameList.get(i), frequencyList.get(i)));
        }

        Scene scene = new Scene(bc, 800, 600);
        bc.getData().addAll(series1);
        stage.setScene(scene);
        stage.show();

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
     * Action takes to after update patient button is being pressed.
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
     * Action takes to after delete patient button is being pressed.
     */
    @FXML
    private void handleDeletePatientButton() {
        String id = deletePatientIdField.getText();
        String input = "delete patient:" + "#" + id;
        executeDukeWithInput(input);
        deletePatientIdField.clear();
    }

    /**
     * Action takes to after list patients button is being pressed.
     */
    @FXML
    private void handleListPatientsButton() {
        executeDukeWithInput("list patients");
    }

    /**
     * Action takes to after add task button is being pressed.
     */
    @FXML
    private void handleAddTaskButton() {
        String name = addTaskNameField.getText();
        String input = "add task:" + name;
        executeDukeWithInput(input);
        addTaskNameField.clear();
    }

    /**
     * Action takes to after delete task button is being pressed.
     */
    @FXML
    private void handleDeleteTaskButton() {
        String id = deleteTaskIdField.getText();
        String input = "delete task:" + "#" + id;
        executeDukeWithInput(input);
        deleteTaskIdField.clear();
    }

    /**
     * Action takes to after list task button is being pressed.
     */
    @FXML
    private void handleListTasksButton() {
        executeDukeWithInput("list tasks");
    }

    /**
     * Action takes to after Assign Period Task button is being pressed.
     */
    @FXML
    private void handleAssignTaskButton() {
        String taskId = assignTaskIdField.getText();
        String patientId = assignTaskPatientIdField.getText();
        LocalDate startDate = assignTaskStartDatePicker.getValue();
        LocalDate endDate = assignTaskEndDatePicker.getValue();
        String startDateInString = startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String endDateInString = endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String startTime = assignTaskStartTimeField.getText();
        String endTime = assignTaskEndTimeField.getText();
        String input = "assign period task :" + "#" + patientId + " :"
            + "#" + taskId + " :" + startDateInString + " " + startTime
            + " :" + endDateInString + " " + endTime;
        executeDukeWithInput(input);
        assignTaskIdField.clear();
        assignTaskPatientIdField.clear();
        assignTaskStartTimeField.clear();
        assignTaskEndTimeField.clear();
    }

    /**
     * Action takes to after Assign Deadline Task button is being pressed.
     */
    @FXML
    private void handleDeadlineTaskButton() {
        String taskId = assignTaskIdField.getText();
        String patientId = assignTaskPatientIdField.getText();
        LocalDate deadlineDate = assignDeadlineTaskDatePicker.getValue();

        String deadlineDateInString = deadlineDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String deadlineTime = assignDeadlineTaskTimeField.getText();
        String input = "assign deadline task :" + "#" + patientId + " :"
            + "#" + taskId + " :" + deadlineDateInString + " " + deadlineTime;
        executeDukeWithInput(input);
        assignTaskIdField.clear();
        assignTaskPatientIdField.clear();
        assignDeadlineTaskTimeField.clear();
    }

    /**
     * Action takes to after Delete Assign task button is being pressed.
     */
    @FXML
    private void handleDeleteUuidTaskButton() {
        String uuid = assignTaskUuidField.getText();

        String input = "delete assigned task :" + "#" + uuid;
        executeDukeWithInput(input);
        assignTaskUuidField.clear();

    }
    //@@author

    //@@author lmtaek

    /**
     * Handler for HelpGuide tab.
     */
    public void showHelpGuide() {
        for (HelpBox newHelpBox : HelpBox.getHelpGuide()) {
            helpGuideContainer.getChildren().addAll(newHelpBox);
            helpGuideScrollPane.setContent(helpGuideContainer);
        }
    }

    /**
     * Handler for Upcoming Tasks tab. Dynamically updates to display tasks assigned for day within upcoming week.
     *
     * @throws DukeException When Upcoming Tasks tab is unable to locate Upcoming Tasks to fill out respective fields.
     */
    public void showUpcomingTasks() throws DukeException {
        VBox[] upcomingTaskContainers = {firstDayBox, secondDayBox, thirdDayBox, fourthDayBox,
            fifthDayBox, sixthDayBox, seventhDayBox};
        TitledPane[] titledPanes = {firstTitledPane, secondTitledPane, thirdTitledPane, fourthTitledPane,
            fifthTitledPane, sixthTitledPane, seventhTitledPane};
        ScrollPane[] scrollPanes = {firstScroll, secondScroll, thirdScroll, fourthScroll,
            fifthScroll, sixthScroll, seventhScroll};
        ArrayList<UpcomingTasks> upcomingTasks =
            new UpcomingTasksCommand(LocalDateTime.now(), false).getUpcomingTaskLists();

        for (int i = 0; i < 7; i++) {
            upcomingTasks.add(new UpcomingTasks(LocalDateTime.now().plusDays(i),
                duke.getAssignedTaskManager(), duke.getTaskManager(),
                duke.getPatientManager()));
        }

        for (int i = 0; i < upcomingTaskContainers.length; i++) {
            upcomingTaskContainers[i].getChildren().clear();
            titledPanes[i].setText(upcomingTasks.get(i).getFormattedDate());
            ArrayList<UpcomingTasksBox> taskBoxesForDate
                = UpcomingTasksBox.createUpcomingTasksBoxesForDate(upcomingTasks.get(i).getTaskAndInfo());

            for (UpcomingTasksBox taskInfoForDate : taskBoxesForDate) {
                upcomingTaskContainers[i].getChildren().addAll(taskInfoForDate);
            }
            scrollPanes[i].setContent(upcomingTaskContainers[i]);
            titledPanes[i].setContent(scrollPanes[i]);
        }
    }

    //@@author qjie7
    private void initializeAutoCompletion() {
        String[] possibleWords = {"add task :", "add patient :", "assign period task :#", "assign deadline task :#",
            "list patients", "list tasks", "delete patient :#", "delete task :#",
            "delete assigned task :#", "find patient :#", "find patient :", "find task :#",
            "find task :", "find assigned tasks :#", "find assigned task :", "update patient :#",
            "update task :#", "show upcoming tasks", "barchart", "help", "piechart", "bye", "undo",
            "help"};
        TextFields.bindAutoCompletion(userInput, possibleWords);
    }

}
