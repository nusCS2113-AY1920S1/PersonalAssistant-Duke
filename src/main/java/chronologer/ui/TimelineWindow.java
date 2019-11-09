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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

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
    @FXML
    private AnchorPane backgroundOfTimeline;
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
    @FXML
    private Label todayLabel1;
    @FXML
    private Label currentWeekLabel;

    private TaskList tasks;

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

    private static final String DARK_MODE = "#373737";
    private static final String LIGHT_MODE = "#f1f1f1";
    private static final String GREEN_LABEL = "-fx-border-color: #009933; -fx-border-width: 3;";
    private static final String RED_LABEL = "-fx-border-color: #FF0000; -fx-border-width: 3;";
    private static final String BACKGROUND_COLOR_DARK = "-fx-background-color: #000000;";
    private static final String BACKGROUND_COLOR_LIGHT = "-fx-background-color: #D3D3D3; -fx-border-color: #FFFFFF";
    private static final int MONDAY = 6;
    private static final int TUESDAY = 5;
    private static final int WEDNESDAY = 4;
    private static final int THURSDAY = 3;
    private static final int FRIDAY = 2;
    private static final int SATURDAY = 1;
    private static final int SUNDAY = 0;
    private static final int CURRENT_WEEK_INDICATOR = -1;

    private LocalDate currentSundayDate;


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
        this.tasks = tasks;
        initializeTimelineComponents();
    }

    private void initializeTimelineComponents() {
        attachTasksListener();
        currentSundayDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        populateEveryDay();
        populateReminders();
        prioritizedTodayTasks();
        tasksWithoutDates();
        setDarkMode();
        moveTodayLabels();
    }

    @FXML
    private void updateDarkBackground() {
        backgroundOfTimeline.setStyle(BACKGROUND_COLOR_DARK);
    }

    @FXML
    private void updateLightBackground() {
        backgroundOfTimeline.setStyle(BACKGROUND_COLOR_LIGHT);
    }

    private void setDarkMode() {
        setListViewBackgroundAndTheme(mondayTask, tuesdayTask, wednesdayTask, thursdayTask, fridayTask, DARK_MODE,
            BACKGROUND_COLOR_DARK);
        setListViewBackgroundAndTheme(saturdayTask, sundayTask, priorityTask, tasksWithoutDates, reminderTask,
            DARK_MODE, BACKGROUND_COLOR_DARK);
        updateDarkBackground();
    }

    private void setLightMode() {
        setListViewBackgroundAndTheme(mondayTask, tuesdayTask, wednesdayTask, thursdayTask, fridayTask, LIGHT_MODE,
            BACKGROUND_COLOR_LIGHT);
        setListViewBackgroundAndTheme(saturdayTask, sundayTask, priorityTask, tasksWithoutDates, reminderTask,
            LIGHT_MODE, BACKGROUND_COLOR_LIGHT);
        updateLightBackground();
    }


    private void setListViewBackgroundAndTheme(ListView<String> task1, ListView<String> task2, ListView<String> task3,
                                               ListView<String> task4, ListView<String> task5, String theme,
                                               String background) {
        listViewComponents(task1, theme);
        task1.setStyle(background);
        listViewComponents(task2, theme);
        task2.setStyle(background);
        listViewComponents(task3, theme);
        task3.setStyle(background);
        listViewComponents(task4, theme);
        task4.setStyle(background);
        listViewComponents(task5, theme);
        task5.setStyle(background);
    }

    /**
     * This method populates the ListView with prioritized tasks for the day.
     */
    private void populateReminders() {
        ObservableList<String> reminderTasks;
        reminderTasks = FXCollections.observableArrayList(tasks.fetchReminders());
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
                    populateReminders();
                    prioritizedTodayTasks();
                    tasksWithoutDates();
                } else if (change.wasRemoved()) {
                    populateEveryDay();
                    populateReminders();
                    prioritizedTodayTasks();
                    tasksWithoutDates();
                } else if (change.wasReplaced()) {
                    populateEveryDay();
                    populateReminders();
                    prioritizedTodayTasks();
                    tasksWithoutDates();
                }
            }
        });

        tasks.getCurrentSetting().addListener((ListChangeListener<Integer>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    if (tasks.getCurrentSetting().get(0).equals(1)) {
                        setLightMode();
                    } else {
                        setDarkMode();
                    }
                    changeWeek(tasks.getCurrentSetting().get(1));
                } else if (change.wasReplaced()) {
                    if (tasks.getCurrentSetting().get(0).equals(1)) {
                        setLightMode();
                    } else {
                        setDarkMode();
                    }
                    changeWeek(tasks.getCurrentSetting().get(1));
                }
            }
        });
    }

    private void changeWeek(int chosenWeek) {
        final DayOfWeek Sunday = DayOfWeek.SUNDAY;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        LocalDate firstDay = LocalDate.of(currentYear, 8, 1);
        LocalDate firstMondayOfSemester = firstDay.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        LocalDate firstSundayOfSemester = firstMondayOfSemester.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        LocalDate requiredSundayDate;
        if (chosenWeek == CURRENT_WEEK_INDICATOR) {
            requiredSundayDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(Sunday));
            todayLabel.setVisible(true);
        } else {
            requiredSundayDate = firstSundayOfSemester.plusDays(chosenWeek * 7);
            if (requiredSundayDate.isEqual(LocalDate.now().with(TemporalAdjusters.nextOrSame(Sunday)))) {
                todayLabel.setVisible(true);
            } else {
                todayLabel.setVisible(false);
            }
        }
        currentSundayDate = requiredSundayDate;
    }

    /**
     * This method will populate the ListViews of the timeline.
     */
    private void populateEveryDay() {
        String monday = getDay(MONDAY);
        currentWeekLabel.setText("Your timeline for week staring with " + monday);
        String tuesday = getDay(TUESDAY);
        String wednesday = getDay(WEDNESDAY);
        String thursday = getDay(THURSDAY);
        String friday = getDay(FRIDAY);
        String saturday = getDay(SATURDAY);
        String sunday = getDay(SUNDAY);

        setTasks(monday, tuesday, wednesday, mondayTask, tuesdayTask, wednesdayTask);
        setTasks(thursday, friday, saturday, thursdayTask, fridayTask, saturdayTask);
        ObservableList<String> sundayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(sunday));
        sundayTask.setItems(sundayTasks);
    }

    /**
     * Returns date required of particular day as a String.
     * @param dayAdjuster Holds the days to subtract from Sunday eg. to get Monday subtract 6.
     */
    private String getDay(int dayAdjuster) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(currentSundayDate.minusDays(dayAdjuster));
    }

    private void moveTodayLabels() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        String shiftLocationOfHighlight = dtf.format(now);

        todayLabel.setStyle(GREEN_LABEL);
        todayLabel1.setStyle(GREEN_LABEL);
        if (shiftLocationOfHighlight.equals(getDay(MONDAY))) {
            moveXOfDays = mondayX;
            moveYOfDays = weekdayY;
        }  else if (shiftLocationOfHighlight.equals(getDay(TUESDAY))) {
            moveXOfDays = tuesdayX;
            moveYOfDays = weekdayY;
        }  else if (shiftLocationOfHighlight.equals(getDay(WEDNESDAY))) {
            moveXOfDays = wednesdayX;
            moveYOfDays = weekdayY;
        }  else if (shiftLocationOfHighlight.equals(getDay(THURSDAY))) {
            moveXOfDays = thursdayX;
            moveYOfDays = weekdayY;
        }  else if (shiftLocationOfHighlight.equals(getDay(FRIDAY))) {
            moveXOfDays = fridayX;
            moveYOfDays = weekdayY;
        }  else if (shiftLocationOfHighlight.equals(getDay(SATURDAY))) {
            moveXOfDays = saturdayX;
            moveYOfDays = weekendY;
            todayLabel.setStyle(RED_LABEL);
            todayLabel1.setStyle(RED_LABEL);
        }  else if (shiftLocationOfHighlight.equals(getDay(SUNDAY))) {
            moveXOfDays = sundayX;
            moveYOfDays = weekendY;
            todayLabel.setStyle(RED_LABEL);
            todayLabel1.setStyle(RED_LABEL);
        }
        todayLabel.setLayoutX(moveXOfDays);
        todayLabel.setLayoutY(moveYOfDays);
    }


    private void setTasks(String day1, String day2, String day3, ListView<String> day1Task,
                          ListView<String> day2Task, ListView<String> day3Task) {
        ObservableList<String> day1Tasks = FXCollections.observableArrayList(tasks.scheduleForDay(day1));
        day1Task.setItems(day1Tasks);
        ObservableList<String> day2Tasks = FXCollections.observableArrayList(tasks.scheduleForDay(day2));
        day2Task.setItems(day2Tasks);
        ObservableList<String> day3Tasks = FXCollections.observableArrayList(tasks.scheduleForDay(day3));
        day3Task.setItems(day3Tasks);
    }

    private void listViewComponents(ListView<String> stringListView, String mode) {
        stringListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                        setStyle("-fx-control-inner-background: " + mode + ";");
                    }
                };
            }
        });
    }
}