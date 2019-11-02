package chronologer.ui;

import chronologer.command.Command;
import chronologer.parser.Parser;
import chronologer.task.Task;
import chronologer.task.TaskList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import javafx.collections.ListChangeListener;
import javafx.util.Callback;

/**
 * UI element designed for the user to interact with the application.
 * It has 3 main tasks.
 * 1. Displays and reads user's input.
 * 2. Parses VALID user's input into a defined command and displays the corresponding result.
 * 3. Displays the appropriate error message for INVALID user's input.
 */
class TimelineWindow extends UiComponent<Region> {
    private static final String FXML = "TimelineWindow.fxml";
    private static final Double mondayX = 44.0;
    private static final Double tuesdayX = 241.0;
    private static final Double wednesdayX = 436.0;
    private static final Double thursdayX = 633.0;
    private static final Double fridayX = 828.0;
    private static final Double saturdayX = 44.0;
    private static final Double sundayX = 241.0;
    private static final Double weekdayY = 50.0;
    private static final Double weekendY = 357.0;
    private static Double moveXOfDays = 0.0;
    private static Double moveYOfDays = 0.0;


    @FXML
    private ListView<String> mondayTask;
    @FXML
    private ListView<String> tuesdayTask;
    @FXML
    private ListView<String> wednesdayTask;
    @FXML
    private ListView<String> thursdayTask;
    @FXML
    private ListView<String> fridayTask;
    @FXML
    private ListView<String> saturdayTask;
    @FXML
    private ListView<String> sundayTask;
    @FXML
    private ListView<String> priorityTask;
    @FXML
    private ListView<String> tasksWithoutDates;
    @FXML
    private ListView<String> reminderTask;
    @FXML
    private Label todayLabel;

    private Parser parser;
    private Command command;
    private TaskList tasks;
    private static final String DEFAULT_CONTROL_INNER_BACKGROUND = "#373737";

    /**
     * Constructs a UiComponent with the corresponding FXML file name and root object.
     * The FXML file written should not have a controller attribute as this is handled by the loadFXMLFile.
     *
     * @param command Holds the command object of MainWindow.
     * @param parser Holds the parser object of MainWindow.
     * @param tasks Holds the tasks object of MainWindow.
     */
    TimelineWindow(Command command, Parser parser, TaskList tasks) {
        super(FXML, null);
        this.command = command;
        this.parser = parser;
        this.tasks = tasks;
        initializeTimelineComponents();
    }

    private void initializeTimelineComponents() {
        attachTasksListener();
        populateEveryDay();
        populateReminders();
        prioritizedTodayTasks();
        tasksWithoutDates();
        initializeListViewComponents();
    }

    /**
     * This @FXML initialize() is a special function where static members of the GUI can be initialised.
     */
    @FXML
    public void initialize() {

    }

    private void initializeListViewComponents() {
        initializeListViewDarkComponents(mondayTask);
        initializeListViewDarkComponents(tuesdayTask);
        initializeListViewDarkComponents(wednesdayTask);
        initializeListViewDarkComponents(thursdayTask);
        initializeListViewDarkComponents(fridayTask);
        initializeListViewDarkComponents(saturdayTask);
        initializeListViewDarkComponents(sundayTask);
        initializeListViewDarkComponents(priorityTask);
        initializeListViewDarkComponents(tasksWithoutDates);
    }

    /**
     * This method populates the ListView with prioritized tasks for the day.
     */
    private void populateReminders() {
        ObservableList<String> reminderTasks;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        reminderTasks = FXCollections.observableArrayList(tasks.fetchReminders(now));
        reminderTask.setItems(reminderTasks);
    }

    /**
     * This method populates the ListView with prioritized tasks for the day.
     */
    private void prioritizedTodayTasks() {
        ObservableList<String> holdPriorityTasks;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        holdPriorityTasks = FXCollections.observableArrayList(tasks.obtainPriorityList(dtf.format(now)));
        priorityTask.setItems(holdPriorityTasks);
    }

    /**
     * This method populates the ListView with prioritized tasks for the day.
     */
    private void tasksWithoutDates() {
        ObservableList<String> holdTasksWithoutDates;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        holdTasksWithoutDates = FXCollections.observableArrayList(tasks.obtainTasksWithoutDates());
        tasksWithoutDates.setItems(holdTasksWithoutDates);
    }

    /**
     * This method attaches the listener.
     */
    private void attachTasksListener() {
        tasks.getObservableListOfTasks().addListener((ListChangeListener<Task>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    populateEveryDay();
                    prioritizedTodayTasks();
                    tasksWithoutDates();
                } else if (change.wasRemoved()) {
                    populateEveryDay();
                    prioritizedTodayTasks();
                    tasksWithoutDates();
                } else if (change.wasReplaced()) {
                    populateEveryDay();
                    prioritizedTodayTasks();
                    tasksWithoutDates();
                }
            }
        });
    }

    /**
     * This method will populate the ListViews of the timeline.
     */
    private void populateEveryDay() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        final DayOfWeek Sunday = DayOfWeek.SUNDAY;
        LocalDate sundayDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(Sunday));

        String monday = dtf.format(sundayDate.minusDays(6));
        String tuesday = dtf.format(sundayDate.minusDays(5));
        String wednesday = dtf.format(sundayDate.minusDays(4));
        String thursday = dtf.format(sundayDate.minusDays(3));
        String friday = dtf.format(sundayDate.minusDays(2));
        String saturday = dtf.format(sundayDate.minusDays(1));
        String sunday = dtf.format(sundayDate);
        String shiftLocationOfHighlight = dtf.format(now);
        todayLabel.setStyle("-fx-border-color: #009933; -fx-border-width: 3;");
        if (shiftLocationOfHighlight.equals(monday)) {
            moveXOfDays = mondayX;
            moveYOfDays = weekdayY;
        }  else if (shiftLocationOfHighlight.equals(tuesday)) {
            moveXOfDays = tuesdayX;
            moveYOfDays = weekdayY;
        }  else if (shiftLocationOfHighlight.equals(wednesday)) {
            moveXOfDays = wednesdayX;
            moveYOfDays = weekdayY;
        }  else if (shiftLocationOfHighlight.equals(thursday)) {
            moveXOfDays = thursdayX;
            moveYOfDays = weekdayY;
        }  else if (shiftLocationOfHighlight.equals(friday)) {
            moveXOfDays = fridayX;
            moveYOfDays = weekdayY;
        }  else if (shiftLocationOfHighlight.equals(saturday)) {
            moveXOfDays = saturdayX;
            moveYOfDays = weekendY;
            todayLabel.setStyle("-fx-border-color: #FF0000; -fx-border-width: 3;");
        }  else if (shiftLocationOfHighlight.equals(sunday)) {
            moveXOfDays = sundayX;
            moveYOfDays = weekendY;
            todayLabel.setStyle("-fx-border-color: #FF0000; -fx-border-width: 3;");
        }

        todayLabel.setLayoutX(moveXOfDays);
        todayLabel.setLayoutY(moveYOfDays);

        ObservableList<String> mondayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(monday));
        mondayTask.setItems(mondayTasks);
        ObservableList<String> tuesdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(tuesday));
        tuesdayTask.setItems(tuesdayTasks);
        ObservableList<String> wednesdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(wednesday));
        wednesdayTask.setItems(wednesdayTasks);
        ObservableList<String> thursdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(thursday));
        thursdayTask.setItems(thursdayTasks);
        ObservableList<String> fridayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(friday));
        fridayTask.setItems(fridayTasks);
        ObservableList<String> saturdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(saturday));
        saturdayTask.setItems(saturdayTasks);
        ObservableList<String> sundayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(sunday));
        sundayTask.setItems(sundayTasks);
    }

    private void initializeListViewDarkComponents(ListView<String> stringListView) {
        stringListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                        setStyle("-fx-control-inner-background: " + DEFAULT_CONTROL_INNER_BACKGROUND + ";");
                    }
                };
            }
        });
    }
}