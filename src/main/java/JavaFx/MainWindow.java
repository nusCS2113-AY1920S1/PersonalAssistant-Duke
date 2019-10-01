package JavaFx;
import Interface.*;
import Tasks.Task;
import Tasks.TaskList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends BorderPane implements Initializable {
    @FXML
    private Label currentTime;
    @FXML
    private Label todoLabel;
    @FXML
    private Label deadlineLabel;
    @FXML
    private TableColumn<TodoView, String> todoTaskColumn;
    @FXML
    private TableColumn<TodoView, String> todoDoneColumn;
    @FXML
    private TableView<TodoView> todoTable;
    @FXML
    private TableColumn<TimetableView, String> eventFromColumn;
    @FXML
    private TableColumn<TimetableView, String> eventToColumn;
    @FXML
    private TableColumn<TimetableView, String> eventTaskColumn;
    @FXML
    private TableView<TimetableView> eventTable;
    @FXML
    private TableColumn<DeadlineView, String> deadlineTimeColumn;
    @FXML
    private TableColumn<DeadlineView, String> deadlineTaskColumn;
    @FXML
    private TableView<DeadlineView> deadlineTable;
    @FXML
    private DatePicker datePicker;
    private Duke duke;
    private Storage storage;
    private ArrayList<Task> todos;
    private ArrayList<Task> events;
    private ArrayList<Task> deadlines;
    private TaskList todosList;
    private TaskList eventsList;
    private TaskList deadlinesList;

    /**
     * This method initializes the display in the window of the GUI.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            events = new ArrayList<>();
            todos = new ArrayList<>();
            deadlines = new ArrayList<>();
            datePicker = new DatePicker(LocalDate.now());
            todoLabel.setText("To-Do");
            deadlineLabel.setText("Deadline");
            setClock();
            retrieveList();

            eventToColumn.setCellValueFactory(new PropertyValueFactory<>("to"));
            eventFromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
            eventTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
            eventTable.setItems(setEventTable());

            deadlineTimeColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            deadlineTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
            deadlineTable.setItems(setDeadlineTable());

            todoTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
            todoDoneColumn.setCellValueFactory(new PropertyValueFactory<>("done"));
            todoTable.setItems(setTodoTable());

        } catch (ParseException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize Duke object in MainWindow controller with Duke object from Main.
     * @param d Duke object from Main bridge
     */
    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Animates the clock timer in MainWindow GUI.
     */
    private void setClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM/yyyy HH:mm:ss");
            currentTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /**
     * Pulls the list from storage data and stores here.
     * @throws IOException On input error reading lines in the file
     * @throws ParseException On conversion error from string to Task object
     */
    private void retrieveList() throws IOException, ParseException {
        storage = new Storage();
        todosList = new TaskList();
        eventsList = new TaskList();
        deadlinesList = new TaskList();
        storage.readTodoList(todosList);
        storage.readEventList(eventsList);
        storage.readDeadlineList(deadlinesList);
        todos = todosList.getList();
        events = eventsList.getList();
        deadlines = deadlinesList.getList();
    }

    private ObservableList<TimetableView> setEventTable() throws ParseException {
        String to;
        String from;
        String description;
        ObservableList<TimetableView> timetables = FXCollections.observableArrayList();
        for (Task task : events) {
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            DateFormat endTimeFormat = new SimpleDateFormat("hh:mm a");
            DateFormat timeFormat= new SimpleDateFormat("HH:mm");
            String arr[] = task.getDateTime().split("to");
            to = timeFormat.format(endTimeFormat.parse(arr[1].trim()));
            from = timeFormat.format(dateFormat.parse(arr[0].trim()));
            description = task.getDescription();
            timetables.add(new TimetableView(to, from, description));
        }
        return timetables;
    }

    private ObservableList<DeadlineView> setDeadlineTable() throws ParseException {
        String to;
        String description;
        String activity;
        ObservableList<DeadlineView> deadlineViews = FXCollections.observableArrayList();
        for (Task task : deadlines) {
            activity = task.toString();
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            DateFormat timeFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = dateFormat.parse(activity.substring(activity.indexOf("by:") + 4, activity.indexOf(')')));
            to = timeFormat.format(date);
            description = task.getDescription();
            deadlineViews.add(new DeadlineView(to, description));
        }
        return deadlineViews;
    }

    private ObservableList<TodoView> setTodoTable() {
        String description;
        String done;
        String activity;
        ObservableList<TodoView> todoViews = FXCollections.observableArrayList();
        for (Task task : todos) {
            activity = task.toString();
            description = activity.substring(7);
            if (activity.contains("[\u2713[")) {
                done = "\u2713";
            } else {
                done = "\u2718";
            }
            todoViews.add(new TodoView(description, done));
        }
        return todoViews;
    }

    /**
     * This method sets the platform for CLI fxml.
     */
    @FXML
    private void openCommandScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ChatBot.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            fxmlLoader.<ChatBot>getController().setDuke(duke);
            stage.setTitle("DukeBot");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}