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

public class FindAssignedTaskCommand implements Command {

    private int patientId;

    /**
     * .
     *
     * @param cmd .
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
     * .
     *
     * @param assignedTaskManager    .
     * @param tasksManager       .
     * @param patientManager     .
     * @param dukeUi                 .
     * @param storageManager .
     * @throws DukeException .
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

    @Override
    public boolean isExit() {
        return false;
    }
}