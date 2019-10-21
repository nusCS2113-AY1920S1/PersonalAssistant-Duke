package duke.command;

import duke.core.DukeException;
import duke.core.Ui;
import duke.patient.Patient;
import duke.patient.PatientManager;
import duke.relation.PatientTask;
import duke.relation.PatientTaskList;
import duke.statistic.Counter;
import duke.storage.CounterStorage;
import duke.storage.PatientStorage;
import duke.storage.PatientTaskStorage;
import duke.storage.TaskStorage;
import duke.task.Task;
import duke.task.TaskManager;

import java.util.ArrayList;

public class FindPatientTaskCommand extends Command {

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
     * @param patientTaskStorage .
     * @param taskStorage        .
     * @param patientStorage     .
     * @throws DukeException .
     */
    @Override
    public void execute(PatientTaskList patientTaskList, TaskManager tasksManager, PatientManager patientManager,
                        Ui ui, PatientTaskStorage patientTaskStorage,
                        TaskStorage taskStorage, PatientStorage patientStorage) throws DukeException {
        char firstChar = command.charAt(0);
        if (firstChar == '#') {
            int id;
            try {
                id = Integer.parseInt(command.substring(1, command.length()));
                Patient patient = patientManager.getPatient(id);
                ArrayList<PatientTask> patientTask = patientTaskList.getPatientTask(id);
                ArrayList<Task> tempTask = new ArrayList<>();
                for (PatientTask temppatientTask : patientTask) {
                    tempTask.add(tasksManager.getTask(temppatientTask.getTaskID()));
                }
                ui.patientTaskFound(patient, patientTask, tempTask);
            } catch (Exception e) {
                throw new DukeException(e.getMessage());
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
                for (PatientTask temppatientTask : patientWithTask) {
                    tempTask.add(tasksManager.getTask(temppatientTask.getTaskID()));
                }
                ui.patientTaskFound(patientsWithSameName.get(0), patientWithTask, tempTask);

            } catch (Exception e) {
                throw new DukeException("You may entered the wrong format or the patient does not have any tasks");
            }
        }
    }


    @Override
    public boolean isExit() {
        return false;
    }
}