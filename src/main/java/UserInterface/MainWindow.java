package UserInterface;

import Commands.ShowPreviousCommand;
import Commands.WeekCommand;
import Commands.UpdateProgressIndicatorCommand;
import Commons.*;
import DukeExceptions.DukeIOException;
import DukeExceptions.DukeInvalidFormatException;
import Parser.WeekParse;
import Tasks.Assignment;
import Tasks.Deadline;
import Tasks.TaskList;
import javafx.animation.PauseTransition;
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

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    private TableColumn<DeadlineView, String> overdueDaysColumn;
    @FXML
    private TableView<DukeResponseView> dukeResponseTable;
    @FXML
    private TableColumn<DukeResponseView, String> dukeResponseColumn;

    private Duke duke;
    private Storage storage;
    private ArrayList<Assignment> events;
    private ArrayList<Assignment> deadlines;
    private ArrayList<Assignment> todos;
    private ArrayList<Assignment> overdue;
    private TaskList eventsList;
    private TaskList deadlinesList;
    public static ArrayList<String> outputList = new ArrayList<>();
    private static WeekList outputWeekList = new WeekList();
    private final Logger LOGGER = DukeLogger.getLogger(MainWindow.class);
    private static LookupTable lookupTable = LookupTable.getInstance();


    /**
     * This method initializes the display in the window of the GUI.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            events = new ArrayList<>();
            todos = new ArrayList<>();
            deadlines = new ArrayList<>();
            setWeek(true, NO_FIELD);
            displayQuoteOfTheDay();

            retrieveList();
            openReminderBox();

            setDeadlineTableContents();
            setProgressContainer();
        } catch (NullPointerException | IOException | ParseException e) {
            LOGGER.severe("Unable to initialise main window GUI." + e.getMessage());
        }
    }

    private void displayQuoteOfTheDay(){
        try {
            ArrayList<String> listOfQuotes = new ArrayList<>();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("documents/quotes.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer sb = new StringBuffer();
            String firstLine;
            String line;
            while ((line = bufferedReader.readLine()) != null){
                listOfQuotes.add(line);
            }
            Random random = new Random();
            int result = random.nextInt(68);
            firstLine = listOfQuotes.get(result);
            AlertBox.display("Quote of the day", "Quote of the day !!", firstLine, Alert.AlertType.INFORMATION);
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            LOGGER.severe("quotes.txt not found. Unable to load quote of the day." + e.getMessage());
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
                LOGGER.severe("ProgressIndicator.fxml not found." + e.getMessage());
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

    static ArrayList<String> filteredInput = new ArrayList<>();

    /**
     * Pulls the list from storage data and stores here.
     * @throws IOException On input error reading lines in the file
     * @throws ParseException On conversion error from string to Task object
     */
    private void retrieveList() throws DukeIOException {
        storage = new Storage();
        eventsList = new TaskList();
        deadlinesList = new TaskList();
        overdue = new ArrayList<>();
        storage.readEventList(eventsList);
        storage.readDeadlineList(deadlinesList);
        events = eventsList.getList();
        deadlines = deadlinesList.getList();
    }

    private ObservableList<DukeResponseView> betterDukeResponse = FXCollections.observableArrayList();

    private void setDeadlineTableContents() throws ParseException {
        overdueDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        overdueTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        overdueDaysColumn.setCellValueFactory(new PropertyValueFactory<>("overDays"));
        overdueTable.setItems(setDeadlineTable());
    }

    private ObservableList<DeadlineView> setDeadlineTable() throws ParseException {
        String dateTime;
        String modCodeAndTask;
        String overDays;
        boolean status;

        ObservableList<DeadlineView> deadlineViews = FXCollections.observableArrayList();
        for(Assignment assignment: deadlines) {
            status = assignment.getStatus();

            modCodeAndTask = assignment.getModCode() + "\n" + assignment.getDescription();
            dateTime = assignment.getDateTime();
            if(status == true) {
                overDays = "-";
                continue;
            } else {
                DateFormat timeFormat= new SimpleDateFormat("E dd/MM/yyyy HH:mm a");
                Date taskDateTime = timeFormat.parse(dateTime);
                overDays = String.valueOf(daysBetween(taskDateTime));
                Integer daysToOrPast = Integer.parseInt(overDays);
                if(daysToOrPast <= 0) overDays = "-";
            }
            Text textModCodeAndTask = new Text(modCodeAndTask);
            textModCodeAndTask.setWrappingWidth(overdueTaskColumn.getWidth()-5);
            Text textDateTime = new Text(dateTime);
            textDateTime.setWrappingWidth(overdueDateColumn.getWidth()-5);
            Text textOverDays = new Text(overDays);
            textOverDays.setWrappingWidth(overdueDaysColumn.getWidth()-5);
            deadlineViews.add(new DeadlineView(textModCodeAndTask, textDateTime, textOverDays));
        }
        return deadlineViews;
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

    private void setDukeResponse() {
        dukeResponseTable.getColumns().clear();
//        TableColumn dukeIndexColumn = new TableColumn<>();
//        dukeIndexColumn.setText("Index");
//        dukeIndexColumn.setMinWidth(35);
//        dukeIndexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        dukeResponseColumn = new TableColumn<>();
        dukeResponseColumn.setText("Duke Response");
        dukeResponseColumn.setSortable(false);
        /*dukeResponseColumn.setCellFactory((param) -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                if (item != null) {
                    setText(item);
                    if (dukeResponseTable.getSelectionModel().getFocusedIndex() == getTableRow().getIndex()) {
                        setStyle("-fx-background-color: red;");
                    } else {
                        setStyle(null);
                    }
                }
                super.updateItem(item, empty);
            }
        });*/
        dukeResponseColumn.setCellValueFactory(new PropertyValueFactory("response"));
        dukeResponseTable.setItems(betterDukeResponse);
        dukeResponseTable.getColumns().add(dukeResponseColumn);
        dukeResponseTable.scrollTo(betterDukeResponse.size()-1);
        dukeResponseTable.getSelectionModel().select(betterDukeResponse.size()-1);
        dukeResponseTable.getSelectionModel().getFocusedIndex();
    }

    @FXML
    private void handleUserInput() throws IOException, DukeInvalidFormatException, ParseException {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        if(input.startsWith("Week")) {
            if(WeekParse.isValid(input)) {
                week = input;
                // //if Week Recess need make till Recess Week normal case: Week 7
                setWeek(false, WeekParse.getWeek(input));
            }
        }
        retrieveList();
        //if(week.contains(" Week")) week = WeekParse.invertWeek(week);
        duke.getResponse(week);
        outputWeekList = WeekCommand.getWeekList();
        updateListView();

        outputList = ShowPreviousCommand.getOutputList();


        overdueTable.getColumns().clear();
        overdueDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        overdueTaskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
        overdueDaysColumn.setCellValueFactory(new PropertyValueFactory<>("overDays"));
        overdueTable.getColumns().addAll(overdueTaskColumn,overdueDateColumn, overdueDaysColumn);
        overdueTable.setItems(setDeadlineTable());


        //add/d CS1000 mod /by 01/11/2019 1500
        setProgressContainer();
        if(!response.isEmpty()) {
            Text temp = new Text(response);
            temp.setWrappingWidth(dukeResponseColumn.getWidth() - 20);
            Integer index = betterDukeResponse.size() + 1;
            betterDukeResponse.add(new DukeResponseView(index.toString(), temp));
            setDukeResponse();
        }

        if (userInput.getText().equals("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished( event -> Platform.exit() );
            delay.play();
        }
        userInput.clear();

        if (input.contains("retrieve/previous")) {
            String previousInput = Duke.getPreviousInput();
            userInput.setText(previousInput);
        } else if (input.startsWith("retrieve/ft ")) {
            String selectedOption = Duke.getSelectedOption();
            userInput.setText(selectedOption);
        }
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
            selectedWeek = lookupTable.getValue(date);
            currentWeek.setText(selectedWeek + " ( " + lookupTable.getValue(selectedWeek.toLowerCase()) + " )");
            week = selectedWeek;
            currentWeek.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC,30));
            currentWeek.setTextFill(Color.GOLDENROD);
        }
        else{
            currentWeek.setText(selectedWeek + " ( " + lookupTable.getValue(selectedWeek.toLowerCase()) + " )");
            //week = selectedWeek;
        }
    }

    private void updateListView(){
        monEventView.setItems(outputWeekList.getMonList());
        tueEventView.setItems(outputWeekList.getTueList());
        wedEventView.setItems(outputWeekList.getWedList());
        thuEventView.setItems(outputWeekList.getThuList());
        friEventView.setItems(outputWeekList.getFriList());
        satEventView.setItems(outputWeekList.getSatList());
        sunEventView.setItems(outputWeekList.getSunList());
    }
}