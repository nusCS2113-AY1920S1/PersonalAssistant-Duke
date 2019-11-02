//@@kkeejjuunn

package duke.commands.task;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.Task;
import duke.util.DukeUi;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.tasks.TaskManager;

public class UpdateTaskCommand implements Command {
    private String[] command;

    /**
     * .
     *
     * @param command .
     */
    public UpdateTaskCommand(String[] command) {
        this.command = command;
    }

    /**
     * .
     *
     * @param patientTask        .
     * @param taskManager        .
     * @param patientManager     .
     * @param dukeUi                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(AssignedTaskManager patientTask, TaskManager taskManager, PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {
        char firstChar = command[0].charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(command[0].substring(1, command[0].length()));
                Task taskToBeUpdated = taskManager.getTask(id);
                if (command[1].toLowerCase().equals("description")) {
                    taskToBeUpdated.setDescription(command[2]);
                } else {
                    throw new DukeException(UpdateTaskCommand.class, "You can only update 'Description' of the task");
                }

                storageManager.saveTasks(taskManager.getTaskList());
                dukeUi.showUpdatedSuccessfully();
                dukeUi.showTaskInfo(taskToBeUpdated);
            } catch (Exception e) {
                throw new DukeException(UpdateTaskCommand.class,
                        "Please follow the format 'update task :#<id> :description :<new description>'.");
            }

        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
