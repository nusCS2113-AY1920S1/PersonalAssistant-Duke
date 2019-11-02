package duke.commands.assignedtask;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.TaskManager;
import duke.util.DukeUi;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;

public class DeleteAssignedTaskCommand implements Command {
    private int taskUniqueId;


    /**
     * .
     *
     * @param deleteInfo .
     * @throws DukeException .
     */
    public DeleteAssignedTaskCommand(String deleteInfo) throws DukeException {
        super();
        char firstChar = deleteInfo.charAt(0);

        try {
            if (deleteInfo.charAt(0) != '#') {
                throw new DukeException("Invalid format. Please follow: "
                        + "delete assigned task : #<taskUniqueID>");
            }
            taskUniqueId = Integer.parseInt(deleteInfo.substring(1));

        } catch (DukeException e) {
            throw new DukeException("Warning " + e.getMessage());
        }
    }

    /**
     * .
     *
     * @param assignedTaskManager .
     * @param taskManager         .
     * @param patientManager      .
     * @param dukeUi                  .
     * @param storageManager      .
     * @throws DukeException .
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager taskManager,
                        PatientManager patientManager, DukeUi dukeUi,
                        StorageManager storageManager)
        throws DukeException {

        try {
            assignedTaskManager.deletePatientTaskByUniqueId(taskUniqueId);
            storageManager.saveAssignedTasks(assignedTaskManager.getAssignTasks());
            dukeUi.patientTaskDeleted(taskUniqueId);
        } catch (DukeException e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * .
     *
     * @return .
     */
    @Override
    public boolean isExit() {
        return false;
    }
}