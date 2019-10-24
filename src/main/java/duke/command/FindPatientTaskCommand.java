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
     * @param patientTaskList    .
     * @param tasksManager       .
     * @param patientManager     .
     * @param ui                 .
     * @param storageManager .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager tasksManager, PatientManager patientManager,
                        Ui ui, StorageManager storageManager) throws DukeException {
        char firstChar = command.charAt(0);
        if (firstChar == '#') {
            int id;
            id = Integer.parseInt(command.substring(1, command.length()));
            Patient patient = patientManager.getPatient(id);
            if (patientTaskList.doesPatientIdExist(id)) {
                ArrayList<PatientTask> patientTask = patientTaskList.getPatientTask(id);
                ArrayList<Task> tempTask = new ArrayList<>();
                for (PatientTask tempPatientTask : patientTask) {
                    tempTask.add(tasksManager.getTask(tempPatientTask.getTaskID()));
                }
                ui.patientTaskFound(patient, patientTask, tempTask);
            } else {
                throw new DukeException("This patient does not have any tasks.");
            }
        } else {
            String name = command.toLowerCase();
            ArrayList<Patient> patientsWithSameName = patientManager.getPatientByName(name);
            ArrayList<PatientTask> patientWithTask = new ArrayList<>();
            ArrayList<Task> tempTask = new ArrayList<>();

            try {
                for (Patient patient : patientsWithSameName) {
                    if (patient.getName().toLowerCase().equals(name)) {
                        patientWithTask = patientTaskList.getPatientTask(patient.getID());
                    }
                }
                for (PatientTask tempPatientTask : patientWithTask) {
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