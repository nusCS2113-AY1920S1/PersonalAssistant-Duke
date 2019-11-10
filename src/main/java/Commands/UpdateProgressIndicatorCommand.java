package Commands;

import UserInterface.ProgressController;
import Tasks.TaskList;
import javafx.fxml.FXMLLoader;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the command to update the progress indicator.
 */
public class UpdateProgressIndicatorCommand {
    TaskList eventList;
    TaskList deadlineList;
    private static final String TICK_SYMBOL = "\u2713";

    /**
     * Creates UpdateProgressIndicator object.
     * @param eventList The TaskList object for events
     * @param deadlineList The TaskList object for deadlines
     */
    public UpdateProgressIndicatorCommand(TaskList eventList, TaskList deadlineList) {
        this.eventList = eventList;
        this.deadlineList = deadlineList;
    }

    /**
     * Load the fxml to update the progress indicator.
     * @param eventList The TaskList object for events
     * @param deadlineList The Tasklist object for deadlines
     * @throws IOException if the input is invalid
     */
    public Pair<HashMap<String, String>, ArrayList<Pair<String, Pair<String, String>>>>
        getWholeDate(TaskList eventList, TaskList deadlineList) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ProgressIndicator.fxml"));
        fxmlLoader.load();
        Pair<HashMap<String, String>,
                ArrayList<Pair<String, Pair<String, String>>>> result
                = fxmlLoader.<ProgressController>getController()
                .getProgressIndicatorMap(eventList.getMap(), deadlineList.getMap());
        return result;
    }

    /**
     * This method gets the map which contains the module code.
     * @param wholeData The entire map which contains the module code with the description and tick/cross
     */
    public HashMap<String, String> getModuleMap(Pair<HashMap<String, String>,
            ArrayList<Pair<String, Pair<String, String>>>> wholeData) {
        HashMap<String, String> modulesMap = wholeData.getKey();
        return modulesMap;
    }

    /**
     * This method calculate the values to be shown in the progress indicator.
     * @param moduleMap The map that contains the module code
     * @param wholeData The entire map which contains the module code, description and tick/cross
     * @return module code, completed tasks and total number of tasks
     */
    public HashMap<String, Pair<Integer, Integer>>
        getValues(HashMap<String, String> moduleMap, Pair<HashMap<String, String>,
            ArrayList<Pair<String, Pair<String, String>>>> wholeData) {
        HashMap<String, Pair<Integer, Integer>> moduleCodeAndTotalNumOfTasksAndCompletedValue = new HashMap<>();
        for (String module : moduleMap.keySet()) {
            int totalNumTasks = 0;
            int completedValue = 0;
            ArrayList<Pair<String, Pair<String, String>>> tasks = wholeData.getValue();
            for (Pair<String, Pair<String, String>> as : tasks) {
                if (as.getKey().equals(module)) {
                    totalNumTasks += 1;
                    if (as.getValue().getKey().equals(TICK_SYMBOL)) {
                        completedValue += 1;
                    }
                }
            }
            moduleCodeAndTotalNumOfTasksAndCompletedValue.put(module, new Pair<>(totalNumTasks, completedValue));
        }
        return moduleCodeAndTotalNumOfTasksAndCompletedValue;
    }
}
