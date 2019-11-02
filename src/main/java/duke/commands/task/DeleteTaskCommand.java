//@@author kkeejjuunn

package duke.commands.task;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.Task;
import duke.util.DukeUi;
import duke.models.patients.Patient;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.tasks.TaskManager;

import java.util.ArrayList;

public class DeleteTaskCommand implements Command {
    private int id;

    private String deletedTaskInfo;
    private Task taskToBeDeleted;

    /**
     * It keeps the delete task command.
     *
     * @param deletedTaskInfo contains the information of the patient to be deleted.
     */
    public DeleteTaskCommand(String deletedTaskInfo) {
        this.deletedTaskInfo = deletedTaskInfo;
    }

    /**
     * It extracts the task id from the delete task command.
     * It retrieves task based on the id extracted.
     *
     * @param deletedTaskInfo contains the delete command received from parser class which is a string.
     * @param dukeUi              allow user choose the correct task to be deleted.
     * @param taskManager     retrieves the task to be deleted.
     * @return the task to be deleted.
     * @throws DukeException if no match task found.
     */
    public Task getTaskByDeleteTaskCommand(String deletedTaskInfo, DukeUi dukeUi,
                                           TaskManager taskManager) throws DukeException {
        char firstChar = deletedTaskInfo.charAt(0);
        Task task = null;
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(deletedTaskInfo.substring(1));
            } catch (Exception e) {
                throw new DukeException(DeleteTaskCommand.class,"The task id is invalid");
            }
            try {
                task = taskManager.getTask(id);
            } catch (Exception e) {
                throw new DukeException(DeleteTaskCommand.class, "The task id does not exist. ");
            }
        } else {
            throw new DukeException(DeleteTaskCommand.class, "Please follow format 'delete task :#<id>'. ");
        }
        return task;
    }

    /**
     * It deletes the task returned from getTaskByDeleteTaskCommand.
     * It checks whether this task is assigned to any patient.
     * It deletes the relation between this task and any patients
     *
     * @param assignedTaskManager contains the information between all the tasks and patients.
     * @param taskManager         contains information of all tasks.
     * @param patientManager      contains information of all patients.
     * @param dukeUi                  interacts with user.
     * @param storageManager      save the changes in csv file.
     * @throws DukeException if there is error deleting the task.
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager taskManager, PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {
        try {
            taskToBeDeleted = getTaskByDeleteTaskCommand(deletedTaskInfo, dukeUi, taskManager);
        } catch (Exception e) {
            throw e;
        }
        dukeUi.showTaskInfo(taskToBeDeleted);
        try {
            ArrayList<AssignedTask> patientTasks = assignedTaskManager.getTaskPatient(taskToBeDeleted.getId());
            ArrayList<Patient> relatedPatients = new ArrayList<>();
            for (AssignedTask patientTask : patientTasks) {
                relatedPatients.add(patientManager.getPatient(patientTask.getPid()));
            }
            dukeUi.taskPatientFound(taskToBeDeleted, relatedPatients);
            assignedTaskManager.deleteAllAssignedTaskByTaskId(taskToBeDeleted.getId());
            storageManager.saveAssignedTasks(assignedTaskManager.getAssignTasks());
            taskManager.deleteTask(taskToBeDeleted.getId());
        } catch (Exception e) {
            taskManager.deleteTask(taskToBeDeleted.getId());
        }
        storageManager.saveTasks(taskManager.getTaskList());
        dukeUi.taskDeleted();
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