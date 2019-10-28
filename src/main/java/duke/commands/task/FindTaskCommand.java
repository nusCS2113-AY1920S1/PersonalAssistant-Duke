//@@author kkeejjuunn

package duke.commands.task;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.util.Ui;
import duke.models.tasks.Task;
import duke.models.tasks.TaskManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.patients.PatientManager;

import java.util.ArrayList;

public class FindTaskCommand implements Command {

    private String command;

    public FindTaskCommand(String command) {
        this.command = command;
    }

    /**
     * It checks whether the command is containing id or description.
     * It finds the task based on id or description.
     *
     * @param patientTask         contains the information between all the tasks and patients.
     * @param taskManager         contains information of all tasks.
     * @param patientManager      contains information of all patients.
     * @param ui                  interacts with user.
     * @param storageManager      save the changes in csv file.
     * @throws DukeException if there is error finding tasks.
     */
    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager taskManager, PatientManager patientManager,
                        Ui ui, StorageManager storageManager) throws DukeException {
        char firstChar = command.charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(command.substring(1, command.length()));
            } catch (Exception e) {
                throw new DukeException("The task id is invalid.");
            }
            try {
                Task task = taskManager.getTask(id);
                ui.taskFoundById(task);
            } catch (Exception e) {
                throw new DukeException("The task id does not exist.");
            }
        } else {
            ArrayList<Task> tasksWithSameDescription = taskManager.getTaskByDescription(command);
            ui.tasksFoundByDescription(tasksWithSameDescription, command);
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
