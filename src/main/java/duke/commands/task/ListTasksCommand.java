//@author kkeejjuunn

package duke.commands.task;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.Task;
import duke.util.DukeUi;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.tasks.TaskManager;

import java.util.ArrayList;

public class ListTasksCommand implements Command {

    /**
     * It lists all the tasks in the Dukepital.
     *
     * @param patientTask    contains the information between all the tasks and patients.
     * @param taskManager    contains information of all tasks.
     * @param patientManager contains information of all patients.
     * @param dukeUi         interacts with user.
     * @param storageManager save the changes in csv file.
     * @throws DukeException if there is error finding tasks.
     */
    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager taskManager, PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException{
        ArrayList<Task> taskList = taskManager.getTaskList();
        if (taskList.size() < 1) {
            throw new DukeException("You do not have any task yet.");
        } else {
            dukeUi.listAllTasks(taskList);
        }
    }

    /**
     * It terminates the Dukepital.
     *
     * @return false.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
