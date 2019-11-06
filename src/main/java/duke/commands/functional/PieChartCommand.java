package duke.commands.functional;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.gui.PieChartBox;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.patients.PatientManager;
import duke.models.tasks.TaskManager;
import duke.storages.StorageManager;
import duke.util.DukeUi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PieChartCommand implements Command {

    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks,
                        PatientManager patientList, DukeUi dukeUi,
                        StorageManager storageManager) throws DukeException {
        Map<String, Integer> counterMap = new HashMap<>();
        counterMap = storageManager.loadCommandFrequency();
        ArrayList<Integer> frequencyList = new ArrayList<Integer>(counterMap.values());
        final ArrayList<String> commandNameList = new ArrayList<String>(counterMap.keySet());

        PieChartBox.showPieChartBox(frequencyList, commandNameList);


    }

    @Override
    public boolean isExit() {
        return false;
    }
}
