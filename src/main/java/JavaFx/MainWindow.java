package JavaFx;
import Interface.*;
import Tasks.Deadline;
import Tasks.Task;
import Tasks.TaskList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends BorderPane implements Initializable {
    private static final String NO_FIELD = "void";
    @FXML
    private Label currentTime;
    @FXML
    private Label currentWeek;
    @FXML
    private TextField userInput;
    @FXML
    private ListView sunEventView;
    @FXML
    private ListView monEventView;
    @FXML
    private ListView tueEventView;
    @FXML
    private ListView wedEventView;
    @FXML
    private ListView thuEventView;
    @FXML
    private ListView friEventView;
    @FXML
    private ListView satEventView;
    @FXML
    private TableView<DeadlineView> overdueTable;
    @FXML
    private TableColumn<DeadlineView, String> overdueDateColumn;
    @FXML
    private TableColumn<DeadlineView, String> overdueTaskColumn;
    @FXML
    private TableView<DeadlineView> deadlineTable;
    @FXML
    private TableColumn<DeadlineView, String> deadlineDateColumn;
    @FXML
    private TableColumn<DeadlineView, String> deadlineTaskColumn;
    private Duke duke;
    private Storage storage;
    private ArrayList<Task> events;
    private ArrayList<Task> deadlines;
    private ArrayList<Task> todos;
    private ArrayList<Task> overdue;
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
            setClock();
            setWeek(NO_FIELD);
            retrieveList();
            openReminderBox();

            deadlineDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            deadlineTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
            deadlineTable.setItems(setDeadlineTable());

            overdueDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            overdueTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
            overdueTable.setItems(setOverdueTable());


            setListItem();

            //todo: handling of the list view
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
        eventsList = new TaskList();
        deadlinesList = new TaskList();
        overdue = new ArrayList<>();
        storage.readEventList(eventsList);
        storage.readDeadlineList(deadlinesList);
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
            String[] arr = task.getDateTime().split("to");
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
            if (overdueCheck(date) && activity.contains("\u2718")) {
                overdue.add(task);
            } else {
                deadlineViews.add(new DeadlineView(to, description));
            }
        }
        return deadlineViews;
    }

    private ObservableList<DeadlineView> setOverdueTable() throws ParseException {
        String daysDue;
        String description;
        String activity;
        ObservableList<DeadlineView> overdueViews = FXCollections.observableArrayList();
        for (Task task : overdue) {
            activity = task.toString();
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            Date date = dateFormat.parse(activity.substring(activity.indexOf("by:") + 4, activity.indexOf(')')));
            daysDue = String.valueOf(daysBetween(date));
            description = task.getDescription();
            overdueViews.add(new DeadlineView(daysDue, description));
        }
        return overdueViews;
    }

     private void openReminderBox() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        for (int i = 0; i < todos.size(); i++) {
            if (todos.get(i).getDescription().contains("(from") && todos.get(i).getDescription().contains("to")) {
                String description = todos.get(i).getDescription();
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

    @FXML
    private void handleUserInput() throws ParseException {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        //todo: handling of the response
        //refresh(input);
    }

    private boolean overdueCheck(Date date) {
        //Solution below adapted from https://stackoverflow.com/questions/23930216/how-to-check-if-the-date-belongs-to-current-week-or-not/23930578#23930578
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SUNDAY);
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);

        Date startOfWeek = c.getTime();
        if (date.before(startOfWeek)) {
            return true;
        } else return false;
    }

    private long daysBetween(Date date) {
        Date currentDate = new Date();
        return (currentDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);
    }

    //Temp file as Add command and storage not yet implemented. By right go to file find week -> find day
    private String[] tempList = {"Week 8 Mon FBC", "Week 8 Mon A", "Week 8 Tue EFG", "Week 8 Wed EFG", "Week 8 Thu EFG", "Week 8 Fri EFG", "Week 8 Sat EFG", "Week 8 Sun EFG"};
    private String week = "Week 8";

    private final ObservableList<String> monList = FXCollections.observableArrayList();
    private final ObservableList<String> tueList = FXCollections.observableArrayList();
    private final ObservableList<String> wedList = FXCollections.observableArrayList();
    private final ObservableList<String> thuList = FXCollections.observableArrayList();
    private final ObservableList<String> friList = FXCollections.observableArrayList();
    private final ObservableList<String> satList = FXCollections.observableArrayList();
    private final ObservableList<String> sunList = FXCollections.observableArrayList();

    /**
     * This method generates data in day GridPane ListViews based on the week selected
     */
    private void setListItem(){
        for(String item: tempList){ //update (tempList) when actually list is implemented
            if(item.startsWith(week)){
                item = item.replaceFirst(week, "");
                item = item.trim();
                String[] splitItem = item.split(" ", 2);
                System.out.println(splitItem[1]);
                switch (splitItem[0]){
                    case "Mon":
                        monList.add(splitItem[1]);
                        break;
                    case  "Tue":
                        tueList.add(splitItem[1]);
                        break;
                    case "Wed":
                        wedList.add(splitItem[1]);
                        break;
                    case "Thu":
                        thuList.add(splitItem[1]);
                        break;
                    case "Fri":
                        friList.add(splitItem[1]);
                        break;
                    case "Sat":
                        satList.add(splitItem[1]);
                        break;
                    case "Sun":
                        sunList.add(splitItem[1]);
                        break;
                }
            }
        }
        monEventView.setItems(monList.sorted());
        tueEventView.setItems(tueList.sorted());
        wedEventView.setItems(wedList.sorted());
        thuEventView.setItems(thuList.sorted());
        friEventView.setItems(friList.sorted());
        satEventView.setItems(satList.sorted());
        sunEventView.setItems(sunList.sorted());
    }

    /**
     * This method updates currentWeek Label
     * @param selectedWeek The week selected.
     */
    private void setWeek(String selectedWeek){
        //if start up selectedWeek will be NO_FIELD, else if user search for week, week equals selected week
        if(selectedWeek == NO_FIELD){
            Date dateTime = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = dateFormat.format(dateTime);
            //Search look up table for week of current date
            selectedWeek = "current week"; //todo after look up table is up and set selected week = week + date to date
            currentWeek.setText(selectedWeek + " ( " + " date to date" + " )");
            //week = selectedWeek;
        }
        else{
            //search look up table for week selected
            currentWeek.setText(selectedWeek + " ( " + " date to date" + " )"); //todo after look up table is up and set selectedweek = week + date to date
        }
    }

    /**
     * This method refreshes the GridPane ListView after user Adds an item
     * @param input The user input from Command Line
     * @throws ParseException
     */
    private void refresh(String input) throws ParseException { // boolean onAdd,boolean onWeek. if onAdd = 1 it's a add command, if onWeek = 1 it's a week command
        //Assume input to be implement:
        // Week label format: Week 8 ( 07/10/2019 - 11/10/2019 )
        //deadline format - add-d modulecode description date time
        //event format - add-e modulecode description date(07/10/2019) from time to time )
        DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
        String[] spiltWeekLabel = (currentWeek.getText()).split(" ");
        String[] splitInput = input.split(" ");
        int indices = splitInput.length;
        if(input.startsWith("add-e")) {
            Date inputDate = dateFormat.parse(splitInput[(indices-1)-4]);
            Date startDate = dateFormat.parse(spiltWeekLabel[3]);
            Date endDate = dateFormat.parse(spiltWeekLabel[5]);

            if(inputDate.after(startDate) && inputDate.before(endDate)){
                String day = (splitInput[(indices-1)-4]).substring(0,2);
                switch (day){
                    case "Mon":
                        monList.add((splitInput[2] + " " + splitInput[3]));
                        monEventView.setItems(monList.sorted());
                        break;
                    case  "Tue":
                        tueList.add((splitInput[2] + " " + splitInput[3]));
                        tueEventView.setItems(tueList.sorted());
                        break;
                    case "Wed":
                        wedList.add((splitInput[2] + " " + splitInput[3]));
                        wedEventView.setItems(wedList.sorted());
                        break;
                    case "Thu":
                        thuList.add((splitInput[2] + " " + splitInput[3]));
                        thuEventView.setItems(thuList.sorted());
                        break;
                    case "Fri":
                        friList.add((splitInput[2] + " " + splitInput[3]));
                        friEventView.setItems(friList.sorted());
                        break;
                    case "Sat":
                        satList.add((splitInput[2] + " " + splitInput[3]));
                        satEventView.setItems(satList.sorted());
                        break;
                    case "Sun":
                        sunList.add((splitInput[2] + " " + splitInput[3]));
                        sunEventView.setItems(sunList.sorted());
                        break;
                }
            }
        } else if(input.startsWith("add-d")){
            deadlineTable.setItems(setDeadlineTable());
        }
    }
}