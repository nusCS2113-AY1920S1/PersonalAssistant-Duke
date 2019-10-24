package Commands;

import JavaFx.ProgressController;
import Tasks.TaskList;
import javafx.fxml.FXMLLoader;
import javafx.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

public class UpdateProgressIndicatorCommand {
    TaskList eventList;
    TaskList deadlineList;

    public UpdateProgressIndicatorCommand(TaskList eventList, TaskList deadlineList) {
        this.eventList = eventList;
        this.deadlineList = deadlineList;
    }

    public Pair<HashMap<String, String>, ArrayList<Pair<String, Pair<String, String>>>> getWholeDate (TaskList eventList, TaskList deadlineList) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ProgressIndicator.fxml"));
        fxmlLoader.load();
        Pair<HashMap<String, String>, ArrayList<Pair<String, Pair<String, String>>>> result = fxmlLoader.<ProgressController>getController().getProgressIndicatorMap(eventList.getMap(), deadlineList.getMap());
        return result;
    }

    public HashMap<String, String> getModuleMap (Pair<HashMap<String, String>, ArrayList<Pair<String, Pair<String, String>>>> wholeData) {
        HashMap<String, String> modulesMap = wholeData.getKey();
        return modulesMap;
    }

    public HashMap<String, Pair<Integer, Integer>> getValues(HashMap<String, String> moduleMap, Pair<HashMap<String, String>, ArrayList<Pair<String, Pair<String, String>>>> wholeData) {
        HashMap<String, Pair<Integer, Integer>> moduleCodeAndTotalNumOfTasksAndCompletedValue = new HashMap<>();
        for (String module : moduleMap.keySet()) {
            int totalNumTasks = 0;
            int completedValue = 0;
            ArrayList<Pair<String, Pair<String, String>>> tasks = wholeData.getValue();
            for (Pair<String, Pair<String, String>> as : tasks) {
                if (as.getKey().equals(module)) {
                    totalNumTasks += 1;
                    if (as.getValue().getKey().equals("\u2713")) {
                        completedValue += 1;
                    }
                }
            }
            moduleCodeAndTotalNumOfTasksAndCompletedValue.put(module, new Pair(totalNumTasks, completedValue));
        }
        return moduleCodeAndTotalNumOfTasksAndCompletedValue;
    }
}
