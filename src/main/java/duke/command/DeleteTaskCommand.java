//@@author kkeejjuunn

package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.PatientTask;
import duke.relation.PatientTaskList;
import duke.storage.StorageManager;
import duke.task.Task;
import duke.task.TaskManager;

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
     * It checks whether user is trying to delete a task by id or description.
     * It retrieves task based on the id extracted.
     *
     * @param deletedTaskInfo contains the delete command received from parser class which is a string.
     * @param ui allow user choose the correct task to be deleted.
     * @param taskManager retrieves the task to be deleted.
     * @return the task to be deleted.
     * @throws DukeException if no match task found.
     */
    public Task getTaskByDeleteTaskCommand(String deletedTaskInfo, Ui ui,
                                           TaskManager taskManager) throws DukeException {
        char firstChar = deletedTaskInfo.charAt(0);
        Task task = null;
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(deletedTaskInfo.substring(1));
            } catch (Exception e) {
                throw e;
            }
            try {
                task = taskManager.getTask(id);
            } catch (Exception e) {
                throw new DukeException("The task id does not exist. ");
            }
        } else {
            ArrayList<Task> tasksWithSameDescription = taskManager.getTaskByDescription(deletedTaskInfo);
            if (tasksWithSameDescription.size() >= 1) {
                int numberChosen = ui.chooseTaskToDelete(tasksWithSameDescription.size());
                if (numberChosen >= 1) {
                    task = tasksWithSameDescription.get(numberChosen - 1);
                }
            } else {
                throw new DukeException("There is no task matched this description. ");
            }
        }
        return task;
    }

    /**
     * It deletes the task returned from getTaskByDeleteTaskCommand.
     * It checks whether this task is assigned to any patient.
     * It deletes the relation between this task and any patients
     *
     * @param patientTaskList contains the information between all the tasks and patients.
     * @param taskManager contains information of all tasks.
     * @param patientManager contains information of all patients.
     * @param ui interacts with user.
     * @param storageManager save the changes in csv file.
     * @throws DukeException if there is error deleting the task.
     */
    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager taskManager, PatientManager patientManager,
                        Ui ui, StorageManager storageManager) throws DukeException {
        try {
            taskToBeDeleted = getTaskByDeleteTaskCommand(deletedTaskInfo, ui, taskManager);
        } catch (Exception e) {
            throw e;
        }
        ui.showTaskInfo(taskToBeDeleted);
        boolean toDelete;
        try {
            ArrayList<PatientTask> patientTasks = patientTaskList.getTaskPatient(taskToBeDeleted.getID());
            ArrayList<Patient> relatedPatients = new ArrayList<>();
            for (PatientTask patientTask : patientTasks) {
                relatedPatients.add(patientManager.getPatient(patientTask.getPatientId()));
            }
            ui.taskPatientFound(taskToBeDeleted, patientTasks, relatedPatients);
            toDelete = ui.confirmTaskToBeDeleted(taskToBeDeleted, true);
            if (toDelete) {
                patientTaskList.deleteAllPatientTaskByTaskId(taskToBeDeleted.getID());
                storageManager.saveAssignedTasks(patientTaskList.fullPatientTaskList());
            }
        } catch (Exception e) {
            toDelete = ui.confirmTaskToBeDeleted(taskToBeDeleted,false);
        }
        if (toDelete) {
            taskManager.deleteTask(taskToBeDeleted.getID());
            storageManager.saveTasks(taskManager.getTaskList());
            ui.taskDeleted();
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


