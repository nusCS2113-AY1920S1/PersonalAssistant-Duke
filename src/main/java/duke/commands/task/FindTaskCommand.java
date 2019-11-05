//@@author kkeejjuunn

package duke.commands.task;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.util.DukeUi;
import duke.models.tasks.Task;
import duke.models.tasks.TaskManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.patients.PatientManager;

import java.util.ArrayList;

public class FindTaskCommand implements Command {

    private String command;

    /**
     * The constructor for the FindTaskCommand.
     *
     * @param command contains the task id/description.
     */
    public FindTaskCommand(String command) {
        this.command = command;
    }

    /**
     * It checks whether the command is containing id or description.
     * It finds the task based on id or description.
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
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {
        char firstChar = command.charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(command.substring(1, command.length()));
            } catch (Exception e) {
                throw new DukeException(FindTaskCommand.class, "The task id is invalid.");
            }
            try {
                Task task = taskManager.getTask(id);
                dukeUi.taskFoundById(task);
            } catch (Exception e) {
                throw e;
            }
        } else {
            ArrayList<Task> tasksWithSameDescription = taskManager.getTaskByDescription(command);
            dukeUi.tasksFoundByDescription(tasksWithSameDescription, command);
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
