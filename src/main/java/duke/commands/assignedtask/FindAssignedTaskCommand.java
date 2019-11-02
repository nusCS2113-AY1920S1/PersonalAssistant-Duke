//@@author WEIFENG-NUSCEG

package duke.commands.assignedtask;

import duke.commands.Command;
import duke.exceptions.DukeException;
import duke.models.tasks.TaskManager;
import duke.util.DukeUi;
import duke.models.patients.Patient;
import duke.models.patients.PatientManager;
import duke.models.assignedtasks.AssignedTask;
import duke.models.assignedtasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.tasks.Task;

import java.util.ArrayList;

/**
 * Represents a command to find all the tasks assigned to a specific patient
 * through the patient's id.
 */
public class FindAssignedTaskCommand implements Command {
    private int patientId;

    /**
     * Create a new FindAssignedTaskCommand with the user input (patient id) received, check if the
     * information follows the correct FindAssignedTaskCommand format.
     *
     * @param cmd Id of the patient who we are interested to find all his/her associated tasks.
     * @throws DukeException throws exception if the format is wrong.
     */
    public FindAssignedTaskCommand(String cmd) throws DukeException {
        super();
        try {
            if (cmd.charAt(0) != '#') {
                throw new DukeException("Invalid format. Please follow: "
                        + "find assigned tasks : #<patientID>");
            }
            patientId = Integer.parseInt(cmd.substring(1));

        } catch (DukeException e) {
            throw new DukeException("Warning " + e.getMessage());
        }
    }

    /**
     * Run the command with the respect TaskList, UI, and storage, during the execution,
     * this method will be checking if any assigned tasks in the list belongs to the
     * specific patient received from user input and append these AssignedTasks into
     * an array list and print them out with the tasks' detail information on the ui.
     *
     * @param assignedTaskManager contains the information between all the tasks and patients.
     * @param tasksManager        contains information of all the tasks.
     * @param patientManager      contains information of all the patients.
     * @param ui                  interacts with user.
     * @param storageManager      save the changes in csv file.
     * @throws DukeException if there is error during searching for the assigned task.
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager tasksManager,
                        PatientManager patientManager,
                        DukeUi dukeUi, StorageManager storageManager) throws DukeException {
        try {
            Patient patient = patientManager.getPatient(patientId);
            ArrayList<AssignedTask> patientTask = assignedTaskManager.getPatientTask(patientId);
            ArrayList<Task> newTask = new ArrayList<>();
            for (AssignedTask tempPatientTask : patientTask) {
                newTask.add(tasksManager.getTask(tempPatientTask.getTid()));
            }
            dukeUi.patientTaskFound(patient, patientTask, newTask);
        } catch (DukeException e) {
            throw new DukeException("Warning " + e.getMessage());
        }
    }

    /**
     * Decide whether duke should exist.
     *
     * @return A boolean. True if the command tells Duke to exit, false
     */
    @Override
    public boolean isExit() {
        return false;
    }
}