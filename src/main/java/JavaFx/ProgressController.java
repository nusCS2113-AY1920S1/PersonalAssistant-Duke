package JavaFx;

import Tasks.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Controller for progress indicator. This control represents the progress indicator for a module.
 */
public class ProgressController extends HBox {
    @FXML
    private Label moduleCodeLabel;
    @FXML
    private Label completedValueLabel;
    @FXML
    private Label overdueValueLabel = new Label();
    @FXML
    private ProgressIndicator progressIndicator;

    private final ArrayList<Pair<String, Pair<String, String>>> moduleCodeStatusIconDescriptionArrList = new ArrayList<>();
    private final HashMap<String, String> moduleCodeMap = new HashMap<>();

    /**
     * This function gets the arraylist containing tasks in terms of Pair(module code (status icon, description)) and determine the modules taken by user.
     * @param eventsList the list containing the event tasks
     * @param deadlineList the list containing the deadline tasks
     * @return a pair containing the arraylist of tasks and hashmap of module code
     */
    public Pair<HashMap<String, String>, ArrayList<Pair<String, Pair<String, String>>>> getProgressIndicatorMap (HashMap<String, HashMap<String, ArrayList<Task>>> eventsList, HashMap<String, HashMap<String, ArrayList<Task>>> deadlineList) {
        Task eventTask;
        if (eventsList.size() != 0) {
            for (String moduleCode : eventsList.keySet()) {
                moduleCodeMap.put(moduleCode, null);
                for (String date : eventsList.get(moduleCode).keySet()) {
                    for (int i = 0; i < eventsList.get(moduleCode).get(date).size(); i++) {
                        eventTask = eventsList.get(moduleCode).get(date).get(i);
                        moduleCodeStatusIconDescriptionArrList.add(new Pair(moduleCode, new Pair(eventTask.getStatusIcon(), eventTask.getDescription())));
                    }
                }
            }
        }

        Task deadlineTask;
        String eventsMC;
        for (int i = 0; i < moduleCodeStatusIconDescriptionArrList.size(); i++) { //checks if the module code of the events hashMap is found in the deadline hashMap
            eventsMC = moduleCodeStatusIconDescriptionArrList.get(i).getKey();
            for (String deadlineMC : deadlineList.keySet()) {
                if (!eventsMC.equals(deadlineMC)) {
                    moduleCodeMap.put(deadlineMC, null);
                    for (String date : deadlineList.get(deadlineMC).keySet()) {
                        for (int j = 0; j < deadlineList.get(deadlineMC).get(date).size(); j++) {
                            deadlineTask = deadlineList.get(deadlineMC).get(date).get(j);
                            moduleCodeStatusIconDescriptionArrList.add(new Pair(deadlineMC, new Pair<>(deadlineTask.getStatusIcon(), deadlineTask.getDescription())));
                        }
                    }
                } else if (eventsMC.equals(deadlineMC)) {
                    for (String date : deadlineList.get(deadlineMC).keySet()) {
                        for (int j = 0; j < deadlineList.get(deadlineMC).get(date).size(); j++) {
                            deadlineTask = deadlineList.get(deadlineMC).get(date).get(j);
                            moduleCodeStatusIconDescriptionArrList.add(new Pair(deadlineMC, new Pair<>(deadlineTask.getStatusIcon(), deadlineTask.getDescription())));
                        }
                    }
                }
            }
            break;
        }
        return new Pair(moduleCodeMap, moduleCodeStatusIconDescriptionArrList);
    }

    /**
     * This function sets the text for moduleCodeLabel, completedValueLabel and overdueValueLabel and sets the progress for progressIndicator.
     * @param mc the module code
     * @param totalValue the total number of event and deadline tasks
     * @param completedValue the number of tasks completed
     */
    public void getData (String mc, int totalValue , int completedValue) {
        int undoneValue = totalValue - completedValue;
        Double progressValue = (double) completedValue / totalValue;
        moduleCodeLabel.setText(mc);
        completedValueLabel.setText(String.valueOf(completedValue));
        overdueValueLabel.setText(String.valueOf(undoneValue));
        progressIndicator.setProgress(progressValue);
    }
}
