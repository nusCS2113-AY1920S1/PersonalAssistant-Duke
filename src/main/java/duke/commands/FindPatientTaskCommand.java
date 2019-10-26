package duke.commands;

import duke.exceptions.DukeException;
import duke.util.Ui;
import duke.models.patients.Patient;
import duke.models.patients.PatientManager;
import duke.models.assignedPatientTasks.AssignedTask;
import duke.models.assignedPatientTasks.AssignedTaskManager;
import duke.storages.StorageManager;
import duke.models.patientTasks.Task;
import duke.models.patientTasks.TaskManager;

import java.util.ArrayList;

public class FindPatientTaskCommand implements Command {

    private String command;

    /**
     * .
     *
     * @param cmd .
     */
    public FindPatientTaskCommand(String cmd) {
        super();
        this.command = cmd;
    }

    /**
     * .
     *
     * @param assignedTaskManager    .
     * @param tasksManager       .
     * @param patientManager     .
     * @param ui                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(AssignedTaskManager assignedTaskManager, TaskManager tasksManager, PatientManager patientManager,
                        Ui ui, StorageManager storageManager) throws DukeException {
        char firstChar = command.charAt(0);
        if (firstChar == '#') {
            int id;
            id = Integer.parseInt(command.substring(1, command.length()));
            Patient patient = patientManager.getPatient(id);
            if (assignedTaskManager.doesPatientIdExist(id)) {
                ArrayList<AssignedTask> patientTask = assignedTaskManager.getPatientTask(id);
                ArrayList<Task> tempTask = new ArrayList<>();
                for (AssignedTask tempPatientTask : patientTask) {
                    tempTask.add(tasksManager.getTask(tempPatientTask.getTaskID()));
                }
                ui.patientTaskFound(patient, patientTask, tempTask);
            } else {
                throw new DukeException("This patient does not have any tasks.");
            }
        } else {
            String name = command.toLowerCase();
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(name);
            ArrayList<AssignedTask> patientWithTask = new ArrayList<>();
            ArrayList<Task> tempTask = new ArrayList<>();

            try {
                for (Patient patient : patientsWithSameName) {
                    if (patient.getName().toLowerCase().equals(name)) {
                        patientWithTask = assignedTaskManager.getPatientTask(patient.getId());
                    }
                }
                for (AssignedTask tempPatientTask : patientWithTask) {
                    tempTask.add(tasksManager.getTask(tempPatientTask.getTaskID()));
                }
                ui.patientTaskFound(patientsWithSameName.get(0), patientWithTask, tempTask);

            } catch (Exception e) {
                throw new DukeException("The patient does not have any tasks");
            }
        }
    }


    @Override
    public boolean isExit() {
        return false;
    }
}