package JavaFx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for fixed duration tasks. Provides the GUI for user to select best option.
 */
public class FixedDurationTask {
    private static final String NO_FIELD = new String ("void");
    private static final String CROSS = new String ("[\u2718]");
    @FXML
    private ChoiceBox<String> TaskTypeChoiceBox;
    @FXML
    private TextField TaskDescriptionTextField;
    @FXML
    private ListView<String> AvailablePeriodListView;
    @FXML
    private Button cancelButton;
    @FXML
    private Button AddButton;
    @FXML
    private TextField PeriodTextField;

    private ArrayList< Pair<Date, Date>> freeTimes = new ArrayList< Pair<Date, Date>>();

    private String taskDescription;
    private String command = NO_FIELD;
    private String taskDetails = NO_FIELD;
    private Pair<String, String> commandAndTaskDetails = new Pair<>(NO_FIELD,NO_FIELD);

    ObservableList<String> taskTypeStatusList = FXCollections.observableArrayList("[T] Todo", "[D] Deadline", "[E] Event");

    /**
     * This method initializes the display in the window of the GUI.
     */
    @FXML
    public void initialize() {
        TaskTypeChoiceBox.setValue("[T] Todo");
        TaskTypeChoiceBox.setItems(taskTypeStatusList);
    }

    /**
     * This function gets data from previous window
     * @param availableTimeSlot All free times found in  start and end pairs
     * @param task The task input by user
     */
    public void getDataNeeded(ArrayList<Pair<Date, Date>> availableTimeSlot, String task){
        freeTimes = availableTimeSlot;
        taskDescription = task;
        TaskDescriptionTextField.setText(taskDescription);
        generateTimeSlots();
    }

    /**
     * This function populates data into ListView
     */
    private void generateTimeSlots(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");
        for(int i = 0; i < 5; i ++)
            AvailablePeriodListView.getItems().add("Start: " + dateFormat.format(freeTimes.get(i).getKey()) + "\nEnd: " + dateFormat.format(freeTimes.get(i).getValue()));
    }


    /**
     * This function returns data processed in current window to previous window
     * @return The command and message to display to the ChatBot
     */
    public Pair<String, String> returnData(){
        return commandAndTaskDetails;
    }

    /**
     * This function processes the data selected from GUI then generates command and task details.
     * @param taskType The task type
     * @param date The start and end dates
     * @return The processed data command and task details in a pair
     * @throws ParseException
     */
    public Pair<String, String> sortByTaskType(String taskType, String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");

        date = date.replace("Start: ", "");
        String[] spiltDate = date.split("End: ");
        String command = new String();
        String details = new String();
        String startDate = spiltDate[0];
        String endDate = spiltDate[1];

        if (taskType.substring(0,3).trim().equals("[T]")){
            command = taskType.trim().substring(3).toLowerCase() + " " + taskDescription + " "+ startDate + " until " + endDate;
            details = taskType.trim().substring(0,3) + CROSS + " " + taskDescription + " " + startDate + " until " + endDate;
        } else if (taskType.substring(0,3).trim().equals("[D]")){
            command = taskType.trim().substring(3).toLowerCase()+ " " + taskDescription + " /by " + formatter.format(dateFormat.parse(startDate));
            details = taskType.trim().substring(0,3) + CROSS + " " + taskDescription + " (by: " + startDate + " until " + endDate + ")";
        } else if (taskType.substring(0,3).trim().equals("[E]")){
            command = taskType.trim().substring(3).toLowerCase() + " " + taskDescription + " /at " + formatter.format(dateFormat.parse(startDate));
            details = taskType.trim().substring(0,3) + CROSS + " " + taskDescription + " (at: " + startDate + " until " + endDate + ")";
        }
        return new Pair<String, String>(command, details);
    }

    /**
     * This function checks if is selected parameter valid, then prompts the user to confirm
     */
    @FXML
    private void handleAdd() throws ParseException {
        if(!PeriodTextField.getText().isEmpty()){
            commandAndTaskDetails = sortByTaskType((TaskTypeChoiceBox.getValue().toString()), PeriodTextField.getText());
            boolean isOk = AlertBox.display("Confirmation Dialog", "Add Task", "Press OK to add task.\nPress Cancel to change your options.", Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) AddButton.getScene().getWindow();
            if (isOk) {
                stage.close();
                AlertBox.display("Notification Dialog", "", "Your task has been added.", Alert.AlertType.INFORMATION);
            }
        } else {
            AlertBox.display("Warning Dialog","Period is empty","Please Select and click a Period from the List.", Alert.AlertType.WARNING);
        }
    }

    /**
     * This function prompts the user to confirm cancel operation
     */
    @FXML
    private void handleCancel() {
        boolean isCancel =AlertBox.display("Confirmation Dialog","Cancel Task","Press OK to return to ChatBot.\nPress Cancel to return to Hello Better options.", Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if(isCancel) stage.close();
    }

    /**
     * This function updates PeriodTextField when mouse clicked on ListView
     */
    @FXML
    private void updatePeriod (){
        PeriodTextField.setText(AvailablePeriodListView.getSelectionModel().getSelectedItem());
    }
}