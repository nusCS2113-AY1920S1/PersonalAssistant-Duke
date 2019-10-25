package JavaFx;
import Commands.UpdateProgressIndicatorCommand;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends BorderPane implements Initializable {
    private static final String NO_FIELD = "void";
    @FXML
    private Text currentTime;
    @FXML
    private Label currentWeek;
    @FXML
    private TextField userInput;
    @FXML
    private HBox progressContainer;
    @FXML
    private ListView<Text> sunEventView;
    @FXML
    private ListView<Text> monEventView;
    @FXML
    private ListView<Text> tueEventView;
    @FXML
    private ListView<Text> wedEventView;
    @FXML
    private ListView<Text> thuEventView;
    @FXML
    private ListView<Text> friEventView;
    @FXML
    private ListView<Text> satEventView;
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
    private static final Logger LOGGER = Logger.getLogger(MainWindow.class.getName());
    static {
        try {
            LT = new LookupTable();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    protected int number_of_modules;

    /**
     * This method initializes the display in the window of the GUI.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            events = new ArrayList<>();
            todos = new ArrayList<>();
            deadlines = new ArrayList<>();
           // setClock();
            setWeek(true, NO_FIELD);
            //displayQuoteOfTheDay();

            retrieveList();
            openReminderBox();

            deadlineDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            deadlineTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
            deadlineTable.setItems(setDeadlineTable());

            overdueDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            overdueTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
            overdueTable.setItems(setOverdueTable());
            //seeList();
            setProgressContainer();
            setListView();
        } catch (NullPointerException | IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private void displayQuoteOfTheDay(){
        try {
            File path = new File(System.getProperty("user.dir") + "\\data\\quotes.txt");
            Scanner scanner = new Scanner(path);
            String firstLine = scanner.nextLine();
            FileWriter writer = new FileWriter(path);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != firstLine)
                    writer.write(line + "\n");
            }
            writer.write(firstLine+"\n");
            AlertBox.display("Quote of the day", "Quote of the day !!", firstLine, Alert.AlertType.INFORMATION);

            scanner.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method creates the progress indicator for the different modules.
     * @throws IOException On reading error in the lines of the file
     */
    private void setProgressContainer() throws IOException {
        progressContainer.getChildren().clear();

        UpdateProgressIndicatorCommand updateProgressIndicatorCommand = new UpdateProgressIndicatorCommand(eventsList, deadlinesList);
        Pair<HashMap<String, String>, ArrayList<Pair<String, Pair<String, String>>>> wholeData = updateProgressIndicatorCommand.getWholeDate(eventsList, deadlinesList);
        HashMap<String, String> moduleMap = updateProgressIndicatorCommand.getModuleMap(wholeData);

        HashMap<String, Pair<Integer, Integer>> progressIndicatorValues = updateProgressIndicatorCommand.getValues(moduleMap, wholeData);
        for (String module : progressIndicatorValues.keySet()) {
            FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/view/ProgressIndicator.fxml"));
            Parent loads = null;
            try {
                loads = fxmlLoad.load();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
            int totalNumOfTasks = progressIndicatorValues.get(module).getKey();
            int completedValue = progressIndicatorValues.get(module).getValue();
            fxmlLoad.<ProgressController>getController().getData(module, totalNumOfTasks, completedValue);
            progressContainer.getChildren().add(loads);
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
//        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM/yyyy HH:mm:ss");
//            currentTime.setText(LocalDateTime.now().format(formatter));
//        }), new KeyFrame(Duration.seconds(1)));
//        clock.setCycleCount(Animation.INDEFINITE);
//        clock.play();
    }

    /**
     * Pulls the list from storage data and stores here.
     * @throws IOException On input error reading lines in the file
     * @throws ParseException On conversion error from string to Task object
     */
    private void retrieveList() {
        storage = new Storage();
        eventsList = new TaskList();
        deadlinesList = new TaskList();
        overdue = new ArrayList<>();
        storage.readEventList(eventsList);
        storage.readDeadlineList(deadlinesList);
        events = eventsList.getList();
        deadlines = deadlinesList.getList();
    }

    private ObservableList<DeadlineView> setDeadlineTable()  {
        String to;
        String description;
        String activity;
        ObservableList<DeadlineView> deadlineViews = FXCollections.observableArrayList();
        for (Task task : deadlines) {
            activity = task.toString();
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            DateFormat timeFormat= new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = null;
            try {
                date = dateFormat.parse(activity.substring(activity.indexOf("by:") + 4, activity.indexOf(')')));
            } catch (ParseException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
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

    private ObservableList<DeadlineView> setOverdueTable() {
        String daysDue;
        String description;
        String activity;
        ObservableList<DeadlineView> overdueViews = FXCollections.observableArrayList();
        for (Task task : overdue) {
            activity = task.toString();
            DateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm a");
            Date date = null;
            try {
                date = dateFormat.parse(activity.substring(activity.indexOf("by:") + 4, activity.indexOf(')')));
            } catch (ParseException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
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
    private void handleUserInput() throws IOException{
        String input = userInput.getText();
        String response = duke.getResponse(input);
        retrieveList();
        if (input.startsWith("Week")) {
            setWeek(false, input);
            setListView();
        } else if (input.startsWith("add")) {
            //if(response.startsWith("true|")) {
            //refresh(input);
            //setWeek(false, input);
            setListView();
            deadlineTable.setItems(setDeadlineTable());
            setProgressContainer();
            //}
        } else if (input.startsWith("delete/e" ) || input.startsWith("done/e")) {
            String[] split = input.split("/at");
            String[] dateAndTime = split[1].split("from");
            String date = dateAndTime[0].trim();
            if (date.startsWith("Week")) {
                String[] dateSplit = date.split(" ");
                date = dateSplit[0] + " " + dateSplit[1];
            } else {
                date = LT.getValue(date);
            }
            if (date.equals(week)) setWeek(false, week);

        }else if (userInput.getText().equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished( event -> Platform.exit() );
            delay.play();
        }
        if (!input.startsWith("Week")) {
            AlertBox.display("", "",
                    response, Alert.AlertType.INFORMATION);
        }
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
                return -1;
            } else if (rightTimeSplitHourMinute[0].equals("12")) {
                return 1;
            } else {
                return leftTimeSplit[0].compareTo(rightTimeSplit[0]);
            }
        } else if (leftTimeSplit[1].equals("AM")) {
            return -1;
        } else if (rightTimeSplit[1].equals("AM")) {
            return 1;
        } else {
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
                String selectedWeek = LT.getValue(spilt[1]);
                if((selectedWeek).equals(week)) {
                    ArrayList<Task> data = item.getValue(); // each item in data has the contents
                    for(Task task: data){
                        //boolean isTick = task.isDone;
                        Text toShow = new Text(task.toShow() + task.getModCode() + "\n" + task.getDescription());
                        toShow.setFont(Font.font(10));
                        if (task.getStatus()){
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
        if(monList.size() != 0 ) monEventView.setItems(monList.sorted(MainWindow::compareByTime));
        if(tueList.size() != 0 ) tueEventView.setItems(tueList.sorted(MainWindow::compareByTime));
        if(wedList.size() != 0 ) wedEventView.setItems(wedList.sorted(MainWindow::compareByTime));
        if(thuList.size() != 0 ) thuEventView.setItems(thuList.sorted(MainWindow::compareByTime));
        if(friList.size() != 0 ) friEventView.setItems(friList.sorted(MainWindow::compareByTime));
        if(satList.size() != 0 ) satEventView.setItems(satList.sorted(MainWindow::compareByTime));
        if(sunList.size() != 0 ) sunEventView.setItems(sunList.sorted(MainWindow::compareByTime));
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
            selectedWeek = LT.getValue(date);
            currentWeek.setText(selectedWeek + " ( " + LT.getValue(selectedWeek.toLowerCase()) + " )");
            week = selectedWeek;
            currentWeek.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC,23));
            currentWeek.setTextFill(Color.GOLDENROD);
        }
        else{
            currentWeek.setText(selectedWeek + " ( " + LT.getValue(selectedWeek.toLowerCase()) + " )");
            week = selectedWeek;
        }
    }

    /**
     * This method refreshes the GridPane ListView after user Adds an item
     * @param input The user input from Command Line
     * @throws ParseException The exception when that is error with the date given
     */
    private void refresh(String input) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat dateDayFormat = new SimpleDateFormat("E dd/MM/yyyy");
        DateFormat timeFormat_24 = new SimpleDateFormat("HHmm");
        DateFormat timeFormat_12 = new SimpleDateFormat("hh:mm a");
        String[] spiltWeekLabel = (currentWeek.getText()).split(" ");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = dateFormat.parse(spiltWeekLabel[3]);
            endDate = dateFormat.parse(spiltWeekLabel[5]);
        } catch (ParseException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        if (input.startsWith("add/e")) {
            String[] spiltInput = input.split(" /at ");
            String[] modAndTask = (spiltInput[0].replaceFirst("add/e ", "")).split(" ");
            String[] dateAndTime = spiltInput[1].split(" from ");
            String date = dateAndTime[0].trim();
            if(date.startsWith("Week")) date = LT.getValue(date.toLowerCase());
            Date inputDate = null;
            Date currentDate = null;
            try {
                currentDate = dateFormat.parse(dateFormat.format(new Date()));
                inputDate = dateFormat.parse(date);
            } catch (ParseException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
            if(inputDate.before(currentDate)) return;
            String[] startAndEndTime = dateAndTime[1].split(" to ");

            if (inputDate.after(startDate) && inputDate.before(endDate)) {
                String day = (dateDayFormat.format(inputDate)).substring(0,3);
                Date startTime = null;
                Date endTime = null;
                try {
                    endTime = timeFormat_24.parse(startAndEndTime[1]);
                    startTime = timeFormat_24.parse(startAndEndTime[0]);
                } catch (ParseException e) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
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