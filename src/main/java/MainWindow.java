import command.Command;
import exception.DukeException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import parser.ParserFactory;
import storage.Storage;
import task.Task;
import task.TaskList;
import task.Todo;
import ui.Ui;

import javax.xml.stream.Location;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ScrollPane errorScrollPane;
    @FXML
    private VBox errorMessageContainer;
    @FXML
    private VBox dialogContainer;
    @FXML
    private VBox todayTaskContainer;
    @FXML
    private ScrollPane mondayScrollPane;
    @FXML
    private ScrollPane tuesdayScrollPane;
    @FXML
    private ScrollPane wednesdayScrollPane;
    @FXML
    private ScrollPane thursdayScrollPane;
    @FXML
    private ScrollPane fridayScrollPane;
    @FXML
    private ScrollPane saturdayScrollPane;
    @FXML
    private ScrollPane sundayScrollPane;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private Label todayTaskLabel;
    @FXML
    private ListView<Task> tasksForTheDay;
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

    /**
     * Allocation of the images for the chat bot.
     */
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    private static String filePath = System.getProperty("user.dir") + "/src/DukeDatabase/ArrayList";
    private static Storage storage;
    private static TaskList tasks;
    private static File file = new File(filePath);
    private static ObservableList<Task> holdTodayTasks;
    private static ObservableList<String> mondayTasks;
    private static ObservableList<String> tuesdayTasks;
    private static ObservableList<String> wednesdayTasks;
    private static ObservableList<String> thursdayTasks;
    private static ObservableList<String> fridayTasks;
    private static ObservableList<String> saturdayTasks;
    private static ObservableList<String> sundayTasks;

    /**
     * This method is utilised to initialize the required aspects of Duke such as the storage and the rendering of
     * the TaskList.
     */
    public static void initializeDukeElements() {
        try {
            storage = new Storage(file);
            tasks = new TaskList(storage.loadFile(file));
        } catch (DukeException e) {
            tasks = new TaskList(new ArrayList<>());
            Ui.printMessage(e.getMessage());
        }
        populateTodayTasks();
        populateEveryDay();
    }

    /**
     * This @FXML initialize() is a special function where static members of the GUI can be initialised.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        tasksForTheDay.setItems(holdTodayTasks);
        todayTaskLabel.setText("You have " + holdTodayTasks.size() + " task(s) today!");
        mondayTask.setItems(mondayTasks);
        tuesdayTask.setItems(tuesdayTasks);
        wednesdayTask.setItems(wednesdayTasks);
        thursdayTask.setItems(thursdayTasks);
        fridayTask.setItems(fridayTasks);
        saturdayTask.setItems(saturdayTasks);
        sundayTask.setItems(sundayTasks);
    }

    /**
     * This @FXML handleUserInput() is provides the logic for the text field, whenever the user provides an input
     * this function handles it by passing it to the Duke logic.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        try {
            Command command = ParserFactory.parse(input);
            command.execute(tasks, storage);
            tasks = new TaskList(storage.loadFile(file));
            populateTodayTasks();
            tasksForTheDay.setItems(holdTodayTasks);
            todayTaskLabel.setText("You have " + holdTodayTasks.size() + " task(s) today!");
            populateEveryDay();
            mondayTask.setItems(mondayTasks);
            tuesdayTask.setItems(tuesdayTasks);
            wednesdayTask.setItems(wednesdayTasks);
            thursdayTask.setItems(thursdayTasks);
            fridayTask.setItems(fridayTasks);
            saturdayTask.setItems(saturdayTasks);
            sundayTask.setItems(sundayTasks);
        } catch (DukeException e) {
            errorMessageContainer.getChildren().addAll(
                ErrorMessageBar.getErrorMessage(e.getMessage())
            );
        }
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage),
            DialogBox.getUserDialog(Ui.userOutputForUI, dukeImage)
        );
        userInput.clear();
    }

    private static void populateTodayTasks() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        holdTodayTasks = FXCollections.observableArrayList(tasks.schedule(dtf.format(now)));
    }

    private static void populateEveryDay() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        final DayOfWeek Monday = DayOfWeek.MONDAY;
        final DayOfWeek Tuesday = DayOfWeek.TUESDAY;
        final DayOfWeek Wednesday = DayOfWeek.WEDNESDAY;
        final DayOfWeek Thursday = DayOfWeek.THURSDAY;
        final DayOfWeek Friday = DayOfWeek.FRIDAY;
        final DayOfWeek Saturday = DayOfWeek.SATURDAY;
        final DayOfWeek Sunday = DayOfWeek.SUNDAY;

        String monday = dtf.format(LocalDate.now().with(TemporalAdjusters.nextOrSame(Monday)));
        String tuesday = dtf.format(LocalDate.now().with(TemporalAdjusters.nextOrSame(Tuesday)));
        String wednesday = dtf.format(LocalDate.now().with(TemporalAdjusters.nextOrSame(Wednesday)));
        String thursday = dtf.format(LocalDate.now().with(TemporalAdjusters.nextOrSame(Thursday)));
        String friday = dtf.format(LocalDate.now().with(TemporalAdjusters.nextOrSame(Friday)));
        String saturday = dtf.format(LocalDate.now().with(TemporalAdjusters.nextOrSame(Saturday)));
        String sunday = dtf.format(LocalDate.now().with(TemporalAdjusters.nextOrSame(Sunday)));

        Integer sundayDate = Integer.parseInt(sunday.split("/", 2)[0].trim());

        if(Integer.parseInt(monday.split("/", 2)[0].trim()) > sundayDate) {
            monday = dtf.format(LocalDate.now().with(TemporalAdjusters.previousOrSame(Monday)));
        }
        else if(Integer.parseInt(tuesday.split("/", 2)[0].trim()) > sundayDate) {
           tuesday = dtf.format(LocalDate.now().with(TemporalAdjusters.previousOrSame(Tuesday)));
        }
        else if(Integer.parseInt(wednesday.split("/", 2)[0].trim()) > sundayDate) {
            wednesday = dtf.format(LocalDate.now().with(TemporalAdjusters.previousOrSame(Wednesday)));
        }
        else if(Integer.parseInt(thursday.split("/", 2)[0].trim()) > sundayDate) {
            thursday = dtf.format(LocalDate.now().with(TemporalAdjusters.previousOrSame(Thursday)));
        }
        else if(Integer.parseInt(friday.split("/", 2)[0].trim()) > sundayDate) {
            friday = dtf.format(LocalDate.now().with(TemporalAdjusters.previousOrSame(Friday)));
        }
        else if(Integer.parseInt(saturday.split("/", 2)[0].trim()) > sundayDate) {
            saturday = dtf.format(LocalDate.now().with(TemporalAdjusters.previousOrSame(Saturday)));
        }

        mondayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(monday));
        tuesdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(tuesday));
        wednesdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(wednesday));
        thursdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(thursday));
        fridayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(friday));
        saturdayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(saturday));
        sundayTasks = FXCollections.observableArrayList(tasks.scheduleForDay(sunday));
    }

}
