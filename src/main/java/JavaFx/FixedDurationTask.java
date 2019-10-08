package JavaFx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller for fixed duration tasks. Provides the GUI for user to select best option.
 */
public class FixedDurationTask {
    private static final String NO_FIELD = "void";
    private static final String CROSS = "[\u2718]";
    @FXML
    private ChoiceBox<String> taskTypeChoiceBox;
    @FXML
    private TextField taskDescriptionTextField;
    @FXML
    private ListView<String> periodListView;
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;
    @FXML
    private TextField periodTextField;

    private ArrayList< Pair<Date, Date>> freeTimes = new ArrayList<>();
    private String taskDescription;
    private String taskType;
    private String taskDetails = NO_FIELD;
    private final ObservableList<String> freeTimesList = FXCollections.observableArrayList();

    /**
     * This function gets data from previous window.
     * @param availableTimeSlot Contains all free times found in start and end pairs
     * @param task The task input by user
     * @param type The task type based on the user's input
     */
    public void getData(ArrayList<Pair<Date, Date>> availableTimeSlot, String task, String type){
        freeTimes = availableTimeSlot;
        taskDescription = task;
        taskType = type;
        taskDescriptionTextField.setText(taskDescription);
        populateFreeTimesList();
        if(type.equals("event")){
            taskTypeChoiceBox.getItems().add("[E] Event");
            taskTypeChoiceBox.setValue("[E] Event");
        } else if (type.equals("todo")) {
            taskTypeChoiceBox.getItems().add("[T] Todo");
            taskTypeChoiceBox.setValue("[T] Todo");
        }
    }

    /**
     * This function populates data into FreeTimeList ObservableList.
     */
    private void populateFreeTimesList(){
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("E dd/MM/yyyy hh:mm aa");
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd/MM/yyyy");
        for(Pair<Date, Date> date: freeTimes){
            String startEnd = "";
            if(taskType.equals("event")) startEnd = "Start: " + dateTimeFormat.format(date.getKey()) + "\nEnd: " + dateTimeFormat.format(date.getValue());
            else if(taskType.equals("todo")) startEnd = "Start: " + dateFormat.format(date.getKey()) + "\nEnd: " + dateFormat.format(date.getValue());
            freeTimesList.add(startEnd);
        }
        periodListView.setItems(freeTimesList);
    }

    /**
     * This function returns data processed in current window to previous window.
     * @return The command and message to display to the ChatBot
     */
    public String returnData(){
        return taskDetails;
    }

    /**
     * This function processes the data selected from GUI then generates command and task details.
     * @param taskType The task type
     * @param date The start and end dates
     * @return The processed data command and task details in a pair
     */
    private String sortByTaskType(String taskType, String date) {

        date = date.replace("Start: ", "");
        String[] spiltDate = date.split("End: ");
        String startDate = spiltDate[0];
        String endDate = spiltDate[1];

        String typeTypeCommand = taskType.trim().substring(4).toLowerCase();
        if (taskType.substring(0,3).trim().equals("[T]")){
            taskDetails = taskType.trim().substring(0,3) + CROSS + " " + taskDescription + " " + startDate + " until " + endDate;
        } else if (taskType.substring(0,3).trim().equals("[E]")){
            taskDetails = taskType.trim().substring(0,3) + CROSS + " " + taskDescription + " (at: " + startDate + " until " + endDate + ")";
        }
        return taskDetails;
    }

    /**
     * This function checks if is selected parameter valid, then prompts the user to confirm.
     */
    @FXML
    private void handleAdd() {
        if(!periodTextField.getText().isEmpty()){
            taskDetails = sortByTaskType((taskTypeChoiceBox.getValue()), periodTextField.getText());
            boolean isOk = AlertBox.display("Confirmation Dialog", "Add Task", "Press OK to add task.\nPress Cancel to change your options.", Alert.AlertType.CONFIRMATION);
            Stage stage = (Stage) addButton.getScene().getWindow();
            if (isOk) {
                stage.close();
                AlertBox.display("Notification Dialog", "", "Your task has been added.", Alert.AlertType.INFORMATION);
            }
        } else {
            AlertBox.display("Warning Dialog","Period is empty","Please Select and click a Period from the List.", Alert.AlertType.WARNING);
        }
    }

    /**
     * This function prompts the user to confirm cancel operation.
     */
    @FXML
    private void handleCancel() {
        boolean isCancel =AlertBox.display("Confirmation Dialog","Cancel Task","Press OK to return to ChatBot.\nPress Cancel to return to Hello Better options.", Alert.AlertType.CONFIRMATION);
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        if(isCancel) {
            stage.close();

        }
    }

    /**
     * This function updates periodTextField when mouse clicked on ListView.
     */
    @FXML
    private void updatePeriod (){
        String temp = periodListView.getSelectionModel().getSelectedItem();
        int index = temp.indexOf("End:");
        String period = temp.substring(0,index) + " " + temp.substring(index);
        periodListView.refresh();
        periodListView.setItems(freeTimesList);
        periodTextField.setText(period);
    }
}