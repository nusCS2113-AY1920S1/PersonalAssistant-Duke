//@@author kkeejjuunn

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
     * The constructor for the UpdateTaskCommand.
     *
     * @param command contains the task id and the new description.
     */
    public UpdateTaskCommand(String[] command) {
        this.command = command;
    }

    /**
     * It finds the task based on id and update its description.
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
        char firstChar = command[0].charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(command[0].substring(1));
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
                throw e;
            }
        } else {
            throw new DukeException(UpdateTaskCommand.class,
                    "Please follow the format 'update task :#<id> :description :<new description>'.");
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
