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
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private Button cmdButton;
    @FXML
    private DatePicker datePicker;
    private Duke duke;
    private Storage storage;
    private ArrayList<Task> todo;
    private ArrayList<Task> event;
    private ArrayList<Task> deadline;
    private Ui ui = new Ui();

    /**
     * This method initializes the display in the window of the GUI.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            event = new ArrayList<>();
            todo = new ArrayList<>();
            deadline = new ArrayList<>();
            todoLabel.setText("To-Do");
            deadlineLabel.setText("Deadline");
            setClock();
            retrieveList();
            openReminderBox();

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

    public void setDuke(Duke d) {
        duke = d;
    }

    private void setClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM/yyyy HH:mm:ss");
            currentTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void retrieveList() throws IOException, ParseException {
        storage = new Storage(System.getProperty("user.dir") + "\\data\\duke.txt");
        TaskList taskList = new TaskList();
        storage.readFile(taskList);
        ArrayList<Task> tempList = taskList.getList();
        for(Task task : tempList) {
            if(task.toString().contains("[T]")) {
                todo.add(task);
            } else if (task.toString().contains("[D]")) {
                deadline.add(task);
            } else {
                event.add(task);
            }
        }
    }

    private ObservableList<TimetableView> setEventTable() throws ParseException {
        String to;
        String from;
        String description;
        String activity;
        ObservableList<TimetableView> timetables = FXCollections.observableArrayList();
        for(Task task : event){
            activity = task.toString();
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            DateFormat timeFormat= new SimpleDateFormat("HH:mm");
            Date date = dateFormat.parse(activity.substring(activity.indexOf("at:") + 4, activity.indexOf(')')));
            to = "";
            from = timeFormat.format(date);
            description = task.getDescription();
            timetables.add(new TimetableView(to, from, description));
        }
        return timetables;
    }

    private ObservableList<DeadlineView> setDeadlineTable() throws ParseException {
        String to;
        String description;
        String activity;
        ObservableList<DeadlineView> deadlines = FXCollections.observableArrayList();
        for(Task task : deadline){
            activity = task.toString();
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            DateFormat timeFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = dateFormat.parse(activity.substring(activity.indexOf("by:") + 4, activity.indexOf(')')));
            to = timeFormat.format(date);
            description = task.getDescription();
            deadlines.add(new DeadlineView(to, description));
        }
        return deadlines;
    }

    private ObservableList<TodoView> setTodoTable() {
        String description;
        String done;
        String activity;
        ObservableList<TodoView> todos = FXCollections.observableArrayList();
        for(Task task : todo){
            activity = task.toString();
            description = activity.substring(7);
            if (activity.contains("[\u2713[")) {
                done = "\u2713";
            } else {
                done = "\u2718";
            }
            todos.add(new TodoView(description, done));
        }
        return todos;
    }
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

     private void openReminderBox() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        for (int i = 0; i < todo.size(); i++) {
            if (todo.get(i).getDescription().contains("(from") && todo.get(i).getDescription().contains("to")) {
                String description = todo.get(i).getDescription();
                int index = description.indexOf("(from");
                String taskDescription = description.substring(0, index);
                description = description.replace(taskDescription, "");
                description = description.replace("(from", "").trim();
                String[] dateString = description.split(" to ", 2);
                String startDate = dateString[0];
                String endDate = dateString[1].replace(")", "").trim();

                if (formatter.format(date).equals(startDate)) {
                    AlertBox.display("Reminder Alert", " To Do Within Period Task: " + taskDescription,
                            "Reminder starts today. On: " + startDate, Alert.AlertType.INFORMATION);

                } else if(formatter.format(date).equals(endDate)) {
                    AlertBox.display("Reminder Alert", "To Do Within Period Task: " + taskDescription,
                            "Reminder ends today. On: " + endDate, Alert.AlertType.INFORMATION);

                }
            }
        }
    }



}