package chronologer.ui;

import chronologer.command.Command;
import chronologer.parser.Parser;
import chronologer.task.Task;
import chronologer.task.TaskList;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ListChangeListener;

import javax.swing.event.ListDataListener;

/**
 * UI element designed for the user to interact with the application.
 * It has 3 main tasks.
 * 1. Displays and reads user's input.
 * 2. Parses VALID user's input into a defined command and displays the corresponding result.
 * 3. Displays the appropriate error message for INVALID user's input.
 */
class TimelineWindow extends UiComponent<Region> {
    private static final String FXML = "TimelineWindow.fxml";
    private static final Double mondayX = 1.0;
    private static final Double tuesdayX = 136.0;
    private static final Double wednesdayX = 269.0;
    private static final Double thursdayX = 403.0;
    private static final Double fridayX = 538.0;
    private static final Double saturdayX = 671.0;
    private static final Double sundayX = 806.0;
    private static Double moveXOfDays = 0.0;


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
    private ListView<Task> priorityTask;
    @FXML
    private ListView<Task> tasksWithoutDates;

    private Parser parser;
    private Command command;
    private TaskList tasks;
    /**
     * Constructs a UiComponent with the corresponding FXML file name and root object.
     * The FXML file written should not have a controller attribute as this is handled by the loadFXMLFile.
     *
     * @param command Holds the name of the corresponding FXML file.
     * @param parser
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
        attachListenerForObjects();
        populateEveryDay();
        prioritizedTodayTasks();
        tasksWithoutDates();
    }

    /**
     * This method populates the ListView with prioritized tasks for the day.
     */
    private void prioritizedTodayTasks() {
        ObservableList<Task> holdPriorityTasks;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        holdPriorityTasks = FXCollections.observableArrayList(tasks.obtainPriorityList(dtf.format(now)));
        priorityTask.setItems(holdPriorityTasks);
    }

    /**
     * This method populates the ListView with prioritized tasks for the day.
     */
    private void tasksWithoutDates() {
        ObservableList<Task> holdTasksWithoutDates;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        holdTasksWithoutDates = FXCollections.observableArrayList(tasks.obtainTasksWithoutDates());
        tasksWithoutDates.setItems(holdTasksWithoutDates);
    }

    /**
     *
     */
    private void attachTasksListener() {
        tasks.getObservableListOfTasks().addListener((ListChangeListener<Task>) change -> {
            while(change.next()) {
                if (change.wasAdded()) {
                    populateEveryDay();
                    prioritizedTodayTasks();
                    tasksWithoutDates();
                }
                else if (change.wasRemoved()) {
                    populateEveryDay();
                    prioritizedTodayTasks();
                    tasksWithoutDates();
                }
                else if (change.wasReplaced()) {
                    populateEveryDay();
                    prioritizedTodayTasks();
                    tasksWithoutDates();
                }
            }
        });
    }

    private void attachListenerForObjects() {
        tasks.getObservableListOfTasks().addListener((InvalidationListener) data -> {
              System.out.println("HERE");
              populateEveryDay();
              prioritizedTodayTasks();
              tasksWithoutDates();
            }
        );
    }


    /**
     * This method will populate the ListViews of the timeline.
     */
    private void populateEveryDay() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        ObservableList<String> mondayTasks;
        ObservableList<String> tuesdayTasks;
        ObservableList<String> wednesdayTasks;
        ObservableList<String> thursdayTasks;
        ObservableList<String> fridayTasks;
        ObservableList<String> saturdayTasks;
        ObservableList<String> sundayTasks;

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

        if (shiftLocationOfHighlight.equals(monday)) {
            moveXOfDays = mondayX;
        }  else if (shiftLocationOfHighlight.equals(tuesday)) {
            moveXOfDays = tuesdayX;
        }  else if (shiftLocationOfHighlight.equals(wednesday)) {
            moveXOfDays = wednesdayX;
        }  else if (shiftLocationOfHighlight.equals(thursday)) {
            moveXOfDays = thursdayX;
        }  else if (shiftLocationOfHighlight.equals(friday)) {
            moveXOfDays = fridayX;
        }  else if (shiftLocationOfHighlight.equals(saturday)) {
            moveXOfDays = saturdayX;
        }  else if (shiftLocationOfHighlight.equals(sunday)) {
            moveXOfDays = sundayX;
        }

        mondayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(monday));
        tuesdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(tuesday));
        wednesdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(wednesday));
        thursdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(thursday));
        fridayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(friday));
        saturdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(saturday));
        sundayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(sunday));

        mondayTask.setItems(mondayTasks);
        tuesdayTask.setItems(tuesdayTasks);
        wednesdayTask.setItems(wednesdayTasks);
        thursdayTask.setItems(thursdayTasks);
        fridayTask.setItems(fridayTasks);
        saturdayTask.setItems(saturdayTasks);
        sundayTask.setItems(sundayTasks);
    }
}