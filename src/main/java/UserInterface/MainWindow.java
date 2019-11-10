package UserInterface;

import Commands.RetrievePreviousCommand;
import Commands.ShowPreviousCommand;
import Commands.WeekCommand;
import Commands.UpdateProgressIndicatorCommand;
import Commons.DukeConstants;
import Commons.LookupTable;
import Commons.Duke;
import Commons.DukeLogger;
import Commons.Storage;
import Commons.WeekList;
import DukeExceptions.DukeIOException;
import Parser.RetrieveFreeTimesParse;
import Parser.WeekParse;
import Tasks.Assignment;
import Tasks.TaskList;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.Date;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends BorderPane implements Initializable {
    @FXML
    private Label weekLabel;
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
    private static Storage storage;
    private ArrayList<Assignment> events;
    private ArrayList<Assignment> deadlines;
    private TaskList eventsList;
    private TaskList deadlinesList;
    private static final int HOURS = 24;
    private static final int MINUTES = 60;
    private static final int SECONDS = 60;
    private static final int MILLISECONDS = 1000;
    private static final int TOTAL_NUM_OF_QUOTES = 68;
    public static ArrayList<String> outputList = new ArrayList<>();
    private static WeekList outputWeekList = new WeekList();
    private final Logger LOGGER = DukeLogger.getLogger(MainWindow.class);
    private static LookupTable lookupTable = LookupTable.getInstance();
    private static final String START_WEEK_DELIMITER = " ( ";
    private static final String END_WEEK_DELIMITER = " )";


    /**
     * This method initializes the display in the window of the GUI.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            events = new ArrayList<>();
            deadlines = new ArrayList<>();
            setWeek(true, DukeConstants.NO_FIELD);
            displayQuoteOfTheDay();
            retrieveList();

            setDeadlineTableContents();
            setProgressContainer();
        } catch (NullPointerException | IOException | ParseException e) {
            LOGGER.severe("Unable to initialise main window GUI.");
        }
    }

    private void displayQuoteOfTheDay() {
        try {
            ArrayList<String> listOfQuotes = new ArrayList<>();
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("documents/quotes.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String firstLine;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                listOfQuotes.add(line);
            }
            Random random = new Random();
            int result = random.nextInt(TOTAL_NUM_OF_QUOTES);
            firstLine = listOfQuotes.get(result);
            AlertBox.display("Quote of the day", "Quote of the day !!", firstLine, Alert.AlertType.INFORMATION);
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            LOGGER.severe("quotes.txt not found. Unable to load quote of the day.");
        }
    }


    /**
     * This method creates the progress indicator for the different modules.
     * @throws IOException On reading error in the lines of the file
     */
    private void setProgressContainer() throws IOException {
        progressContainer.getChildren().clear();

        UpdateProgressIndicatorCommand updateProgressIndicatorCommand = new UpdateProgressIndicatorCommand(eventsList,
                deadlinesList);
        Pair<HashMap<String, String>, ArrayList<Pair<String, Pair<String, String>>>> wholeData =
                updateProgressIndicatorCommand.getWholeDate(eventsList, deadlinesList);
        HashMap<String, String> moduleMap = updateProgressIndicatorCommand.getModuleMap(wholeData);

        HashMap<String, Pair<Integer, Integer>> progressIndicatorValues = updateProgressIndicatorCommand.getValues(
                moduleMap, wholeData);
        for (String module : progressIndicatorValues.keySet()) {
            FXMLLoader fxmlLoad = new FXMLLoader(getClass().getResource("/view/ProgressIndicator.fxml"));
            Parent loads = null;
            try {
                loads = fxmlLoad.load();
            } catch (IOException e) {
                LOGGER.severe("ProgressIndicator.fxml not found.");
            }
            int totalNumOfTasks = progressIndicatorValues.get(module).getKey();
            int completedValue = progressIndicatorValues.get(module).getValue();
            fxmlLoad.<ProgressController>getController().getData(module, totalNumOfTasks, completedValue);
            progressContainer.getChildren().add(loads);
        }
    }

    /**
     * Initialize Duke object in MainWindow controller with Duke object from Main.
     */
    public void setDuke(Duke d) {
        duke = d;
    }

    /**
     * Initialize Storage object in MainWindow controller with storage object from Duke.
     */
    public static void setStorage(Storage storageFromDuke) {
        storage = storageFromDuke;
    }

    /**
     * Pulls the list from storage data and stores here.
     * @throws IOException On input error reading lines in the file
     * @throws ParseException On conversion error from string to Task object
     */
    private void retrieveList() throws DukeIOException {
        eventsList = new TaskList();
        deadlinesList = new TaskList();
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
        boolean isDone;

        ObservableList<DeadlineView> deadlineViews = FXCollections.observableArrayList();
        for (Assignment assignment: deadlines) {
            isDone = assignment.getStatus();

            modCodeAndTask = assignment.getModCode() + "\n" + assignment.getDescription();
            dateTime = assignment.getDateTime();
            if (isDone) {
                overDays = "-";
                continue;
            } else {
                Date taskDateTime = DukeConstants.DEADLINE_DATE_FORMAT.parse(dateTime);
                overDays = String.valueOf(daysBetween(taskDateTime));
                int daysToOrPast = Integer.parseInt(overDays);
                if (daysToOrPast <= 0) {
                    overDays = "-";
                }
            }
            Text textModCodeAndTask = new Text(modCodeAndTask);
            textModCodeAndTask.setWrappingWidth(overdueTaskColumn.getWidth() - 5);
            Text textDateTime = new Text(dateTime);
            textDateTime.setWrappingWidth(overdueDateColumn.getWidth() - 5);
            Text textOverDays = new Text(overDays);
            textOverDays.setWrappingWidth(overdueDaysColumn.getWidth() - 5);
            deadlineViews.add(new DeadlineView(textModCodeAndTask, textDateTime, textOverDays));
        }
        return deadlineViews;
    }

    private void setDukeResponse() {
        dukeResponseColumn.setSortable(false);
        dukeResponseColumn.setCellValueFactory(new PropertyValueFactory("response"));
        dukeResponseTable.setItems(betterDukeResponse);
        dukeResponseTable.scrollTo(betterDukeResponse.size() - 1);
        dukeResponseTable.getSelectionModel().select(betterDukeResponse.size() - 1);
        dukeResponseTable.getSelectionModel().getFocusedIndex();
    }

    @FXML
    private void handleUserInput() throws IOException, ParseException {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        if (input.startsWith(DukeConstants.SHOW_WEEK_HEADER)) {
            if (WeekParse.isValid(input)) {
                week = input;
                setWeek(false, WeekParse.getWeek(input));
            }
        }

        retrieveList();
        duke.getResponse(week);
        outputWeekList = WeekCommand.getWeekList();
        updateListView();

        outputList = ShowPreviousCommand.getOutputList();

        overdueTable.setItems(setDeadlineTable());

        setProgressContainer();
        if (!response.isEmpty() && !response.equals(DukeConstants.NO_FIELD)) {
            Text temp = new Text(response);
            temp.setWrappingWidth(dukeResponseColumn.getWidth() - 20);
            int index = betterDukeResponse.size() + 1;
            betterDukeResponse.add(new DukeResponseView(Integer.toString(index), temp));
            setDukeResponse();
        }

        if (userInput.getText().equals(DukeConstants.BYE_HEADER)) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }
        userInput.clear();

        if (!input.contains(DukeConstants.SHOW_PREVIOUS_HEADER)
                && input.contains(DukeConstants.RETRIEVE_PREVIOUS_HEADER) && RetrievePreviousCommand.isValid()) {
            String previousInput = Duke.getPreviousInput();
            userInput.setText(previousInput);
        } else if (input.startsWith(DukeConstants.RETRIEVE_TIME_HEADER)
                && RetrieveFreeTimesParse.isValidOption(input)) {
            String selectedOption = Duke.getSelectedOption();
            userInput.setText(selectedOption);
            userInput.positionCaret(DukeConstants.ADD_EVENT_HEADER.length() + DukeConstants.BLANK_SPACE.length());
        }
    }

    private long daysBetween(Date date) {
        Date currentDate = new Date();
        return (currentDate.getTime() - date.getTime()) / (MILLISECONDS * SECONDS * MINUTES * HOURS);
    }

    private String week = DukeConstants.NO_FIELD;

    /**
     * This method updates weekLabel Label.
     * @param onStart The flag which indicates program startup
     * @param selectedWeek The week selected
     */
    private void setWeek(Boolean onStart,String selectedWeek) {
        if (onStart) {
            Date dateTime = new Date();
            String date = DukeConstants.EVENT_DATE_INPUT_FORMAT.format(dateTime);
            selectedWeek = lookupTable.getValue(date);
            weekLabel.setText(selectedWeek + START_WEEK_DELIMITER + lookupTable.getValue(selectedWeek.toLowerCase())
                    + END_WEEK_DELIMITER);

            week = WeekParse.getWeekCommandFormat(selectedWeek);
            weekLabel.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC,30));
            weekLabel.setTextFill(Color.GOLDENROD);
        } else {
            weekLabel.setText(selectedWeek + START_WEEK_DELIMITER + lookupTable.getValue(selectedWeek.toLowerCase())
                    + END_WEEK_DELIMITER);
        }
    }

    private void updateListView() {
        monEventView.setItems(outputWeekList.getMonList());
        tueEventView.setItems(outputWeekList.getTueList());
        wedEventView.setItems(outputWeekList.getWedList());
        thuEventView.setItems(outputWeekList.getThuList());
        friEventView.setItems(outputWeekList.getFriList());
        satEventView.setItems(outputWeekList.getSatList());
        sunEventView.setItems(outputWeekList.getSunList());
    }
}