package JavaFx;
import Interface.*;
import Tasks.Task;
import Tasks.TaskList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private HBox progressContainer;
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
    private static LookupTable LT;
    static {
        try {
            LT = new LookupTable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    int number_of_modules;
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
            setWeek(true, NO_FIELD);

            retrieveList();
            openReminderBox();

            deadlineDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            deadlineTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
            deadlineTable.setItems(setDeadlineTable());

            overdueDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            overdueTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
            overdueTable.setItems(setOverdueTable());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ProgressIndicator.fxml"));
            fxmlLoader.load();
            Pair<HashMap<String, String>, ArrayList<Pair<String, Pair<String, String>>>> result= fxmlLoader.<ProgressController>getController().getProgressIndicatorMap(eventsList.getMap(), deadlinesList.getMap());
            number_of_modules = result.getKey().keySet().size();
            //System.out.println("Number of times: " + (String.valueOf(number_of_modules)));

            HashMap<String, String> modules = result.getKey();
            int totalNumTasks = 0;
            int completedValue = 0;
            for (String module : modules.keySet()) {
                ArrayList<Pair<String, Pair<String, String>>> tasks = result.getValue();
                //totalNumTasks = tasks.size();
                for (Pair<String, Pair<String, String>> as : tasks) {
                    if (as.getKey().equals(module)) {
                        totalNumTasks += 1;
                        if (as.getValue().getKey().equals("\u2713")) {
                            completedValue += 1;
                        }
                    }
                }
                FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/view/ProgressIndicator.fxml"));
                Parent loads = fxmlLoad.load();
                fxmlLoad.<ProgressController>getController().getData(module, totalNumTasks, completedValue);
                progressContainer.getChildren().add(loads);
            }

            setListView();
        } catch (IOException | NullPointerException | ParseException e) {
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
        if (input.startsWith("Week")) {
            setWeek(false, input);
            setListView();
        } else if (input.startsWith("add")) {
            if(response.startsWith("true|"))
            refresh(input);
        } else if (input.startsWith("delete/e" ) || input.startsWith("done/e")) {
            String[] split = input.split("/at");
            String[] dateAndTime = split[1].split("from");
            String date = dateAndTime[0].trim();
            if (date.startsWith("Week")) {
                String[] dateSplit = date.split(" ");
                date = dateSplit[0] + " " + dateSplit[1];
            } else {
                date = LT.getWeek(date);
            }
            if (date.equals(week)) setWeek(false, week);

        }else if (userInput.getText().equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished( event -> Platform.exit() );
            delay.play();
        }
        AlertBox.display("", "",
                response, Alert.AlertType.INFORMATION);
        userInput.clear();
    }

    private boolean overdueCheck(Date date) {
        Calendar c = Calendar.getInstance();
        Date startOfWeek = c.getTime();
        if (date.before(startOfWeek)) {
            return true;
        } else return false;
    }

    private long daysBetween(Date date) {
        Date currentDate = new Date();
        return (currentDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);
    }

    private String week = NO_FIELD;
    private final ObservableList<Text> monList = FXCollections.observableArrayList();
    private final ObservableList<Text> tueList = FXCollections.observableArrayList();
    private final ObservableList<Text> wedList = FXCollections.observableArrayList();
    private final ObservableList<Text> thuList = FXCollections.observableArrayList();
    private final ObservableList<Text> friList = FXCollections.observableArrayList();
    private final ObservableList<Text> satList = FXCollections.observableArrayList();
    private final ObservableList<Text> sunList = FXCollections.observableArrayList();

    /**
     * This method clears the data in GridPane ListViews.
     */
    private void clearData(){
        monList.clear();
        tueList.clear();
        wedList.clear();
        thuList.clear();
        friList.clear();
        satList.clear();
        sunList.clear();
    }

    /**
     * This method creates a comparator for a 12 hour time to be sorted by timeline.
     * @param lhs First item compared
     * @param rhs Second item compared
     * @return The result of the comparison
     */
    private static int compareByTime(Text lhs, Text rhs) {
        String left = lhs.getText().replaceFirst("Start: ", "");
        String[] leftSplit = left.split("\n",2);
        String[] leftTimeSplit = leftSplit[0].split(" ");
        String right = rhs.getText().replaceFirst("Start: ", "");
        String[] rightSplit = right.split("\n",2);
        String[] rightTimeSplit = rightSplit[0].split(" ");

        if(leftTimeSplit[1].equals("AM") && rightTimeSplit[1].equals("AM")){
            String[]leftTimeSplitHourMinute = leftTimeSplit[0].split(":");
            String[]rightTimeSplitHourMinute = rightTimeSplit[0].split(":");
            if(leftTimeSplitHourMinute[0].equals("12") && rightTimeSplitHourMinute[0].equals("12")) {
                return leftTimeSplitHourMinute[1].compareTo(rightTimeSplitHourMinute[1]);
            } else if(leftTimeSplitHourMinute[0].equals("12")) {
                //return left
                return -1;
            } else if (rightTimeSplitHourMinute[0].equals("12")) {
                //return right
                return 1;
            } else {
                return leftTimeSplit[0].compareTo(rightTimeSplit[0]);
            }
        } else if (leftTimeSplit[1].equals("AM")) {
            //return left
            return -1;
        } else if (rightTimeSplit[1].equals("AM")) {
            //return right
            return 1;
        } else {
            //return left
            return leftSplit[0].compareTo(rightSplit[0]);
        }
    }

    /**
     * This method generates data in day GridPane ListViews based on the week selected
     */
    private void setListView() {
        clearData();
        for(Map.Entry<String, HashMap<String, ArrayList<Task>>> module: eventsList.getMap().entrySet()) {
            HashMap<String, ArrayList<Task>> moduleValue = module.getValue();
            for(Map.Entry<String, ArrayList<Task>> item: moduleValue.entrySet()) {
                String strDate = item.getKey();
                String[] spilt = strDate.split(" ", 3);
                String selectedWeek = LT.getWeek(spilt[1]);
                if((selectedWeek).equals(week)) {
                    ArrayList<Task> data = item.getValue(); // each item in data has the contents
                    for(Task task: data){
                        //boolean isTick = task.isDone;
                        Text toShow = new Text(task.toShow() + task.getModCode() + "\n" + task.getDescription());
                        toShow.setFont(Font.font(10));
                        if (task.getIsDone()){
                            toShow.setFill(Color.GAINSBORO);
                            toShow.setStrikethrough(true);
                        }
                        toShow.wrappingWidthProperty().bind(monEventView.widthProperty().subtract(20));
                        String day = spilt[0];
                        switch (day){
                            case "Mon":
                                monList.add(toShow);
                                break;
                            case  "Tue":
                                tueList.add(toShow);
                                break;
                            case "Wed":
                                wedList.add(toShow);
                                break;
                            case "Thu":
                                thuList.add(toShow);
                                break;
                            case "Fri":
                                friList.add(toShow);
                                break;
                            case "Sat":
                                satList.add(toShow);
                                break;
                            case "Sun":
                                sunList.add(toShow);
                                break;
                        }
                    }
                }
            }
        }
        monEventView.setItems(monList.sorted(MainWindow::compareByTime));
        tueEventView.setItems(tueList.sorted(MainWindow::compareByTime));
        wedEventView.setItems(wedList.sorted(MainWindow::compareByTime));
        thuEventView.setItems(thuList.sorted(MainWindow::compareByTime));
        friEventView.setItems(friList.sorted(MainWindow::compareByTime));
        satEventView.setItems(satList.sorted(MainWindow::compareByTime));
        sunEventView.setItems(sunList.sorted(MainWindow::compareByTime));
    }

    /**
     * This method updates currentWeek Label.
     * @param onStart The flag which indicates program startup
     * @param selectedWeek The week selected
     */
    private void setWeek(Boolean onStart,String selectedWeek){
        if(onStart){
            Date dateTime = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String date = dateFormat.format(dateTime);
            selectedWeek = LT.getWeek(date);
            currentWeek.setText(selectedWeek + " ( " + LT.getDates(selectedWeek.toLowerCase()) + " )");
            week = selectedWeek;
            currentWeek.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC,23));
            currentWeek.setTextFill(Color.GOLDENROD);
            //currentWeek.setUnderline(true);
            //currentWeek.setDisable(true);
        }
        else{
            currentWeek.setText(selectedWeek + " ( " + LT.getDates(selectedWeek.toLowerCase()) + " )");
            week = selectedWeek;
        }
    }

    /**
     * This method refreshes the GridPane ListView after user Adds an item
     * @param input The user input from Command Line
     * @throws ParseException The exception when that is error with the date given
     */
    private void refresh(String input) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateDayFormat = new SimpleDateFormat("E dd/MM/yyyy");
        DateFormat timeFormat_24 = new SimpleDateFormat("HHmm");
        DateFormat timeFormat_12 = new SimpleDateFormat("hh:mm a");
        String[] spiltWeekLabel = (currentWeek.getText()).split(" ");
        Date startDate = dateFormat.parse(spiltWeekLabel[3]);
        Date endDate = dateFormat.parse(spiltWeekLabel[5]);

        if (input.startsWith("add/e")) {
            String[] spiltInput = input.split(" /at ");
            String[] modAndTask = (spiltInput[0].replaceFirst("add/e ", "")).split(" ");
            String[] dateAndTime = spiltInput[1].split(" from ");
            String date = dateAndTime[0].trim();
            if(date.startsWith("Week")) date = LT.getDates(date.toLowerCase());
            Date inputDate = dateFormat.parse(date);
            Date currentDate = dateFormat.parse(dateFormat.format(new Date()));
            if(inputDate.before(currentDate)) return;
            String[] startAndEndTime = dateAndTime[1].split(" to ");

            if (inputDate.after(startDate) && inputDate.before(endDate)) {
                String day = (dateDayFormat.format(inputDate)).substring(0,3);
                Date startTime = timeFormat_24.parse(startAndEndTime[0]);
                Date endTime = timeFormat_24.parse(startAndEndTime[1]);
                Text toShow = new Text("Start: " + timeFormat_12.format(startTime) + "\nEnd: " +timeFormat_12.format(endTime) + "\n" + modAndTask[0] + "\n" + modAndTask[1]);
                toShow.wrappingWidthProperty().bind(monEventView.widthProperty().subtract(15));
                toShow.setFont(Font.font(10));
                switch (day){
                    case "Mon":
                        monList.add(toShow);
                        monEventView.setItems(monList.sorted(MainWindow::compareByTime));
                        break;
                    case  "Tue":
                        tueList.add(toShow);
                        tueEventView.setItems(tueList.sorted(MainWindow::compareByTime));
                        break;
                    case "Wed":
                        wedList.add(toShow);
                        wedEventView.setItems(wedList.sorted(MainWindow::compareByTime));
                        break;
                    case "Thu":
                        thuList.add(toShow);
                        thuEventView.setItems(thuList.sorted(MainWindow::compareByTime));
                        break;
                    case "Fri":
                        friList.add(toShow);
                        friEventView.setItems(friList.sorted(MainWindow::compareByTime));
                        break;
                    case "Sat":
                        satList.add(toShow);
                        satEventView.setItems(satList.sorted(MainWindow::compareByTime));
                        break;
                    case "Sun":
                        sunList.add(toShow);
                        sunEventView.setItems(sunList.sorted(MainWindow::compareByTime));
                        break;
                }
            }
        } else if (input.startsWith("add/d")) {
            deadlineTable.setItems(setDeadlineTable());
        }
    }
}