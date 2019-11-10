package UserInterface;

import Tasks.Assignment;
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

    private final ArrayList<Pair<String, Pair<String, String>>> moduleCodeStatusIconDescriptionArrList = new
            ArrayList<>();
    private final HashMap<String, String> moduleCodeMap = new HashMap<>();

    /**
     * This method adds the events/deadlines from the list into the map.
     * @param list The list containing either deadlines/events
     * @param modCodeMap The map containing the module code
     * @param moduleCodeStatusIconDescriptionArrList The list containing module code, status icon and the description
     */
    private ArrayList<Pair<String, Pair<String, String>>> addModuleCodeToArrList(
            HashMap<String, HashMap<String, ArrayList<Assignment>>> list, HashMap<String, String> modCodeMap,
            ArrayList<Pair<String, Pair<String, String>>> moduleCodeStatusIconDescriptionArrList) {
        Assignment task;
        for (String moduleCode : list.keySet()) {
            modCodeMap.put(moduleCode, null);
            for (String date : list.get(moduleCode).keySet()) {
                for (int i = 0; i < list.get(moduleCode).get(date).size(); i++) {
                    task = list.get(moduleCode).get(date).get(i);
                    moduleCodeStatusIconDescriptionArrList.add(new Pair<>(moduleCode, new Pair<>(task.getStatusIcon(),
                            task.getDescription())));
                }
            }
        }
        return moduleCodeStatusIconDescriptionArrList;
    }

    private ArrayList<Pair<String, Pair<String, String>>> addModFromDeadlineListIntoMap(
            String deadlineMC, Assignment deadlineTask, HashMap<String, HashMap<String, ArrayList<Assignment>>> list,
            ArrayList<Pair<String, Pair<String, String>>> moduleCodeStatusIconDescriptionArrList) {
        for (String date : list.get(deadlineMC).keySet()) {
            for (int i = 0; i < list.get(deadlineMC).get(date).size(); i++) {
                deadlineTask = list.get(deadlineMC).get(date).get(i);
                moduleCodeStatusIconDescriptionArrList.add(new Pair<>(deadlineMC, new Pair<>(
                        deadlineTask.getStatusIcon(), deadlineTask.getDescription())));
            }
        }
        return moduleCodeStatusIconDescriptionArrList;
    }

    public Assignment deadlineTask;
    private ArrayList<Pair<String, Pair<String, String>>> arrList;

    /**
     * This function gets the arraylist containing tasks in terms of Pair(module code (status icon, description)) and
     * determine the modules taken by user.
     * @param eventsList the list containing the event tasks
     * @param deadlineList the list containing the deadline tasks
     * @return a pair containing the arraylist of tasks and hashmap of module code
     */
    public Pair<HashMap<String, String>, ArrayList<Pair<String, Pair<String, String>>>> getProgressIndicatorMap(
            HashMap<String, HashMap<String, ArrayList<Assignment>>> eventsList, HashMap<String, HashMap<String,
            ArrayList<Assignment>>> deadlineList) {

        if (eventsList.size() != 0) {
            arrList = addModuleCodeToArrList(eventsList, moduleCodeMap, moduleCodeStatusIconDescriptionArrList);
        }

        String eventsMC;
        if (eventsList.size() == 0) {
            arrList = addModuleCodeToArrList(deadlineList, moduleCodeMap, moduleCodeStatusIconDescriptionArrList);
        } else {
            for (int i = 0; i < moduleCodeStatusIconDescriptionArrList.size(); i++) {
                eventsMC = moduleCodeStatusIconDescriptionArrList.get(i).getKey();
                for (String deadlineMC : deadlineList.keySet()) {
                    if (!eventsMC.equals(deadlineMC)) {
                        moduleCodeMap.put(deadlineMC, null);
                        arrList = addModFromDeadlineListIntoMap(deadlineMC, deadlineTask, deadlineList,
                                moduleCodeStatusIconDescriptionArrList);
                    } else if (eventsMC.equals(deadlineMC)) {
                        arrList = addModFromDeadlineListIntoMap(deadlineMC, deadlineTask, deadlineList,
                                moduleCodeStatusIconDescriptionArrList);
                    }
                }
                break;
            }
        }
        return new Pair<>(moduleCodeMap, arrList);
    }

    /**
     * This function sets the text for moduleCodeLabel, completedValueLabel and overdueValueLabel and
     * sets the progress for progressIndicator.
     * @param mc the module code
     * @param totalValue the total number of event and deadline tasks
     * @param completedValue the number of tasks completed
     */
    public void getData(String mc, int totalValue, int completedValue) {
        int undoneValue = totalValue - completedValue;
        double progressValue = (double) completedValue / totalValue;
        moduleCodeLabel.setText(mc);
        completedValueLabel.setText(String.valueOf(completedValue));
        overdueValueLabel.setText(String.valueOf(undoneValue));
        progressIndicator.setProgress(progressValue);
    }
}
