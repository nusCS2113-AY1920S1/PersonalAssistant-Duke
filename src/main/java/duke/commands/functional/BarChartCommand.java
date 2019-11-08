//@@author qjie7

package duke.commands.functional;

import duke.Duke;
import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.gui.BarChartBox;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.models.patients.PatientManager;
import duke.models.tasks.TaskManager;
import duke.storages.StorageManager;
import duke.util.DukeUi;

import java.util.ArrayList;
import java.util.Map;

public class BarChartCommand implements Command {
    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager tasks, PatientManager patientList,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {
        try {
            Map<String, Integer> counterMap;
            counterMap = storageManager.loadCommandFrequency();
            final ArrayList<Integer> frequencyList = new ArrayList<Integer>(counterMap.values());
            final ArrayList<String> commandNameList = new ArrayList<String>(counterMap.keySet());
            BarChartBox.showBarChartBox(frequencyList, commandNameList);
        } catch (DukeException e) {
            throw new DukeException(BarChartCommand.class,"No command has been entered yet.");

        }



    }

    @Override
    public boolean isExit() {
        return false;
    }
}
